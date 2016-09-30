package com.oic.plugin.multidimens.plugin.sample;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.ui.MessageType;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Created by khacpham on 9/27/16.
 */
public class PasteAction extends EditorAction {


    public PasteAction(EditorActionHandler defaultHandler) {
        super(defaultHandler);
    }

    public PasteAction() {
        this(new StylePasteHandler());
    }

    private static class StylePasteHandler extends EditorWriteActionHandler {
        private StylePasteHandler() {
        }

        @Override
        public void executeWriteAction(Editor editor, DataContext dataContext) {
            Document document = editor.getDocument();

            if (!document.isWritable()) {
                return;
            }

            // get text from clipboard
            String source = getCopiedText();
            if (source == null) {
                StylerUtils.showBalloonPopup(dataContext, Consts.ERROR_CLIPBOARD, MessageType.ERROR);
                return;
            }

            // get result
            try {
                String styleName = getStyleName();
                if (styleName == null || styleName.isEmpty()) {
                    StylerUtils.showBalloonPopup(dataContext, Consts.ERROR_NAME, MessageType.ERROR);
                    return;
                }
                String output = StylerEngine.style(styleName, source);
                // delete text that is selected now
                deleteSelectedText(editor, document);
                CaretModel caretModel = editor.getCaretModel();
                // insert new duplicated string into the document
                document.insertString(caretModel.getOffset(), output);
                // move caret to the end of inserted text
                caretModel.moveToOffset(caretModel.getOffset() + output.length());
                // scroll to the end of inserted text
                editor.getScrollingModel().scrollToCaret(ScrollType.RELATIVE);
            } catch (ParserConfigurationException | TransformerException e) {
                e.printStackTrace();
                StylerUtils.showBalloonPopup(dataContext, Consts.XML_ERROR, MessageType.ERROR);
            } catch (Exception e) {
                e.printStackTrace();
                StylerUtils.showBalloonPopup(dataContext, Consts.WRONG_INPUT, MessageType.ERROR);
            }
        }

        private static String getStyleName() {
            return (String) JOptionPane.showInputDialog(
                    new JFrame(), Consts.DIALOG_NAME_CONTENT,
                    Consts.DIALOG_NAME_TITLE,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null, "");
        }

        private String getCopiedText() {
            try {
                return (String) CopyPasteManager.getInstance().getContents().getTransferData(DataFlavor.stringFlavor);
            } catch (NullPointerException | IOException | UnsupportedFlavorException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void deleteSelectedText(Editor editor, Document document) {
            SelectionModel selectionModel = editor.getSelectionModel();
            document.deleteString(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd());
        }
    }
}