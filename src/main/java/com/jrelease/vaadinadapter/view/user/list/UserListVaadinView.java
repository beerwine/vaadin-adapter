package com.jrelease.vaadinadapter.view.user.list;

import com.jrelease.vaadinadapter.view.user.list.mvp.UserListPresenter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.VaadinView;

@Component
@Scope("prototype")
@VaadinView(UserListVaadinView.NAVIGATOR_NAME)
@SuppressWarnings("serial")
public class UserListVaadinView extends CustomComponent implements View {
    public static final String NAVIGATOR_NAME = "";

    @Autowired
    private UserListPresenter presenter;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        presenter.onViewEnter();
        setCompositionRoot(presenter.getView());
    }
}
