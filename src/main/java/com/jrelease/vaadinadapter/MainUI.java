package com.jrelease.vaadinadapter;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.DiscoveryNavigator;

@Component
@Scope("prototype")
@Theme("valo")
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();
        Page.getCurrent().setTitle("Adapter pattern demo");
        DiscoveryNavigator navigator = new DiscoveryNavigator(this, this);
    }

}
