package net.starfind.forumextractor.parser;

import java.util.ArrayList;
import java.util.List;

import net.starfind.forumextractor.model.Post;

public class ParsedTopicPage {

	private String name;
	
	private String description;
	
	private final List<Post> posts = new ArrayList<>();
	
	private int pageNumber = 1;
	
	private int pageCount = 1;

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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<Post> getPosts() {
		return posts;
	}

	@Override
	public String toString() {
		return "ParsedTopicPage [name=" + name + ", description=" + description + ", posts=" + posts + ", pageNumber="
				+ pageNumber + ", pageCount=" + pageCount + "]";
	}

}
