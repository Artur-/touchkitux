package org.vaadin.artur.touchkit.uxtest.view;

import java.util.List;

import org.vaadin.artur.touchkit.uxtest.data.TodoList;
import org.vaadin.artur.touchkit.uxtest.ui.UxTestUI;

import com.vaadin.addon.touchkit.extensions.TouchKitIcon;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;

public class TodoListEditView extends NavigationView {
	private Button newList;
	private VerticalComponentGroup group;
	private Button doneButton;

	public TodoListEditView() {
		super("Tokka");
		doneButton = new Button("Done");

		setRightComponent(doneButton);
		doneButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UxTestUI.getNavigationManager().navigateTo(new TodoListView());
			}
		});

		newList = new Button("New list");
		newList.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				new NewListPopover().showRelativeTo(newList);
			}
		});

		group = new VerticalComponentGroup();
		// content.addComponent(group);
		CssLayout layout = new CssLayout();
		setContent(layout);

		layout.addComponent(newList);
		layout.addComponent(group);
	}

	public class NewListPopover extends Popover {
		private TextField listName;

		public NewListPopover() {
			CssLayout layout = new CssLayout();
			setContent(layout);
			layout.setWidth("100%");

			listName = new TextField("List name");
			listName.addShortcutListener(new ShortcutListener("",
					KeyCode.ENTER, null) {
				@Override
				public void handleAction(Object sender, Object target) {
					addAndClose();
				}
			});
			listName.focus();
			Button add = new Button("Add");
			add.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					addAndClose();
				}
			});

			layout.addComponents(listName, add);
		}

		protected void addAndClose() {
			UxTestUI.getTodos().add(new TodoList(listName.getValue()));
			close();
			populate();
		}
	}

	private void populate() {
		group.removeAllComponents();
		List<TodoList> lists = UxTestUI.getTodos();

		for (final TodoList list : lists) {
			Button todo = new Button(list.name);
			TouchKitIcon.removeCircle.addTo(todo);
			todo.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					UxTestUI.getTodos().remove(list);
					populate();
				}
			});

			group.addComponent(todo);
		}
	}

	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();
		populate();

	}
}
