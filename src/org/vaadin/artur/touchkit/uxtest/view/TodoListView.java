package org.vaadin.artur.touchkit.uxtest.view;

import java.util.List;

import org.vaadin.artur.touchkit.uxtest.data.TodoList;
import org.vaadin.artur.touchkit.uxtest.ui.UxTestUI;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class TodoListView extends NavigationView {
	private VerticalComponentGroup group;
	private Button editButton;

	public TodoListView() {
		super("Tokka");
		editButton = new Button("Edit");
		setRightComponent(editButton);
		editButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UxTestUI.getNavigationManager().navigateTo(
						new TodoListEditView());
			}
		});

		group = new VerticalComponentGroup();
		// content.addComponent(group);
		setContent(group);
	}

	private void populate() {
		group.removeAllComponents();
		List<TodoList> lists = UxTestUI.getTodos();

		for (TodoList list : lists) {
			NavigationButton todo = new NavigationButton(list.name);
			todo.setDescription(list.getCompletedCount() + "/"
					+ list.getTotalCount());
			todo.setTargetView(new TodoItemsView(list));

			group.addComponent(todo);
		}
	}

	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();
		// This can't be the best way, but it works
		populate();

	}
}
