package net.starfind.forumextractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
public class ForumExtractor {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ForumExtractor.class, args);
    }

}
