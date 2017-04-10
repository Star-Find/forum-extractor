package net.starfind.forumextractor.http;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Configuration
@Validated
@ConfigurationProperties(prefix="http")
public class HttpConfiguration {

	public static class Authorisation {
		
		private String cookieName;
		
		private String cookieValue;

		public String getCookieName() {
			return cookieName;
		}

		public String getCookieValue() {
			return cookieValue;
		}

		public void setCookieName(String cookieName) {
			this.cookieName = cookieName;
		}

		public void setCookieValue(String cookieValue) {
			this.cookieValue = cookieValue;
		}
	}
	
	@URL
	@NotBlank
	private String baseUrl;
	
	@NotBlank
	private String forumPath;
	
	@NotBlank
	private String topicPath;
	
	private Authorisation auth;

	public String getBaseUrl() {
		return baseUrl;
	}

	public String getForumPath() {
		return forumPath;
	}

	public String getTopicPath() {
		return topicPath;
	}

	public Authorisation getAuth() {
		return auth;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setForumPath(String forumPath) {
		this.forumPath = forumPath;
	}

	public void setTopicPath(String topicPath) {
		this.topicPath = topicPath;
	}

	public void setAuth(Authorisation auth) {
		this.auth = auth;
	}
}
