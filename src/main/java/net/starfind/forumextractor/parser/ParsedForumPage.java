package net.starfind.forumextractor.parser;

import java.util.ArrayList;
import java.util.List;

public class ParsedForumPage {
	
	public static class Topic {
		private String id;
		
		private String description;
		
		private String name;
		
		private boolean pinned;
		
		private boolean locked;

		public String getId() {
			return id;
		}

		public String getDescription() {
			return description;
		}

		public String getName() {
			return name;
		}

		public boolean isPinned() {
			return pinned;
		}

		public boolean isLocked() {
			return locked;
		}

		@Override
		public String toString() {
			return "Topic [id=" + id + ", description=" + description + ", name=" + name + ", pinned=" + pinned
					+ ", locked=" + locked + "]";
		}
	}

	private String name;
	
	private String description;
	
	private final List<Topic> topics = new ArrayList<>();
	
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

	public List<Topic> getTopics() {
		return topics;
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
		return "ParsedForumPage [name=" + name + ", description=" + description + ", topics=" + topics
				+ ", pageCount=" + pageCount + "]";
	}
	
	public static TopicBuilder topic() {
		return new TopicBuilder();
	}
	
	public static class TopicBuilder {
		private String id;
		private String name;
		private String description;
		private boolean pinned;
		private boolean locked;
		
		private TopicBuilder() {
			
		}
		
		public TopicBuilder id(String id) {
			this.id = id;
			return this;
		}
		
		public TopicBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public TopicBuilder description(String description) {
			this.description = description;
			return this;
		}
		
		public TopicBuilder pinned() {
			return pinned(true);
		}
		
		public TopicBuilder pinned(boolean pinned) {
			this.pinned = pinned;
			return this;
		}
		
		public TopicBuilder locked() {
			return locked(true);
		}
		
		public TopicBuilder locked(boolean locked) {
			this.locked = locked;
			return this;
		}
		
		public Topic build () {
			Topic topic = new Topic();
			topic.id = this.id;
			topic.name = this.name;
			topic.description = this.description;
			topic.pinned = this.pinned;
			topic.locked = this.locked;
			return topic;
		}
	}
}
