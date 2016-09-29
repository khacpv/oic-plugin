package com.oic.plugin.multidimens.sample;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.oic.plugin.multidimens.action.handler.MyTypedHandler;

/**
 * Created by FRAMGIA\pham.van.khac on 9/29/16.
 */
public class EditorIllustration extends AnAction {

    static {
        final EditorActionManager actionManager = EditorActionManager.getInstance();
        final TypedAction typedAction = actionManager.getTypedAction();
        typedAction.setupHandler(new MyTypedHandler());
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // get all required data from data key
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        // get document, caret, selection
        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();

        CaretModel caretModel = editor.getCaretModel();
        LogicalPosition logicalPosition = caretModel.getLogicalPosition();
        VisualPosition visualPosition = caretModel.getVisualPosition();
        int offset = caretModel.getOffset();

        Messages.showInfoMessage(logicalPosition.toString() + "\n" +
                visualPosition.toString() + "\n" +
                "Offset: " + offset, "Caret Parameters Inside The Editor");

        // runnable for replacement
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                document.replaceString(start,end,"Replacement");
            }
        };

        // replacement
        WriteCommandAction.runWriteCommandAction(project,runnable);
        selectionModel.removeSelection();
    }

    @Override
    public void update(final AnActionEvent e) {
        // get data key
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        // set visibility only has project and editor
        e.getPresentation().setVisible(project != null && editor != null && editor.getSelectionModel().hasSelection());
    }
}
