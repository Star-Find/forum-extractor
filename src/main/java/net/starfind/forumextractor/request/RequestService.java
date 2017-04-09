package net.starfind.forumextractor.request;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Consumer;

public interface RequestService {
	public void requestPage (URL pageUrl, Consumer<InputStream> onSuccess) throws IOException;
}
