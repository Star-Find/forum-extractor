package net.starfind.forumextractor.parser.jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import net.starfind.forumextractor.model.Post;
import net.starfind.forumextractor.parser.TopicParser;

public class JsoupTopicParser implements TopicParser {
	
	private static Logger LOG = LoggerFactory.getLogger(JsoupTopicParser.class);
	
	private static DateTimeFormatter POST_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM d y, h:m a");
	
	@Value("${jsoup.base-uri}")
	private String baseUri;
	
	@Override
	public List<Post> parseTopicPage(InputStream is) throws IOException {
		Document doc = Jsoup.parse(is, "UTF-8", baseUri);
		List<Post> posts = new ArrayList<>();
		
		int count = 0;
		Element header = null;
		for (Element row : doc.select("#topic_viewer tbody tr")) {
			if (count % 5 == 1 && row.cssSelector().startsWith("#post")) {
				header = row;
			} else if (count % 5 == 2 && header != null) {
				posts.add(extractPost(header, row));
				header = null;
			}
			count++;
		}
		LOG.info("Found "+posts.size()+" post(s)");
		return posts;
	}

	private Post extractPost (Element headerRow, Element contentRow) {
		String author = headerRow.select(".c_username .member").text();
		String ip = headerRow.select(".c_postinfo .right .desc").text();
		if (ip.startsWith("IP: ")) {
			ip = ip.substring(ip.indexOf(' ')).trim();
		}
		String dateStr = headerRow.select(".c_postinfo .left").text();
		LocalDateTime date = LocalDateTime.parse(dateStr, POST_DATE_FORMAT);
		String postContent = contentRow.select(".c_post").text();
		
		Post post = new Post();
		post.setAuthor(author);
		post.setIpAddress(ip);
		post.setDate(date);
		post.setContent(postContent);
		return post;
	}
}
