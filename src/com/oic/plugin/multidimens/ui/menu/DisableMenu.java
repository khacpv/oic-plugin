package com.oic.plugin.multidimens.ui.menu;

import com.oic.plugin.multidimens.data.VirtualFileDimen;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by FRAMGIA\pham.van.khac on 9/30/16.
 */
public class DisableMenu extends JPopupMenu {

    JMenuItem itemDisable;

    public DisableMenu(VirtualFileDimen file, OnDisableMenuListener listener) {
        String title = "Don't convert";
        if(file.excluded){
            title = "Convert";
        }
        itemDisable = new JMenuItem(new AbstractAction(title) {
            public void actionPerformed(ActionEvent e) {
                listener.onDisableEvent();
            }
        });
        add(itemDisable);
    }

    public interface OnDisableMenuListener {

        void onDisableEvent();
    }
}
