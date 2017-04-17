package net.starfind.forumextractor.parser.jsoup;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import net.starfind.forumextractor.model.Post;
import net.starfind.forumextractor.parser.ParsedTopicPage;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestJsoupTopicParser {

	@InjectMocks
	private JsoupTopicParser parser;
	
	@Before
	public void setUp () {
		ReflectionTestUtils.setField(parser, "baseUri", "#");
	}

	@Test
	public void testParseTopicPage() throws IOException {
		ParsedTopicPage parsedPage;
		
		try (InputStream is = getClass().getResourceAsStream("topic_page.html")) {
			parsedPage = parser.parseTopicPage(is, true);
		}
		
		assertEquals("Topic Test", parsedPage.getName());
		assertEquals(1, parsedPage.getPageCount());
		assertEquals(1, parsedPage.getPageNumber());
		assertEquals(1, parsedPage.getPosts().size());
		
		Post post = parsedPage.getPosts().get(0);
		assertEquals("1234567", post.getId());
		assertEquals("Test Account", post.getAuthor());
		assertEquals("12.34.56.78", post.getIpAddress());
		assertEquals(LocalDateTime.parse("2016-02-27T00:48:00"), post.getDate());
		assertNotNull(post.getContent());
		assertTrue(post.getContent().contains("Topic Test."));
	}

	@Test
	public void testParseTopicPage2() throws IOException {
		ParsedTopicPage parsedPage;
		
		try (InputStream is = getClass().getResourceAsStream("topic_page_2.html")) {
			parsedPage = parser.parseTopicPage(is, false);
		}
		
		assertEquals("Topic Test", parsedPage.getName());
		assertEquals(3, parsedPage.getPageCount());
		assertEquals(2, parsedPage.getPageNumber());
		assertEquals(4, parsedPage.getPosts().size());
		
		Post post = parsedPage.getPosts().get(0);
		assertEquals("100002", post.getId());
		assertEquals("Test", post.getAuthor());
		assertEquals("43.32.133.44", post.getIpAddress());
		assertEquals(LocalDateTime.parse("2017-01-30T10:21:00"), post.getDate());
		assertNotNull(post.getContent());
		assertTrue(post.getContent().contains("Test post 2."));
	}

}
