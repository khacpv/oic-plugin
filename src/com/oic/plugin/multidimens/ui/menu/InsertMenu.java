package com.oic.plugin.multidimens.ui.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by FRAMGIA\pham.van.khac on 9/30/16.
 * Popup for insert a new dimens file
 */
@Deprecated
public class InsertMenu extends JPopupMenu {

    private static final String MENU_ADD_VALUES = "Add dimens.xml";

    public InsertMenu(OnInsertDimenListener listener) {

        JMenuItem itemInsert = new JMenuItem(new AbstractAction(MENU_ADD_VALUES) {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Dimen file name: ");
                listener.onInsertDimen(name);
            }
        });

        this.add(itemInsert);
    }

    public interface OnInsertDimenListener {
        void onInsertDimen(String name);
    }
}
