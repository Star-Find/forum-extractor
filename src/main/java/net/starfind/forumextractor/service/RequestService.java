package net.starfind.forumextractor.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Consumer;

public interface RequestService {
	public void requestPage (URL pageUrl, Consumer<InputStream> onSuccess) throws IOException;
	
	public void requestForumPage (String id, int page, Consumer<InputStream> onSuccess) throws IOException;
	
	public void requestTopicPage (String id, int page, Consumer<InputStream> onSuccess) throws IOException;
}
