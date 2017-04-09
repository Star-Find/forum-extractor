package net.starfind.forumextractor.request.http;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("http")
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
	}
	
	@URL
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
}
