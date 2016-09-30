package com.oic.plugin.multidimens.ui.menu;

import com.oic.plugin.multidimens.data.VirtualFileDimen;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by FRAMGIA\pham.van.khac on 9/30/16.
 */
public class EditDeleteMenu extends JPopupMenu {

    JMenuItem itemEdit;

    JMenuItem itemDelete;

    public EditDeleteMenu(VirtualFileDimen file,OnInsertDimenListener listener) {
        itemEdit = new JMenuItem(new AbstractAction("Edit") {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Dimen file name: ", file.name);
                if(name == null){
                    name = file.name;
                }
                listener.onEditDimen(name);
            }
        });
        itemDelete = new JMenuItem(new AbstractAction("Delete") {
            public void actionPerformed(ActionEvent e) {
                listener.onDeleteDimen();
            }
        });
        add(itemEdit);
        add(itemDelete);
    }

    public interface OnInsertDimenListener {

        void onEditDimen(String name);

        void onDeleteDimen();
    }
}
