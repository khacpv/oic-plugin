package com.oic.plugin.multidimens.common.view.cell;

import com.oic.plugin.multidimens.common.view.tree.StyleTreeNode;
import com.oic.plugin.multidimens.data.VirtualFileDimen;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by khacpham on 10/1/16.
 */
public class CellRenderer extends DefaultTreeCellRenderer {

    String defaultNodeName;

    private JLabel label;
    private URL imageUrl;

    Map<TextAttribute, Object> attributesBold;
    Map<TextAttribute, Object> attributesNormal;

    public CellRenderer(String defaultNodeName) {
        this.defaultNodeName = defaultNodeName;

        attributesBold = new HashMap<>();
        attributesBold.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_SEMIBOLD);

        attributesNormal = new HashMap<>();

        label = new JLabel();
        imageUrl = getClass().getResource("/image/selected.png");
    }

    public void setDefaultNodeName(String name) {
        this.defaultNodeName = name;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component rendererComponent = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        rendererComponent.setFont(Font.getFont(attributesNormal));
        rendererComponent.setForeground(Color.BLACK);

        if (value instanceof StyleTreeNode) {
            StyleTreeNode node = (StyleTreeNode) value;
            if(node.getUserObject() instanceof VirtualFileDimen){
                VirtualFileDimen file = (VirtualFileDimen)node.getUserObject();
                if (defaultNodeName.equalsIgnoreCase(file.name)) {
                    label.setText(file.name);
                    label.setIcon(getIconImage());
                    return label;
                }
            }
        }
        return rendererComponent;
    }

    private ImageIcon getIconImage(){
        ImageIcon imageIcon = new ImageIcon(imageUrl); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        return new ImageIcon(newimg);  // transform it back
    }
}
