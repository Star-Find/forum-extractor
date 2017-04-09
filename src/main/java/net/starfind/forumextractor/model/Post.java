package net.starfind.forumextractor.model;

import java.time.LocalDateTime;

public final class Post {
	
	private String author;
	
	private String ipAddress;
	
	private String content;
	
	private LocalDateTime date;

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
	public String toString() {
		return "Post [author=" + author + ", ipAddress=" + ipAddress + ", content=" + content + ", date=" + date + "]";
	}
}
