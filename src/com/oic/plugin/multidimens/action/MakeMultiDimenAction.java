package com.oic.plugin.multidimens.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.ex.ActionUtil;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.packageDependencies.ui.FileNode;
import com.intellij.pom.Navigatable;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlTag;
import com.oic.plugin.multidimens.common.ActionUtils;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;

/**
 * Created by FRAMGIA\pham.van.khac on 9/29/16.
 */
public class MakeMultiDimenAction extends AnAction {

    public static final String TITLE = "Multi Dimens Tool";

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final Navigatable[] data = CommonDataKeys.NAVIGATABLE_ARRAY.getData(e.getDataContext());
        Assert.assertNotNull(data);

        FileNode file = (FileNode) data[0];
        PsiElement psiFile = file.getPsiElement();

        if (psiFile instanceof com.intellij.psi.impl.source.xml.XmlFileImpl) {
            VirtualFile virtualFile = ((XmlFileImpl) psiFile).getVirtualFile();
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

            XmlDocument document = ((XmlFileImpl) psiFile).getDocument();
            Assert.assertNotNull(document);

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
            Messages.showInfoMessage(content.toString(), TITLE);
        } else {
            Messages.showInfoMessage("please select a xml file" + file, TITLE);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        boolean isDirectory = ActionUtils.isSelectDirectory(e);
        boolean isXmlFile = ActionUtils.isSelectXmlFile(e);

        e.getPresentation().setVisible(project != null && project.isOpen() && !isDirectory && isXmlFile);
    }

}