package com.oic.plugin.multidimens.application;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by FRAMGIA\pham.van.khac on 9/28/16.
 */
public class MyApplicationComp implements ApplicationComponent {
    public MyApplicationComp() {
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
        return "MyApplicationComp";
    }
}
