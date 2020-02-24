package com.example.application.views.page2;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.example.application.MainView;
@Route(value = "page-2", layout = MainView.class)
@PageTitle("Page-2")
@CssImport("styles/views/page2/page2-view.css")
public class Page2View extends Div {

    public Page2View() {
        setId("page2-view");
        add(new Label("Content placeholder"));
    }

}
