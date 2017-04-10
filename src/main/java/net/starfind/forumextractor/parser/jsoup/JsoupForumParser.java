package net.starfind.forumextractor.parser.jsoup;

import static net.starfind.forumextractor.parser.ParsedForumPage.topic;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.starfind.forumextractor.parser.ForumParser;
import net.starfind.forumextractor.parser.ParsedForumPage;

@Component
public class JsoupForumParser implements ForumParser {
	
	private static Logger LOG = LoggerFactory.getLogger(JsoupForumParser.class);
	
	@Value("${jsoup.base-uri}")
	private String baseUri;
	
	@Value("${jsoup.topic.id-regex}")
	private String topicIdRegex;
	
	private Pattern idSelector;
	
	
	public JsoupForumParser () {
		
	}
	
	@PostConstruct
	private void init () {
		idSelector = Pattern.compile(topicIdRegex);
	}
	
	private String getId (String url) {
		Matcher m = idSelector.matcher(url);
		m.find();
		return m.group(1);
	}
	
	@Override
	public ParsedForumPage parseForumPage(InputStream is) throws IOException {
		LOG.info("idRegex="+topicIdRegex+", baseUri="+baseUri);
		Document doc = Jsoup.parse(is, "UTF-8", baseUri);
		ParsedForumPage forumPage = new ParsedForumPage();
		String name = doc.select(".posts th").text();
		
		forumPage.setName(name);
		
		Element lastPage = doc.select("ul.cat-pages li > a").last();
		
		if (lastPage != null) {
			int thisPageNum = Integer.parseInt(doc.select("ul.cat-pages li > span").first().text());
			int pageCount = Integer.parseInt(lastPage.text());
			forumPage.setPageNumber(thisPageNum);
			forumPage.setPageCount(pageCount);
		}
		
		for (Element row : doc.select(".posts tr")) {
			Element topic = row.select("td.c_cat-title > a").first();
			if (topic == null || !topic.hasText()) {
				continue;
			}
			String topicName = topic.text();			
			String topicDesc = row.select("td.c_cat-title .description").text();
			String topicId = getId(topic.attr("href"));
			boolean pinned = row.hasClass("pin");
			boolean locked = row.select("img[alt=\"Locked\"]").first() != null;
			//Element lastPage = pinned.select(".c_cat-title .cat-topicpages li a").last();
			forumPage.getTopics().add(topic().id(topicId).name(topicName).description(topicDesc)
					.locked(locked).pinned(pinned).build());
			LOG.debug("Found pinned topic: name="+topicName+" desc="+topicDesc+" id="+topicId+" pinned="+pinned+" locked="+locked);
		}
		return forumPage;
	}

}
