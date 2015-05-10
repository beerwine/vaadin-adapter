package com.jrelease.vaadinadapter.view.user.edit.mvp;

import com.vaadin.ui.Component;


public interface UserEditView extends Component {

    void setHandler(UserEditHandler handler);
    void setModel(UserEditModel model);
    void buildView();

    interface UserEditHandler {
        void onSave();
        void onCancel();
    }
}
