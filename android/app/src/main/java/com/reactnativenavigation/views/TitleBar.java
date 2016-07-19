package com.reactnativenavigation.views;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.reactnativenavigation.R;
import com.reactnativenavigation.core.Button;

import java.util.List;

public class TitleBar extends Toolbar {

    private Menu menu;
    private ActionMenuView actionMenuView;

    public TitleBar(Context context) {
        super(context);
        createMenu();
    }

    private void createMenu() {
        MenuInflater menuInflater = ((Activity) getContext()).getMenuInflater();
        actionMenuView = new ActionMenuView(getContext());
        menu = actionMenuView.getMenu();
        menuInflater.inflate(R.menu.stub, menu);
        addView(actionMenuView);
        // TODO: Maybe setSupportActionBar
    }

    public void setButtons(List<Button.Params> buttons) {
        menu.clear();

        Activity context = (Activity) getContext();
        for (int i = 0; i < buttons.size(); i++) {
            final Button button = new Button(context, buttons.get(i));
            // Add button in reverse order because in iOS, index 0 starts at right
            final int index = buttons.size() - i - 1;
            button.addToMenu(context, menu, index);
        }
    }
}
