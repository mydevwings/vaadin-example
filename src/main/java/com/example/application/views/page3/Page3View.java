package com.example.application.views.page3;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.example.application.MainView;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Route(value = "page-3", layout = MainView.class)
@PageTitle("Page-3")
@CssImport("styles/views/page3/page3-view.css")
public class Page3View extends Div {

    public Page3View() {
        setId("page3-view");
        setupGrid();
    }

    public class Person implements Serializable {
        private static final long serialVersionUID = 8877593814795677381L;
        public String description;
        public String firstName;
        public int age;
        boolean active;

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Person(String description, String firstName, int age) {
            this.description = description;
            this.firstName = firstName;
            this.age = age;
        }
    }

    private void setupGrid() {
        //https://github.com/vaadin/book-examples/blob/master/src/com/vaadin/book/examples/component/grid/GridEditingExample.java
        Grid<Person> grid = new Grid<>();
        grid.setWidth("70%");
        List<Person> persons = getItems();
        grid.setItems(persons);
        Grid.Column<Person> nameColumn = grid.addColumn(Person::getFirstName)
                .setHeader("First Name");
        Grid.Column<Person> descriptionColumn = grid.addColumn(Person::getDescription)
                .setHeader("Description");
        Grid.Column<Person> ageColumn = grid.addColumn(Person::getAge)
                .setHeader("Age");
        Grid.Column<Person> activeColumn = grid.addColumn(Person::isActive).setHeader(new Html("<b>Activated</b>"));

        Binder<Person> binder = new Binder<>(Person.class);
        grid.getEditor().setBinder(binder);

        TextField firstNameField = new TextField();
        TextField ageField = new TextField();
// Close the editor in case of backward between components
        firstNameField.getElement()
                .addEventListener("keydown",
                        event -> grid.getEditor().cancel())
                .setFilter("event.key === 'Tab' && event.shiftKey");

        binder.forField(firstNameField)
                .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                .bind("firstName");
        nameColumn.setEditorComponent(firstNameField);

        ageField.getElement()
                .addEventListener("keydown",
                        event -> grid.getEditor().cancel())
                .setFilter("event.key === 'Tab'");
        binder.forField(ageField)
                .withConverter(
                        new StringToIntegerConverter("Age must be a number."))
                .bind("age");
        ageColumn.setEditorComponent(ageField);

        TextField descriptionField = new TextField();

        descriptionField.getElement()
                .addEventListener("keydown",
                        event -> grid.getEditor().cancel())
                .setFilter("event.key === 'Tab'");
        binder.forField(descriptionField)
                .withConverter(
                        new StringToIntegerConverter("Age must be a number."))
                .bind("description");
        descriptionColumn.setEditorComponent(descriptionField);

        createFooter(ageColumn);
        nameColumn.setHeader(new Html("<b>Name</b>"));
        nameColumn.setFooter(new Html("<b>Name</b>"));

        HeaderRow topRow = grid.prependHeaderRow();

        topRow.join(nameColumn, descriptionColumn)
                .setComponent(new Label("Basic Information"));

        topRow.join(ageColumn, activeColumn)
                .setComponent(new Label("Additional Information"));

        grid.addItemDoubleClickListener(event -> {
            grid.getEditor().editItem(event.getItem());
            firstNameField.focus();
        });

        grid.getEditor().addCloseListener(event -> {
            if (binder.getBean() != null) {
                //  message.setText(binder.getBean().getFirstName() + ", "+ binder.getBean().getAge());
            }
        });
        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_ROW_STRIPES,
                GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.MATERIAL_COLUMN_DIVIDERS);
        HorizontalLayout layout = new HorizontalLayout();
        layout.getStyle().set("border", "1px solid #9E9E9E");
        Grid<String> gridLabels = createLabels();
        layout.setPadding(false);
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.add(gridLabels, grid);
        add(layout);
    }

    private void setupHeaders(Grid grid) {

    }

    @SuppressWarnings("rawtypes")
    private void createFooter(Grid.Column column) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button button = new Button("Clear", VaadinIcon.RECYCLE.create());
        button.addClickListener(e -> Notification.show("Clear action!"));
        horizontalLayout.add(button);
        Button buttonSave = new Button("Save", VaadinIcon.DATABASE.create());
        buttonSave.addClickListener(e -> Notification.show("Save", 3000, Notification.Position.TOP_CENTER));
        horizontalLayout.add(buttonSave);
        column.setFooter(horizontalLayout);
    }


    private Grid<String> createLabels() {
        Grid<String> gridLabels = new Grid();
        gridLabels.setWidth("30%");
        gridLabels.setItems("почта");
        gridLabels.setSelectionMode(Grid.SelectionMode.NONE);
        Grid.Column<String> nameColumn = gridLabels.addColumn(String::toString)
                .setHeader("Тип политики");
        return gridLabels;
    }

    private List<Person> getItems() {
        return Arrays.asList(new Person("START", "Test", 1),new Person("START", "Test", 2));
    }
}
