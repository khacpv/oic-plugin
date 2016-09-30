package com.oic.plugin.multidimens.data;

import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by FRAMGIA\pham.van.khac on 9/30/16.
 */
public class VirtualFileDimen {
    public String name = "";

    public VirtualFile virtualFile = null;

    public DefaultMutableTreeNode node = null;

    public boolean selected = false;

    public VirtualFileDimen(String name){
        this.name = name;
        this.node = new DefaultMutableTreeNode(this);
    }

    public VirtualFileDimen(VirtualFile virtualFile){
        this.virtualFile = virtualFile;
        this.name = virtualFile.getName();
        this.node = new DefaultMutableTreeNode(this);
    }

    /**
     * @return empty string if virtualFile is null
     */
    public String getPath(){
        if(virtualFile == null){
            return "";
        }
        return virtualFile.getPath();
    }

    @Override
    public String toString() {
        return name;
    }
}
