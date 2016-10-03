package com.oic.plugin.multidimens.common;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.packageDependencies.ui.FileNode;
import com.intellij.pom.Navigatable;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.junit.Assert;

/**
 * Created by khacpham on 9/29/16.
 */
public class ActionUtils {

    /**
     * check selected node is a directory
     *
     * @param e action
     * @return selected node is directory or not
     */
    public static boolean isSelectDirectory(AnActionEvent e){
        NavigatablePsiElement file = (NavigatablePsiElement) e.getData(CommonDataKeys.NAVIGATABLE);
        boolean isDirectory = false;
        if (file instanceof PsiDirectory) {
            isDirectory = true;
        }
        return isDirectory;
    }

    /**
     * check selected node is a xml file
     * @param e action
     * @return true if is a xml file
     */
    public static boolean isSelectXmlFile(AnActionEvent e){
        boolean isXmlFile = false;
        final Navigatable[] data = CommonDataKeys.NAVIGATABLE_ARRAY.getData(e.getDataContext());
        Assert.assertNotNull(data);
        Navigatable node = data[0];
        Assert.assertNotNull(node);
        // TODO check instance of AndroidResFileNode
        if (node instanceof FileNode) {
            PsiElement psiFile = ((FileNode) node).getPsiElement();
            if (psiFile instanceof com.intellij.psi.impl.source.xml.XmlFileImpl) {
                isXmlFile = true;
            }
        }
        return isXmlFile;
    }
}
