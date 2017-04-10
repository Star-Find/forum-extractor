package net.starfind.forumextractor.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.starfind.forumextractor.model.Forum;
import net.starfind.forumextractor.parser.ForumParser;
import net.starfind.forumextractor.parser.ParsedForumPage;

@Service
public class ForumParserService {
	
	@Autowired
	private ForumParser forumParser;
	
	@Autowired
	private RequestService requestService;
	
	public Future<Forum> parseForum(String forumId) throws IOException {
		CompletableFuture<Forum> future = new CompletableFuture<>();
		requestService.requestForumPage(forumId, 1, is -> {
			try {
				ParsedForumPage page = forumParser.parseForumPage(is);
				Forum forum = new Forum();
				forum.setName(page.getName());
				if (page.getPageCount() != 1) {
					//TODO: Request the other pages
				}
				future.complete(forum);
			} catch (IOException ex) {
				future.completeExceptionally(ex);
			}
		});
		return future;
	}
}
