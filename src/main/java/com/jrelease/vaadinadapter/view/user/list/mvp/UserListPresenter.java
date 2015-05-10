package com.jrelease.vaadinadapter.view.user.list.mvp;

import com.jrelease.vaadinadapter.service.UserService;
import com.jrelease.vaadinadapter.view.user.edit.UserEditVaadinView;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("prototype")
public class UserListPresenter {
    
    @Autowired
    private UserService userService;
    @Autowired
    private UserListView view;

    private final UserListModel model = new UserListModel();

    @PostConstruct
    public void init() {
        view.setHandler(new UserListHandlerImpl());
        view.setModel(model);
    }

    public void onViewEnter() {
        model.setUsers(userService.getUsers());
        view.buildView();
    }

    public com.vaadin.ui.Component getView() {
        return view;
    }
    
    private class UserListHandlerImpl implements UserListView.UserListHandler {
        @Override
        public void onEditUser(String userId) {
            UI.getCurrent().getNavigator().navigateTo(UserEditVaadinView.NAVIGATOR_NAME + "/" + userId);
        }
    }
}
