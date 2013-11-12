package org.vaadin.artur.touchkit.uxtest.view;

import org.vaadin.artur.touchkit.uxtest.data.Todo;
import org.vaadin.artur.touchkit.uxtest.data.TodoList;

import com.vaadin.addon.touchkit.extensions.TouchKitIcon;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Switch;
import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.addon.touchkit.ui.TabBarView.SelectedTabChangeEvent;
import com.vaadin.addon.touchkit.ui.TabBarView.SelectedTabChangeListener;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;

public class TodoItemsView extends NavigationView {

	private TodoList list;
	private VerticalComponentGroup todoItems;
	private Filter filter = Filter.ALL;

	public enum Filter {
		ALL, OPEN, COMPLETE;

		public boolean include(boolean complete) {
			return complete && (this == ALL || this == COMPLETE) || !complete
					&& (this == ALL || this == OPEN);
		}
	}

	public TodoItemsView(TodoList list) {
		this.list = list;
		final TextField newItem = new TextField();
		newItem.setWidth("100%");
		newItem.setInputPrompt("New item");
		newItem.setImmediate(true);
		newItem.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = newItem.getValue();
				if (value == null || value.isEmpty()) {
					return;
				}

				Todo todo = new Todo(value);
				TodoItemsView.this.list.todos.add(todo);
				newItem.setValue("");
				populate();
			}
		});
		HorizontalLayout hl = new HorizontalLayout(newItem);
		hl.setMargin(true);
		hl.setWidth("100%");

		todoItems = new VerticalComponentGroup();
		TabBarView bar = new TabBarView();
		// I have no idea where these label go if anywhere?
		final Tab tabAll = bar.addTab(new Label("WAT?"), "All");
		final Tab tabTodo = bar.addTab(new Label("FOO"), "Todo");
		final Tab tabDone = bar.addTab(new Label("BAR"), "Done");

		TouchKitIcon.asterisk.addTo(tabAll);
		TouchKitIcon.checkEmpty.addTo(tabTodo);
		TouchKitIcon.check.addTo(tabDone);

		CssLayout content = new CssLayout();
		content.setSizeFull();
		content.addComponents(hl, todoItems);
		setContent(content);

		setToolbar(bar);
		bar.addListener(new SelectedTabChangeListener() {
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				Tab selected = event.getTabSheet().getSelelectedTab();
				if (selected == tabAll) {
					filter = Filter.ALL;
				} else if (selected == tabDone) {
					filter = Filter.COMPLETE;
				} else if (selected == tabTodo) {
					filter = Filter.OPEN;
				}
				populate();
			}
		});
		bar.setSelectedTab(tabAll);
	}

	private void populate() {
		todoItems.removeAllComponents();
		for (final Todo t : list.todos) {
			if (filter.include(t.complete)) {
				final Switch sw = new Switch();
				sw.setCaption(t.name);
				sw.setValue(t.complete);
				sw.addValueChangeListener(new ValueChangeListener() {

					@Override
					public void valueChange(ValueChangeEvent event) {
						t.complete = sw.getValue();
					}
				});
				todoItems.addComponent(sw);
			}
		}
	}
}
