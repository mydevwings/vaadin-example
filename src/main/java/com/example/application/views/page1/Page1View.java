package com.example.application.views.page1;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.example.application.MainView;
@Route(value = "page-1", layout = MainView.class)
@PageTitle("Page1")
@CssImport("styles/views/page1/page1-view.css")
public class Page1View extends Div {

    public Page1View() {
        setId("page1-view");
        add(new Label("Content placeholder"));
    }

}
