package edu.hw6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {
    @Test
    public void getAllNewsTest() {
        var stories = HackerNews.hackerNewsTopStories();
        assertThat(stories.length).isNotEqualTo(0);
    }

    @Test
    public void getNewsNameTest() {
        int id = 37570037;
        final String expectedName = "JDK 21 Release Notes";
        final String gotName = HackerNews.news(id);
        assertThat(gotName).isEqualTo(expectedName);
    }

    @Test
    public void getInvalidNewsIdNameTest() {
        int id = -100500;
        final String expectedName = "";
        final String gotName = HackerNews.news(id);
        assertThat(expectedName).isEqualTo(gotName);
    }
}
