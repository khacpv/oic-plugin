package com.oic.plugin.multidimens.ui.window;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlTag;
import com.oic.plugin.multidimens.common.MouseUtils;
import com.oic.plugin.multidimens.common.view.cell.CellRenderer;
import com.oic.plugin.multidimens.data.VirtualFileDimen;
import com.oic.plugin.multidimens.logic.LogicFactory;
import com.oic.plugin.multidimens.logic.LogicXmlRead;
import com.oic.plugin.multidimens.ui.menu.EditDeleteMenu;
import com.oic.plugin.multidimens.ui.menu.InsertDimenMenu;
import org.junit.Assert;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by FRAMGIA\pham.van.khac on 9/30/16.
 */
public class FormMain extends JFrame implements ActionListener {

    private String[] dimensions = {
            "values",
            "values-w320dp",
            "values-w360dp",
            "values-w411dp"
    };

    private JPanel rootPanel;
    private JTextPane textContent;
    private JTree treeValues;
    private JButton buttonExport;
    private JSplitPane splitPane;
    private JLabel textHelp;
    private CellRenderer cellRenderer;

    private DefaultMutableTreeNode rootNode;
    VirtualFileDimen selectedValuesFolder;
    private ArrayList<VirtualFileDimen> files = new ArrayList<>();

    private Project project;
    private XmlFileImpl xmlFile;

    private LogicXmlRead xmlRead = LogicFactory.getLogicXmlRead();

    public FormMain() {
        super("MDT Tool");

        initWindows();

        initViews();

        initEvents();
    }

    private void initWindows() {
        setPreferredSize(new Dimension(800, 650));
        setMinimumSize(new Dimension(400, 350));

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initViews() {
        for (String dimension : dimensions) {
            files.add(new VirtualFileDimen(dimension));
        }

        cellRenderer = new CellRenderer("");
        treeValues.setCellRenderer(cellRenderer);

        // set hint text
        MouseUtils.setHintOnMouseHover(treeValues,"Select file to see changes", textHelp);
        MouseUtils.setHintOnMouseHover(textContent,"Type to edit", textHelp);
        MouseUtils.setHintOnMouseHover(buttonExport,"Write to dimens.xml files", textHelp);
    }

    private void initEvents() {
        // button event
        buttonExport.addActionListener(this);

        // tree event
        MouseUtils.addMouseEvent(treeValues, new MouseUtils.OnJTreeMouseClickListener() {
            @Override
            public void onNodeRightMouseClick(MouseEvent event, DefaultMutableTreeNode node) {
                if (node.toString().equalsIgnoreCase(rootNode.toString())) {
                    // show add new dimens
                    InsertDimenMenu popup = new InsertDimenMenu(fileName -> {
                        files.add(new VirtualFileDimen(fileName));
                        refreshTree();
                    });
                    popup.show(event.getComponent(), event.getX(), event.getY());
                    return;
                }

                // show edit dimens: delete, rename
                VirtualFileDimen selectedFile = (VirtualFileDimen) node.getUserObject();
                EditDeleteMenu popup = new EditDeleteMenu(selectedFile, new EditDeleteMenu.OnInsertDimenListener() {
                    @Override
                    public void onEditDimen(String name) {
                        for (VirtualFileDimen file : files) {
                            if (file.name.equalsIgnoreCase(selectedFile.name)) {
                                file.name = name;
                                break;
                            }
                        }
                        refreshTree();
                    }

                    @Override
                    public void onDeleteDimen() {
                        for (VirtualFileDimen file : files) {
                            if (file.name.equalsIgnoreCase(selectedFile.name)) {
                                files.remove(file);
                                break;
                            }
                        }
                        refreshTree();
                    }
                });
                popup.show(event.getComponent(), event.getX(), event.getY());
            }

            @Override
            public void onNodeLeftMouseClick(MouseEvent event, DefaultMutableTreeNode node) {
                if (node.toString().equalsIgnoreCase(rootNode.toString())) {
                    textContent.setText("");
                    return;
                }

                // update content panel
                VirtualFileDimen selectedFile = (VirtualFileDimen) node.getUserObject();
                float factor = selectedFile.getDimen() / selectedValuesFolder.getDimen();
                if (selectedFile.getDimen() == 1) {
                    factor = 1;
                }
                String content = xmlRead.getContent(xmlFile.getDocument(), factor);
                textContent.setText(content);
            }
        });
    }

    /**
     * reload tree file view
     */
    private void refreshTree() {
        Collections.sort(files, (o1, o2) -> o1.name.compareTo(o2.name));

        rootNode = new DefaultMutableTreeNode("res");

        int selectedIndex = 1;

        int i = 1;
        for (VirtualFileDimen file : files) {
            rootNode.add(file.node);
            if (file.selected) {
                selectedIndex = i;
            }
            i++;
        }

        if (selectedValuesFolder != null) {
            cellRenderer.setDefaultNodeName(selectedValuesFolder.name);
            treeValues.setCellRenderer(cellRenderer);
        }

        treeValues.setModel(new DefaultTreeModel(rootNode));
        treeValues.setSelectionRow(selectedIndex);    // ignore root node
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().equalsIgnoreCase(buttonExport.getText())) {
            // TODO convert dimens
            // TODO create files if needed
            // TODO write to files
            JOptionPane.showConfirmDialog(this, "Make done");
        }
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setXmlFile(XmlFileImpl psiFile) {
        this.xmlFile = psiFile;

        String content = xmlRead.getContent(xmlFile.getDocument());
        textContent.setText(content);
    }

    public void refreshData() {
        files.clear();

        // get selected file dimens.xml
        VirtualFile fileDimens = xmlFile.getVirtualFile();
        selectedValuesFolder = new VirtualFileDimen(fileDimens);
        selectedValuesFolder.selected = true;

        // find all file dimens.xml
        VirtualFile folderRes = fileDimens.getParent().getParent();
        VirtualFile[] valuesFolders = folderRes.getChildren();

        for (VirtualFile folder : valuesFolders) {
            if (folder.getName().contains("values")) {
                VirtualFile[] subFolder = folder.getChildren();
                for (VirtualFile xmlFile : subFolder) {
                    if (xmlFile.getName().equalsIgnoreCase("dimens.xml")) {
                        VirtualFileDimen valuesFolder = new VirtualFileDimen(xmlFile);
                        files.add(valuesFolder);
                        if (folder.getName().equalsIgnoreCase(selectedValuesFolder.name)) {
                            valuesFolder.selected = true;
                        }
                    }
                }
            }
        }

        refreshTree();
    }

    public void setData(Project project, XmlFileImpl psiFile) {
        VirtualFile virtualFile = (psiFile).getVirtualFile();
        XmlDocument document = psiFile.getDocument();
        Assert.assertNotNull(document);


        String filePath = virtualFile.getPath();
        System.out.println("file path: " + filePath);

        VirtualFile virtualFileRes = virtualFile.getParent().getParent();

        WriteCommandAction.runWriteCommandAction(project, () -> {
            VirtualFile[] childs = virtualFileRes.getChildren();
            VirtualFile folderW320dp = null;
            VirtualFile folderValues = null;
            for (VirtualFile item : childs) {
                if (item.getName().contains("values-w320dp")) {
                    folderW320dp = item;
                } else if (item.getName().equalsIgnoreCase("values")) {
                    folderValues = item;
                }
            }
            Assert.assertNotNull(folderValues);
            VirtualFile[] childInValues = folderValues.getChildren();
            VirtualFile fileDimens = null;

            if (folderW320dp == null) {
                try {
                    folderW320dp = virtualFileRes.createChildDirectory(project, "values-w320dp");
                    System.out.println("folder 320dp: " + folderW320dp.getPath());
                } catch (IOException e1) {
                    System.out.println(e1.getLocalizedMessage());
                }
            } else {
                System.out.println("folder 320dp: existed");
            }

            for (VirtualFile item : childInValues) {
                if (item.getNameWithoutExtension().equalsIgnoreCase("dimens")) {
                    fileDimens = item;
                }
            }
            Assert.assertNotNull(fileDimens);
            try {
                Assert.assertNotNull(folderW320dp);
                fileDimens.copy(project, folderW320dp, fileDimens.getName());
            } catch (IOException e1) {
                System.out.println(e1.getLocalizedMessage());
            }
        });

        StringBuilder content = new StringBuilder();
        XmlTag root = document.getRootTag();
        Assert.assertNotNull(root);

        PsiElement[] elements = root.getChildren();
        for (PsiElement element : elements) {
            if (element instanceof XmlTag) {
                XmlTag tag = (XmlTag) element;
                String name = tag.getLocalName();

                if (name.equalsIgnoreCase("dimen")) {
                    String attName = tag.getAttributeValue("name");
                    String value = tag.getValue().getTrimmedText();
                    String text = tag.getText();

                    if (value.endsWith("sp")) {

                    } else if (value.endsWith("dip") || value.endsWith("dp")) {
                        content.append(value + "\n");
                    }

                    System.out.print("attName:" + attName);   //text_size_space_small
                    System.out.print("text:" + text); // <dimen name="text_size_space_small">6sp</dimen>
                    System.out.println("value:" + value);    // 6sp
                }
            }
        }

        refreshTree();
    }

    public void setHelp(String help) {
        textHelp.setText(help);
    }
}
