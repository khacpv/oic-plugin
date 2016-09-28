package com.oic.plugin.multidimens.module;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.ModuleComponent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.JDOMExternalizer;
import org.jetbrains.annotations.NotNull;

/**
 * Created by FRAMGIA\pham.van.khac on 9/28/16.
 */
public class MyModuleComp implements ModuleComponent {
    public MyModuleComp(Module module) {
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here

    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "MyModuleComp";
    }

    @Override
    public void projectOpened() {
        // called when project is opened
    }

    @Override
    public void projectClosed() {
        // called when project is being closed
    }

    @Override
    public void moduleAdded() {
        // Invoked when the module corresponding to this component instance has been completely
        // loaded and added to the project.
    }
}
