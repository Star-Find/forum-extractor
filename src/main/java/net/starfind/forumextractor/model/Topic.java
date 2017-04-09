package net.starfind.forumextractor.model;

import java.util.ArrayList;
import java.util.List;

public final class Topic {

	private String name;
	
	private String description;
	
	private boolean pinned;
	
	private boolean locked;
	
	private final List<Post> posts = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPinned() {
		return pinned;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public List<Post> getPosts() {
		return posts;
	}

	@Override
	public String toString() {
		return "Topic [name=" + name + ", description=" + description + ", pinned=" + pinned
				+ ", locked=" + locked + "]";
	}
}
