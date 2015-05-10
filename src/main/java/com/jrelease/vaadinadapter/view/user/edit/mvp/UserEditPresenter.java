package com.jrelease.vaadinadapter.view.user.edit.mvp;

import com.jrelease.vaadinadapter.model.User;
import com.jrelease.vaadinadapter.service.UserService;
import com.jrelease.vaadinadapter.view.user.list.UserListVaadinView;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("prototype")
public class UserEditPresenter {
    
    @Autowired
    private UserService userService;
    @Autowired
    private UserEditView view;

    private UserEditModel model = new UserEditModel();

    @PostConstruct
    public void init() {
        view.setHandler(new UserEditHandlerImpl());
        view.setModel(model);
    }

    public void onViewEnter(String params) {
        User user = userService.getUser(params);
        if(user != null) {
            model.setUser(user);
            view.buildView();
        } else {
            Notification.show("Bad request. User does not exist.", Notification.Type.ERROR_MESSAGE);
            navigateToUserList();
        }
    }

    private void navigateToUserList() {
        UI.getCurrent().getNavigator().navigateTo(UserListVaadinView.NAVIGATOR_NAME);
    }

    public com.vaadin.ui.Component getView() {
        return view;
    }

    private class UserEditHandlerImpl implements UserEditView.UserEditHandler {
        @Override
        public void onSave() {
            userService.saveUser(model.getUser());
            navigateToUserList();
        }

        @Override
        public void onCancel() {
            navigateToUserList();
        }
    }



}
