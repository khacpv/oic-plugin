package com.oic.plugin.multidimens.ui.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by FRAMGIA\pham.van.khac on 9/30/16.
 * Popup for insert a new dimens file
 */
public class InsertDimenMenu extends JPopupMenu {

    JMenuItem itemInsert;

    public InsertDimenMenu(OnInsertDimenListener listener) {
        itemInsert = new JMenuItem(new AbstractAction("Add new dimen") {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Dimen file name: ");
                listener.onInsertDimen(name);
            }
        });
        add(itemInsert);
    }

    public interface OnInsertDimenListener {
        void onInsertDimen(String name);
    }
}
