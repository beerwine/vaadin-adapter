package com.jrelease.vaadinadapter.view.user.edit;

import com.jrelease.vaadinadapter.view.user.edit.mvp.UserEditPresenter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.VaadinView;

@Component
@Scope("prototype")
@VaadinView(UserEditVaadinView.NAVIGATOR_NAME)
public class UserEditVaadinView extends CustomComponent implements View {
    public static final String NAVIGATOR_NAME = "edit";

    @Autowired
    private UserEditPresenter userEditPresenter;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        userEditPresenter.onViewEnter(event.getParameters());
        setCompositionRoot(userEditPresenter.getView());
    }
}
