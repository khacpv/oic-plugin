package com.oic.plugin.multidimens.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.packageDependencies.ui.FileNode;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.oic.plugin.multidimens.common.ActionUtils;
import com.oic.plugin.multidimens.ui.window.FormMain;
import org.junit.Assert;

/**
 * Created by FRAMGIA\pham.van.khac on 9/29/16.
 * Right mouse click on any dimens.xml file, context menu "Multi Dimens Tool" appears.
 */
public class MakeMultiDimenAction extends AnAction {
    private static final String TITLE = "Multi Dimens Tool";

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);

        final Navigatable[] data = CommonDataKeys.NAVIGATABLE_ARRAY.getData(e.getDataContext());
        Assert.assertNotNull(data);

        FileNode file = (FileNode) data[0];
        PsiElement psiFile = file.getPsiElement();

        if (psiFile instanceof com.intellij.psi.impl.source.xml.XmlFileImpl) {
            FormMain makeForm = new FormMain();
            makeForm.setVisible(true);
            makeForm.setProject(project);
            makeForm.setXmlFile((XmlFileImpl) psiFile);
            makeForm.refreshData();
        } else {
            Messages.showInfoMessage("please select dimens.xml file " + file, TITLE);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        boolean isDirectory = ActionUtils.isSelectDirectory(e);
        boolean isXmlFile = ActionUtils.isSelectXmlFile(e);

        boolean shouldShowContextMenu = project != null && project.isOpen() && !isDirectory && isXmlFile;

        e.getPresentation().setVisible(shouldShowContextMenu);
    }

}