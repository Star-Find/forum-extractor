package net.starfind.forumextractor.model;

import java.time.LocalDateTime;

public final class Post {
	
	public static class Builder {
		private String author;
		private String ipAddress;
		private String content;
		private LocalDateTime date;
		
		private Builder() {}
		
		public Builder author (String author) {
			this.author = author;
			return this;
		}
		
		public Builder ipAddress (String ipAddress) {
			this.ipAddress = ipAddress;
			return this;
		}
		
		public Builder content (String content) {
			this.content = content;
			return this;
		}
		
		public Builder date (LocalDateTime date) {
			this.date = date;
			return this;
		}
		
		public Post build () {
			Post post = new Post();
			post.author = this.author;
			post.ipAddress = this.ipAddress;
			post.content = this.content;
			post.date = this.date;
			return post;
		}
	}
	
	public static Builder builder () {
		return new Builder();
	}
	
	private String id;
	
	private String author;
	
	private String ipAddress;
	
	private String content;
	
	private LocalDateTime date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", author=" + author + ", ipAddress=" + ipAddress + ", content=" + content + ", date=" + date + "]";
	}
}
