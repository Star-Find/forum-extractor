package net.starfind.forumextractor.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.starfind.forumextractor.model.Post;
import net.starfind.forumextractor.model.Topic;
import net.starfind.forumextractor.parser.ParsedTopicPage;
import net.starfind.forumextractor.parser.TopicParser;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestTopicParserService {
	
	@Mock
	private TopicParser topicParser;

	@Mock
	private RequestService requestService;
	
	@InjectMocks
	private TopicParserService topicParserService;
	
	@Before
	public void setup () throws IOException {
		MockitoAnnotations.initMocks(this);
		
		doAnswer(new Answer<Void>() {
			@SuppressWarnings("unchecked")
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				System.out.println("Called...");
				Consumer<InputStream> onSuccess = invocation.getArgumentAt(2, Consumer.class);
				onSuccess.accept(mock(InputStream.class));
				return null;
			}
		}).when(requestService).requestTopicPage(anyString(), anyInt(), any());
	}
	
	
	@Test(timeout=3000)
	public void testSinglePageTopic () throws Exception {
		ParsedTopicPage page = new ParsedTopicPage();
		page.setName("Test topic");
		page.setDescription("Test description");
		Post expectedPost1 = Post.builder().author("").content("")
				.ipAddress("").date(LocalDateTime.parse("2017-04-13T20:00:00")).build();
		page.getPosts().add(expectedPost1);
		
		when(topicParser.parseTopicPage(any(), anyBoolean())).thenReturn(page);
		
		Future<Topic> topicResult = topicParserService.parseTopic("1234567");
		
		verify(requestService).requestTopicPage(eq("1234567"), eq(1), any());
		
		Topic topic = topicResult.get(1000, TimeUnit.SECONDS);
		
		assertEquals("Test topic", topic.getName());
		assertEquals("Test description", topic.getDescription());
		assertEquals(1, topic.getPosts().size());
		
		Post post = topic.getPosts().get(0);
		assertEquals(expectedPost1, post);
	}
	
	
	@Test(timeout=3000)
	public void testMultiPageTopic () throws Exception {
		ParsedTopicPage page1 = new ParsedTopicPage();
		page1.setName("Test topic");
		page1.setDescription("Test description");
		page1.setPageCount(2);
		page1.setPageNumber(1);
		
		Post expectedPost1 = Post.builder().author("test1").content("ergerufuyegrsf")
				.ipAddress("12.34.56.78").date(LocalDateTime.parse("2017-04-11T20:00:00")).build();
		Post expectedPost2 = Post.builder().author("test2").content("drthwefftb esrvserbvserfv rev")
				.ipAddress("13.45.22.33").date(LocalDateTime.parse("2017-04-23T20:00:00")).build();
		page1.getPosts().add(expectedPost1);
		page1.getPosts().add(expectedPost2);

		ParsedTopicPage page2 = new ParsedTopicPage();
		page2.setName("Test topic");
		page2.setDescription("Test description");
		page1.setPageCount(2);
		page1.setPageNumber(2);
		
		Post expectedPost3 = Post.builder().author("test3").content("rtrtgdrtg")
				.ipAddress("123.72.222.37").date(LocalDateTime.parse("2017-04-25T20:00:00")).build();
		Post expectedPost4 = Post.builder().author("test4").content("erg frvserv regr")
				.ipAddress("12.34.56.78").date(LocalDateTime.parse("2017-05-13T20:00:00")).build();
		page2.getPosts().add(expectedPost3);
		page2.getPosts().add(expectedPost4);
		
		when(topicParser.parseTopicPage(any(), eq(true))).thenReturn(page1);
		when(topicParser.parseTopicPage(any(), eq(false))).thenReturn(page2);
		
		Future<Topic> topicResult = topicParserService.parseTopic("9876542");
		
		verify(requestService).requestTopicPage(eq("9876542"), eq(1), any());
		verify(requestService).requestTopicPage(eq("9876542"), eq(2), any());
		
		Topic topic = topicResult.get(1000, TimeUnit.SECONDS);
		
		assertEquals("Test topic", topic.getName());
		assertEquals("Test description", topic.getDescription());
		assertEquals(4, topic.getPosts().size());
		
		verifyPost(topic, 1, expectedPost1);
		verifyPost(topic, 2, expectedPost2);
		verifyPost(topic, 3, expectedPost3);
		verifyPost(topic, 4, expectedPost4);
	}
	
	
	private void verifyPost(Topic topic, int num, Post expected) {
		Post post = topic.getPosts().get(num-1);
		assertEquals(expected, post);
	}
}
