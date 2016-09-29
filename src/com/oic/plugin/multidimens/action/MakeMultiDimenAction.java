package com.oic.plugin.multidimens.action;

import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.packageDependencies.ui.FileNode;
import com.intellij.pom.Navigatable;
import com.intellij.psi.*;
import com.intellij.psi.impl.PsiElementBase;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.intellij.psi.impl.source.PsiFileImpl;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.psi.xml.*;
import com.intellij.util.messages.impl.Message;
import com.intellij.util.ui.tree.AbstractFileTreeTable;
import org.junit.Assert;
import org.xml.sax.XMLFilter;

/**
 * Created by FRAMGIA\pham.van.khac on 9/29/16.
 */
public class MakeMultiDimenAction extends AnAction {

    public static final String TITLE = "Make Multi Dimen Action";

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Navigatable[] data = CommonDataKeys.NAVIGATABLE_ARRAY.getData(e.getDataContext());

        FileNode file = (FileNode) data[0];
        PsiElement psiFile = file.getPsiElement();
        if (psiFile instanceof com.intellij.psi.impl.source.xml.XmlFileImpl) {
            XmlDocument document = ((XmlFileImpl) psiFile).getDocument();
            Assert.assertNotNull(document);
            StringBuilder content = new StringBuilder();
            XmlTag root = document.getRootTag();
            PsiElement[] elements = root.getChildren();
            for (PsiElement element : elements) {
                if (element instanceof XmlTag) {
                    String name = ((XmlTag) element).getLocalName();
                    if (name.equalsIgnoreCase("dimen")) {
                        String attName = ((XmlTag) element).getAttributeValue("name");
                        String value = ((XmlTag) element).getValue().getTrimmedText();
                        String text = element.getText();

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
            Messages.showInfoMessage(content.toString(), TITLE);
        } else {
            Messages.showInfoMessage("please select a xml file" + file, TITLE);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        // get data key
        final Project project = e.getData(CommonDataKeys.PROJECT);
        NavigatablePsiElement file = (NavigatablePsiElement) e.getData(CommonDataKeys.NAVIGATABLE);
        boolean isDirectory = false;
        if (file instanceof PsiDirectory) {
            isDirectory = true;
        }
        boolean isXmlFile = false;
        final Navigatable[] data = CommonDataKeys.NAVIGATABLE_ARRAY.getData(e.getDataContext());
        Navigatable node = data[0];
        Assert.assertNotNull(node);
        if (node instanceof FileNode) {
            PsiElement psiFile = ((FileNode) node).getPsiElement();
            if (psiFile instanceof com.intellij.psi.impl.source.xml.XmlFileImpl) {
                isXmlFile = true;
            }
        }
        // set visibility only has project and editor
        e.getPresentation().setVisible(project != null && project.isOpen() && !isDirectory && isXmlFile);
    }
}