package com.oic.plugin.multidimens.common;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by FRAMGIA\pham.van.khac on 9/30/16.
 */
public class MouseUtils {

    public static void addMouseEvent(JTree tree, OnJTreeMouseClickListener listener) {
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();

                JTree tree = (JTree) e.getSource();
                TreePath path = tree.getPathForLocation(x, y);
                if (path == null) {
                    return;
                }

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

                if (SwingUtilities.isRightMouseButton(e)) {
                    listener.onNodeRightMouseClick(e, node);
                    return;
                }

                if (SwingUtilities.isLeftMouseButton(e)) {
                    listener.onNodeLeftMouseClick(e, node);
                    return;
                }
            }
        };
        tree.addMouseListener(ma);
    }

    public interface OnJTreeMouseClickListener {

        void onNodeLeftMouseClick(MouseEvent event, DefaultMutableTreeNode node);

        void onNodeRightMouseClick(MouseEvent event, DefaultMutableTreeNode node);

    }
}
