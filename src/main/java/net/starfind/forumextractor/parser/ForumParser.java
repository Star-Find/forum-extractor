package net.starfind.forumextractor.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.starfind.forumextractor.model.Topic;

public interface ForumParser {
	
	public ParsedForumPage parseForumPage (InputStream is) throws IOException;

}
