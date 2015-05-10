package com.jrelease.vaadinadapter.view.user.edit.mvp;

import com.jrelease.vaadinadapter.model.AuthMethod;
import com.jrelease.vaadinadapter.model.User;
import com.jrelease.vaadinadapter.view.user.authmethod.AuthMethodHelper;
import com.jrelease.vaadinadapter.view.user.edit.helper.UserAuthMethodAdapter;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Scope("prototype")
@SuppressWarnings("serial")
public class UserEditViewImpl extends VerticalLayout implements UserEditView {
    private static final Logger log = LoggerFactory.getLogger(UserEditViewImpl.class);

    private UserEditHandler handler;
    private UserEditModel model;

    // --- UI ---
    private final Table authMethodsTable = new Table("Certification methods:");
    private final TextField name = new TextField("User's name:");

    // --- Columns in table ---
    private static final String COLUMN_METHOD = "method_col";
    private static final String COLUMN_AUTHENTICATION = "authentication_col";
    private static final String COLUMN_AUTHORIZATION = "authorization_col";

    private static final String PROPERTY_NAME = "name";

    /** Adapters contain binders: {@code BeanFieldGroup<UserAuthMethodAdapter>} */
    private List<UserAuthMethodAdapter> authMethodAdapters;
    private BeanFieldGroup<User> userBinder = new BeanFieldGroup<User>(User.class);

    @Override
    public void setHandler(UserEditHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setModel(UserEditModel model) {
        this.model = model;
    }

    @Override
    public void buildView() {
        initLayout();
        bind();
    }
    
    private void initLayout() {
        setMargin(true);

        /** Name field */
        addComponent(name);

        /** Table */
        addComponent(authMethodsTable);
        authMethodsTable.setHeight(20, Unit.EM);

        authMethodsTable.addGeneratedColumn(COLUMN_METHOD, new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                AuthMethod method = (AuthMethod) source.getItem(itemId).getItemProperty(UserAuthMethodAdapter.METHOD).getValue();
                return new Label(AuthMethodHelper.getHumanizedName(method));
            }
        });

        authMethodsTable.addGeneratedColumn(COLUMN_AUTHENTICATION, new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                return source.getItem(itemId).getItemProperty(UserAuthMethodAdapter.AUTHENTICATION_CHECKBOX).getValue();
            }
        });
        
        authMethodsTable.addGeneratedColumn(COLUMN_AUTHORIZATION, new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object itemId, Object columnId) {
                return source.getItem(itemId).getItemProperty(UserAuthMethodAdapter.AUTHORIZATION_CHECKBOX).getValue();
            }
        });   
        
        authMethodsTable.setColumnHeader(COLUMN_METHOD, "Method");
        authMethodsTable.setColumnHeader(COLUMN_AUTHENTICATION, "Authentication");
        authMethodsTable.setColumnHeader(COLUMN_AUTHORIZATION, "Authorization");

        /** Button row */
        HorizontalLayout buttons = new HorizontalLayout();
        addComponent(buttons);
        buttons.setMargin(true);

        /** Save */
        Button saveButton = new Button("Save and close");
        buttons.addComponent(saveButton);
        saveButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    commit();
                    handler.onSave();
                    Notification.show("Saved", Notification.Type.HUMANIZED_MESSAGE);
                } catch (CommitException e) {
                    log.error("Exception when commiting user's data.", e);
                    Notification.show("Save failed. " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        /** Reset */
        Button resetButton = new Button("Reset form");
        buttons.addComponent(resetButton);
        resetButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                resetForm();
            }
        });

        /** Cancel */
        Button cancelButton = new Button("Cancel");
        buttons.addComponent(cancelButton);
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                handler.onCancel();
            }
        });
    }
    
    private void bind() {
        final User user = model.getUser();

        /** Name */
        userBinder.setItemDataSource(user);
        userBinder.bind(name, PROPERTY_NAME);

        /** Table */
        authMethodAdapters = new ArrayList<UserAuthMethodAdapter>(AuthMethod.values().length);
        for (AuthMethod method : AuthMethod.values()) {
            authMethodAdapters.add(new UserAuthMethodAdapter(user, method));
        }
        
        final BeanItemContainer<UserAuthMethodAdapter> authMethodsSource = new BeanItemContainer<UserAuthMethodAdapter>(UserAuthMethodAdapter.class);
        authMethodsSource.addAll(authMethodAdapters);
        authMethodsTable.setContainerDataSource(authMethodsSource);
        
        authMethodsTable.setVisibleColumns(COLUMN_METHOD, COLUMN_AUTHENTICATION, COLUMN_AUTHORIZATION);
    }

    /**
     * Data are saved to model.
     *
     * @throws CommitException
     */
    private void commit() throws CommitException {
        // name
        userBinder.commit();

        // checkboxes
        for (UserAuthMethodAdapter adapter : authMethodAdapters) {
            adapter.getBinder().commit();
        }
    }

    /**
     * Resets values in the form
     */
    public void resetForm() {
        // name
        userBinder.discard();

        // checkboxes
        for (UserAuthMethodAdapter adapter : authMethodAdapters) {
            adapter.getBinder().discard();
        }
    }
}
