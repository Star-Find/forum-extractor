package net.starfind.forumextractor.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.starfind.forumextractor.model.Post;

public interface TopicParser {
	
	public List<Post> parseTopicPage (InputStream is) throws IOException;

}
