package com.example.application.views.page3;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.example.application.MainView;

import java.io.Serializable;
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
        Grid.Column<Person> ageColumn = grid.addColumn(Person::getAge)
                .setHeader("Age");

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

        grid.addItemDoubleClickListener(event -> {
            grid.getEditor().editItem(event.getItem());
            firstNameField.focus();
        });

        grid.getEditor().addCloseListener(event -> {
            if (binder.getBean() != null) {
                //  message.setText(binder.getBean().getFirstName() + ", "+ binder.getBean().getAge());
            }
        });
        HorizontalLayout layout = new HorizontalLayout();
        layout.getStyle().set("border", "1px solid #9E9E9E");
        Grid<String> gridLabels = createLabels();
        layout.setPadding(false);
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.add(gridLabels, grid);
        add(layout);
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
        return Collections.singletonList(new Person("START", "Test", 1));
    }
}
