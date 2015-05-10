package com.jrelease.vaadinadapter.view.user.list.mvp;

import com.jrelease.vaadinadapter.model.AuthMethod;
import com.jrelease.vaadinadapter.model.User;
import com.jrelease.vaadinadapter.view.user.authmethod.AuthMethodHelper;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Scope("prototype")
public class UserListViewImpl extends VerticalLayout implements UserListView {

    private static final String COLUMN_CERTIFICATION_METHODS = "certificationMethods";
    private static final String COLUMN_EDIT = "edit";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ID = "id";

    private UserListHandler handler;
    private UserListModel model;

    private final Table usersTable = new Table();

    @Override
    public void setHandler(UserListHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setModel(UserListModel model) {
        this.model = model;
    }

    @Override
    public void buildView() {
        initLayout();
        bind();
    }

    private void initLayout() {
        setMargin(true);

        addComponent(usersTable);

        usersTable.addGeneratedColumn(COLUMN_CERTIFICATION_METHODS, new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                Set<AuthMethod> authenticationMethods = (Set<AuthMethod>) source.getItem(itemId).getItemProperty("authenticationMethods").getValue();
                Set<AuthMethod> authorizationMethods = (Set<AuthMethod>) source.getItem(itemId).getItemProperty("authorizationMethods").getValue();

                VerticalLayout lines = new VerticalLayout();

                HorizontalLayout row1 = new HorizontalLayout();
                lines.addComponent(row1);
                row1.addComponent(new Label("<b>Authentication methods:&nbsp;</b>", ContentMode.HTML));
                row1.addComponent(new Label(StringUtils.join(AuthMethodHelper.getHumanizedNames(authenticationMethods), ", ")));

                HorizontalLayout row2 = new HorizontalLayout();
                lines.addComponent(row2);
                row2.addComponent(new Label("<b>Authorization methods:&nbsp;</b>", ContentMode.HTML));
                row2.addComponent(new Label(StringUtils.join(AuthMethodHelper.getHumanizedNames(authorizationMethods), ", ")));

                return lines;
            }
        });

        usersTable.addGeneratedColumn(COLUMN_EDIT, new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                final String id = (String) source.getItem(itemId).getItemProperty("id").getValue();

                Button editButton = new Button("edit");
                editButton.addClickListener(new ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        handler.onEditUser(id);
                    }
                });

                return editButton;
            }
        });

        usersTable.setColumnHeader(COLUMN_ID, "ID");
        usersTable.setColumnHeader(COLUMN_NAME, "Name");
        usersTable.setColumnHeader(COLUMN_CERTIFICATION_METHODS, "Certification methods");
        usersTable.setColumnHeader(COLUMN_EDIT, "");
    }

    private void bind() {
        BeanItemContainer<User> userContainer = new BeanItemContainer<User>(User.class, model.getUsers());
        usersTable.setContainerDataSource(userContainer);

        usersTable.setVisibleColumns(COLUMN_ID, COLUMN_NAME, COLUMN_CERTIFICATION_METHODS, COLUMN_EDIT);
    }
}
