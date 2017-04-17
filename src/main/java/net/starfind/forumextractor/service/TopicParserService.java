package net.starfind.forumextractor.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.starfind.forumextractor.model.Topic;
import net.starfind.forumextractor.parser.ParsedTopicPage;
import net.starfind.forumextractor.parser.TopicParser;

@Service
public class TopicParserService {
	
	private static Logger LOG = LoggerFactory.getLogger(TopicParserService.class);
	
	@Autowired
	private TopicParser topicParser;
	
	@Autowired
	private RequestService requestService;
	
	public Future<Topic> parseTopic(String topicId) throws IOException {
		CompletableFuture<Topic> future = new CompletableFuture<>();
		requestService.requestTopicPage(topicId, 1, is -> {
			try {
				ParsedTopicPage page = topicParser.parseTopicPage(is, true);
				Topic topic = new Topic();
				topic.setName(page.getName());
				topic.setDescription(page.getDescription());
				topic.getPosts().addAll(page.getPosts());
				if (page.getPageCount() != 1) {
					requestPages(topic, topicId, page.getPageCount());
				}
				future.complete(topic);
			} catch (IOException | RuntimeException ex) {
				future.completeExceptionally(ex);
			}
		});
		return future;
	}
	
	private void requestPages(Topic topic, String topicId, int count) throws IOException {
		CountDownLatch latch = new CountDownLatch(count-1);
		for (int pageNum=2;pageNum<=count;pageNum++) {
			requestService.requestTopicPage(topicId, pageNum, is -> {
				try {
					ParsedTopicPage page = topicParser.parseTopicPage(is, false);
					topic.getPosts().addAll(page.getPosts());
				} catch (IOException ex) {
					LOG.error("Problem fetching page for topic "+topicId, ex);
				} finally {
					latch.countDown();
				}
			});
		}
		try {
			latch.await();
		} catch (InterruptedException ex) {
			LOG.warn("Interrupted page request!", ex);
		}
	}
}
