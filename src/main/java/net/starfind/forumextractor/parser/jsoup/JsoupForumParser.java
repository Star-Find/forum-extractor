package net.starfind.forumextractor.parser.jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.starfind.forumextractor.model.Topic;
import net.starfind.forumextractor.parser.ForumParser;
import net.starfind.forumextractor.parser.TopicParser;
import net.starfind.forumextractor.request.RequestService;

@Component
public class JsoupForumParser implements ForumParser {
	
	private static Logger LOG = LoggerFactory.getLogger(JsoupForumParser.class);
	
	@Autowired
	private RequestService requester;
	
	@Autowired
	private TopicParser topicParser;
	
	@Value("${jsoup.base-uri}")
	private String baseUri;
	
	public JsoupForumParser () {
		
	}
	
	@Override
	public List<Topic> parseForumPage(InputStream is) throws IOException {
		Document doc = Jsoup.parse(is, "UTF-8", baseUri);
		List<Topic> topics = new ArrayList<>();
		for (Element pinned : doc.select(".posts tr.pin")) {
			Topic topic = new Topic();
			topic.setPinned(true);
			String name = pinned.select(".c_cat-title a").text();
			LOG.info("Found topic: "+name);
			String desc = pinned.select(".c_cat-title .description").text();
			String pageUrl = pinned.select(".c_cat-title a").attr("href");
			topic.setName(name);
			topic.setDescription(desc);
			Element lastPage = pinned.select(".c_cat-title .cat-topicpages li a").last();
			if (lastPage == null) {
				URL url = new URL(pageUrl);
				requester.requestPage(url, pageData -> {
					try {
						topic.getPosts().addAll(topicParser.parseTopicPage(pageData));
					} catch (IOException ex) {
						LOG.error("Problem parsing topic page "+pageUrl, ex);
					}
				});
			} else {
				int count = Integer.parseInt(lastPage.text());
				for (int i=1;i<count;i++) {
					URL url = new URL(pageUrl+"/"+i+"/");
					requester.requestPage(url, pageData -> {
						try {
							topic.getPosts().addAll(topicParser.parseTopicPage(pageData));
						} catch (IOException ex) {
							LOG.error("Problem parsing topic page "+url, ex);
						}
					});
				}
			}
			topics.add(topic);
		}
		return topics;
	}

}
