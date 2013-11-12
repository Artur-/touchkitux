package org.vaadin.artur.touchkit.uxtest.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.vaadin.artur.touchkit.uxtest.data.Todo;
import org.vaadin.artur.touchkit.uxtest.data.TodoList;
import org.vaadin.artur.touchkit.uxtest.view.TodoListView;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("touchkit")
public class UxTestUI extends UI {
	private List<TodoList> todos = new ArrayList<TodoList>();
	private NavigationManager navigationManager;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = UxTestUI.class, widgetset = "org.vaadin.artur.touchkit.uxtest.ui.widgetset.Touchkituxtest1Widgetset")
	public static class Servlet extends TouchKitServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		initDummyData();

		navigationManager = new NavigationManager();
		setContent(navigationManager);
		navigationManager.navigateTo(new TodoListView());

	}

	private void initDummyData() {
		TodoList list1 = new TodoList("Work todo");
		list1.todos.add(new Todo("Checkout snapshot"));
		list1.todos.add(new Todo("Implement wireframe"));
		list1.todos.add(new Todo("Answer survey"));
		TodoList list2 = new TodoList("Shopping list");
		list2.todos.add(new Todo("Milk"));
		list2.todos.add(new Todo("Bread"));
		list2.todos.add(new Todo("Butter"));
		todos.add(list1);
		todos.add(list2);
	}

	public static UxTestUI getCurrent() {
		return (UxTestUI) UI.getCurrent();
	}

	public static List<TodoList> getTodos() {
		return getCurrent().todos;
	}

	public static NavigationManager getNavigationManager() {
		return getCurrent().navigationManager;
	}
}