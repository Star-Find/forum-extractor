package net.starfind.forumextractor.http;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import net.starfind.forumextractor.model.Post;
import net.starfind.forumextractor.model.Topic;
import net.starfind.forumextractor.parser.ForumParser;
import net.starfind.forumextractor.parser.TopicParser;
import net.starfind.forumextractor.service.RequestService;
import net.starfind.forumextractor.service.TopicParserService;

@Component
public class HttpApplicationLauncher implements ApplicationRunner {
	
	private static Logger LOG = LoggerFactory.getLogger(HttpApplicationLauncher.class);
	
	@Autowired
	private ForumParser forumParser;
	
	@Autowired
	private TopicParserService topicParser;
	
	@Autowired
	private HttpConfiguration config;
	
	@Autowired
	private RequestService requester;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<String> forumIds = args.getOptionValues("forum");
		List<String> topicIds = args.getOptionValues("topic");
		
		LOG.info("Starting application (args="+Arrays.toString(args.getSourceArgs()));
		
		if (forumIds != null && !forumIds.isEmpty()) {
			LOG.info("Requesting forums: "+forumIds+" from "+config.getBaseUrl());
			
			for (String forumId : forumIds) {
				String forumPath = config.getForumPath().replace("<id>", forumId);
				
				URL url = new URL(config.getBaseUrl()+forumPath);
				
				requester.requestForumPage(forumId, 1, is -> {
					try {
						System.out.println(forumParser.parseForumPage(is));
					} catch (IOException ex) {
						LOG.error("Problem parsing forum "+url, ex);
					}
				});
			}
		} else if (topicIds != null && !topicIds.isEmpty()) {
			LOG.info("Requesting topics: "+topicIds+" from "+config.getBaseUrl());
			
			for (String topicId : topicIds) {
				Future<Topic> future = topicParser.parseTopic(topicId);
				try {
					Topic topic = future.get(5000, TimeUnit.MILLISECONDS);
					System.out.println(topic);
					for (Post post : topic.getPosts()) {
						System.out.println(post);
					}
				} catch (Exception ex) {
					LOG.error("Problem parsing topic "+topicId, ex);
				}
			}
		} else {
			System.err.println("Requires either --forum=<id> or --topic=<id>");
			System.exit(-1);
			return;
		}
	}
}
