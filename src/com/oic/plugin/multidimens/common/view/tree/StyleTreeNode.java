package com.oic.plugin.multidimens.common.view.tree;

import com.oic.plugin.multidimens.data.VirtualFileDimen;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by khacpham on 10/1/16.
 */
public class StyleTreeNode extends DefaultMutableTreeNode {

    public StyleTreeNode(VirtualFileDimen file){
        super(file);
    }
}
