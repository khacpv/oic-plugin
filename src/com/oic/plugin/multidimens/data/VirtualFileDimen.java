package com.oic.plugin.multidimens.data;

import com.intellij.openapi.vfs.VirtualFile;
import com.oic.plugin.multidimens.common.view.tree.StyleTreeNode;
import com.oic.plugin.multidimens.logic.LogicFactory;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by FRAMGIA\pham.van.khac on 9/30/16.
 */
public class VirtualFileDimen {
    public String name = "";

    public VirtualFile virtualFile = null;

    public StyleTreeNode node = null;

    public boolean selected = false;

    public VirtualFileDimen(String name) {
        this.name = name;
        this.node = new StyleTreeNode(this);
    }

    /**
     * @param virtualFile dimens.xml file
     */
    public VirtualFileDimen(VirtualFile virtualFile) {
        this.virtualFile = virtualFile;
        this.name = virtualFile.getParent().getName();
        this.node = new StyleTreeNode(this);
    }

    /**
     * @return empty string if virtualFile is null
     */
    public String getPath() {
        if (virtualFile == null) {
            return "";
        }
        return virtualFile.getPath();
    }

    /**
     * @return 1 if dimen value is missing
     */
    public float getDimen() {
        String folderValueBase = virtualFile.getParent().getName();
        float value = (float)LogicFactory.getLogicXmlRead().getDimenOfValueFolder(folderValueBase);
        return Math.max(1f, value);
    }

    @Override
    public String toString() {
        return name;
    }
}
