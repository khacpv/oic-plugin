package com.oic.plugin.multidimens.plugin.action;

import com.android.tools.idea.navigator.nodes.AndroidResFileNode;
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

        Navigatable node = data[0];

        PsiElement psiFile = null;

        // Project Files
        if (node instanceof FileNode) {
            psiFile = ((FileNode) node).getPsiElement();
        }
        // Android
        else if (node instanceof AndroidResFileNode) {
            psiFile = ((AndroidResFileNode) node).getValue();
        }

        if (psiFile != null && psiFile instanceof com.intellij.psi.impl.source.xml.XmlFileImpl) {
            FormMain makeForm = new FormMain();
            makeForm.setVisible(true);
            makeForm.setProject(project);
            makeForm.setXmlFile((XmlFileImpl) psiFile);
            makeForm.refreshData();
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