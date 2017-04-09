package net.starfind.forumextractor.request.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.starfind.forumextractor.request.RequestService;

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

}
