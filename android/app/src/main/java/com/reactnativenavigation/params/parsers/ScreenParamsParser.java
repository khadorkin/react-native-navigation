package com.reactnativenavigation.params.parsers;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.reactnativenavigation.params.FabParams;
import com.reactnativenavigation.params.NavigationParams;
import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;
import com.reactnativenavigation.params.TopTabParams;
import com.reactnativenavigation.react.ImageLoader;

import java.util.List;

public class ScreenParamsParser extends Parser {
    private static final String KEY_TITLE = "title";
    private static final String KEY_SCREEN_ID = "screenId";
    private static final String KEY_NAVIGATION_PARAMS = "navigationParams";
    private static final String KEY_RIGHT_BUTTONS = "rightButtons";
    private static final String KEY_LEFT_BUTTON = "leftButton";
    private static final String KEY_FAB = "fab";
    private static final String KEY_BACK_BUTTON_HIDDEN = "backButtonHidden";
    private static final String STYLE_PARAMS = "styleParams";
    private static final String TOP_TABS = "topTabs";
    private static final String FRAGMENT_CREATOR_CLASS_NAME = "fragmentCreatorClassName";
    private static final String FRAGMENT_CREATOR_PASS_PROPS = "fragmentCreatorPassProps";
    public static final String OVERRIDE_BACK_PRESS = "overrideBackPress";

    @SuppressWarnings("ConstantConditions")
    public static ScreenParams parse(Bundle params) {
        ScreenParams result = new ScreenParams();
        result.screenId = params.getString(KEY_SCREEN_ID);
        assertKeyExists(params, KEY_NAVIGATION_PARAMS);
        result.navigationParams = new NavigationParams(params.getBundle(KEY_NAVIGATION_PARAMS));

        result.styleParams = new StyleParamsParser(params.getBundle(STYLE_PARAMS)).parse();

        result.title = params.getString(KEY_TITLE);
        result.rightButtons = parseRightButton(params);
        result.overrideBackPressInJs = params.getBoolean(OVERRIDE_BACK_PRESS, false);
        result.leftButton = parseLeftButton(params);

        result.topTabParams = parseTopTabs(params);

        if (hasKey(params, FRAGMENT_CREATOR_CLASS_NAME)) {
            result.fragmentCreatorClassName = params.getString(FRAGMENT_CREATOR_CLASS_NAME);
            result.fragmentCreatorPassProps = params.getBundle(FRAGMENT_CREATOR_PASS_PROPS);
        }

        result.fabParams = parseFab(params);

        result.tabLabel = getTabLabel(params);
        result.tabIcon = getTabIcon(params);

        result.animateScreenTransitions = params.getBoolean("animated", true);

        return result;
    }

    private static Drawable getTabIcon(Bundle params) {
        Drawable tabIcon = null;
        if (hasKey(params, "icon")) {
            tabIcon = ImageLoader.loadImage(params.getString("icon"));
        }
        return tabIcon;
    }

    private static String getTabLabel(Bundle params) {
        String tabLabel = null;
        if (hasKey(params, "label")) {
            tabLabel = params.getString("label");
        }
        return tabLabel;
    }

    private static List<TopTabParams> parseTopTabs(Bundle params) {
        List<TopTabParams> topTabParams = null;
        if (hasKey(params, TOP_TABS)) {
            topTabParams = new TopTabParamsParser().parse(params.getBundle(TOP_TABS));
        }
        return topTabParams;
    }

    private static List<TitleBarButtonParams> parseRightButton(Bundle params) {
        List<TitleBarButtonParams> rightButtons = null;
        if (hasKey(params, KEY_RIGHT_BUTTONS)) {
            rightButtons = new TitleBarButtonParamsParser().parseButtons(params.getBundle(KEY_RIGHT_BUTTONS));
        }
        return rightButtons;
    }

    private static TitleBarLeftButtonParams parseLeftButton(Bundle params) {
        TitleBarLeftButtonParams leftButton = null;
        if (hasKey(params, KEY_LEFT_BUTTON)) {
            leftButton =  new TitleBarLeftButtonParamsParser().parseSingleButton(params.getBundle(KEY_LEFT_BUTTON));

            boolean backButtonHidden = params.getBoolean(KEY_BACK_BUTTON_HIDDEN, false);
            if (backButtonHidden && leftButton.isBackButton()) {
                leftButton = null;
            }
        }
        return leftButton;
    }

    private static FabParams parseFab(Bundle params) {
        FabParams fabParams = null;
        if (hasKey(params, KEY_FAB)) {
            fabParams = new FabParamsParser().parse(params.getBundle(KEY_FAB));
        }
        return fabParams;
    }

    public List<ScreenParams> parseTabs(Bundle params) {
        return parseBundle(params, new ParseStrategy<ScreenParams>() {
            @Override
            public ScreenParams parse(Bundle screen) {
                return ScreenParamsParser.parse(screen);
            }
        });
    }
}
