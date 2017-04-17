package net.starfind.forumextractor.parser;

import java.io.IOException;
import java.io.InputStream;

public interface TopicParser {
	
	public ParsedTopicPage parseTopicPage (InputStream is, boolean firstPage) throws IOException;

}
