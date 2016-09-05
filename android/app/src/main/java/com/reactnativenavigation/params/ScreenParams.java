package com.reactnativenavigation.params;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.List;

public class ScreenParams {
    public String screenId;
    public List<TitleBarButtonParams> rightButtons;
    public TitleBarLeftButtonParams leftButton;
    public boolean overrideBackPressInJs;
    public String title;
    public StyleParams styleParams;
    public List<TopTabParams> topTabParams;
    public String fragmentCreatorClassName;
    public Bundle fragmentCreatorPassProps;
    public boolean animateScreenTransitions;
    public FabParams fabParams;
    public String tabLabel;
    public Drawable tabIcon;

    public NavigationParams navigationParams;

    public boolean hasTopTabs() {
        return topTabParams != null && !topTabParams.isEmpty();
    }

    public boolean isFragmentScreen() {
        return fragmentCreatorClassName != null;
    }

    public String getScreenInstanceId() {
        return navigationParams.screenInstanceId;
    }

    public String getNavigatorId() {
        return navigationParams.navigatorId;
    }

    public String getNavigatorEventId() {
        return navigationParams.navigatorEventId;
    }
}
