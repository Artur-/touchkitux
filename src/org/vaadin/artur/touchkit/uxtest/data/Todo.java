package org.vaadin.artur.touchkit.uxtest.data;

public class Todo {
	public String name;
	public boolean complete;

	public Todo(String name) {
		this(name, false);
	}

	public Todo(String name, boolean complete) {
		super();
		this.name = name;
		this.complete = complete;
	}

}