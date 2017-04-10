package net.starfind.forumextractor.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.starfind.forumextractor.service.RequestService;

@Service
public class HttpRequestService implements RequestService {
	
	private static Logger LOG = LoggerFactory.getLogger(HttpRequestService.class);
	
	@Autowired
	private HttpConfiguration config;

	@Override
	public void requestPage(URL pageUrl, Consumer<InputStream> onSuccess) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) pageUrl.openConnection();
		
		if (config.getAuth() != null) {
			String cookeName = config.getAuth().getCookieName();
			String cookieValue = config.getAuth().getCookieValue();
			conn.setRequestProperty("Cookie", cookeName+"="+cookieValue+";");
		}
		
		int responseCode = conn.getResponseCode();
		LOG.info("Page "+pageUrl+" returned "+responseCode);
		if (responseCode == 200) {
			onSuccess.accept(conn.getInputStream());
		} else {
			throw new IOException("Failed to retrieve "+pageUrl+": code="+responseCode);
		}
	}

	@Override
	public void requestForumPage(String id, int page, Consumer<InputStream> onSuccess) throws IOException {
		String forumPath = config.getForumPath().replace("<id>", id).replace("<page>", Integer.toString(page));
		
		URL url = new URL(config.getBaseUrl()+forumPath);
		
		requestPage(url, onSuccess);
	}

	@Override
	public void requestTopicPage(String id, int page, Consumer<InputStream> onSuccess) throws IOException {
		String topicPath = config.getTopicPath().replace("<id>", id).replace("<page>", Integer.toString(page));
		
		URL url = new URL(config.getBaseUrl()+topicPath);
		
		requestPage(url, onSuccess);
		
	}

}
