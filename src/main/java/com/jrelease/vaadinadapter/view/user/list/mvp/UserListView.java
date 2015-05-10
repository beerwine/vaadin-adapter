package com.jrelease.vaadinadapter.view.user.list.mvp;

import com.vaadin.ui.Component;

public interface UserListView extends Component {

    
    void setHandler(UserListHandler handler);
    void setModel(UserListModel model);
    void buildView();
    
    interface UserListHandler {
        void onEditUser(String userId);
    }
}
