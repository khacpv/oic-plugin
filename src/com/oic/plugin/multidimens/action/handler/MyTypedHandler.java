package com.oic.plugin.multidimens.action.handler;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Created by FRAMGIA\pham.van.khac on 9/29/16.
 */
public class MyTypedHandler implements TypedActionHandler {

    @Override
    public void execute(@NotNull Editor editor, char c, @NotNull DataContext dataContext) {
        final Document document = editor.getDocument();

        Project project = editor.getProject();

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                document.insertString(0,"Typed\n");
//            }
//        };
//
//        WriteCommandAction.runWriteCommandAction(project, runnable);
    }
}
