package net.starfind.forumextractor.model;

import java.util.ArrayList;
import java.util.List;

public final class Forum {
	private String name;
	
	private String description;
	
	private List<Forum> subforums = new ArrayList<>();
	
	private List<Thread> threads = new ArrayList<>();

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

	public List<Forum> getSubforums() {
		return subforums;
	}

	public void setSubforums(List<Forum> subforums) {
		this.subforums = subforums;
	}

	public List<Thread> getThreads() {
		return threads;
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

	@Override
	public String toString() {
		return "Forum [name=" + name + ", description=" + description + "]";
	}
}
