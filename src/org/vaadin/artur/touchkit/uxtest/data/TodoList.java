package org.vaadin.artur.touchkit.uxtest.data;

import java.util.ArrayList;
import java.util.List;

public class TodoList {

	public String name;

	public TodoList(String name) {
		this.name = name;
	}

	public List<Todo> todos = new ArrayList<Todo>();

	public int getTotalCount() {
		return todos.size();
	}

	public int getCompletedCount() {
		int done = 0;
		for (Todo t : todos) {
			if (t.complete) {
				done++;
			}
		}
		return done;
	}
}
