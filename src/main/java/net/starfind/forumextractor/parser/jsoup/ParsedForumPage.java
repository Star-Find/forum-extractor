package net.starfind.forumextractor.parser.jsoup;

import java.util.List;

public class ParsedForumPage {

	private String name;
	
	private String description;
	
	private List<String> topicIds;
	
	private int pageNumber;
	
	private int pageCount;

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

	public List<String> getTopicIds() {
		return topicIds;
	}

	public void setTopicIds(List<String> topicIds) {
		this.topicIds = topicIds;
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

	@Override
	public String toString() {
		return "ParsedForumPage [name=" + name + ", description=" + description + ", topicIds=" + topicIds
				+ ", pageCount=" + pageCount + "]";
	}
}
