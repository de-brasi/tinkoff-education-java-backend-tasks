// Task 5

package edu.hw6;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HackerNews {
    private HackerNews() {}

    @SuppressWarnings("MultipleStringLiterals")
    public static long[] hackerNewsTopStories() {
        try {
            var request = HttpRequest.newBuilder()
                .uri(new URI("https://hacker-news.firebaseio.com/v0/topstories.json"))
                .GET()
                .header("AcceptEncoding", "gzip")
                .build();
            var response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            String storiesList = response.body();
            return Arrays.stream(storiesList.substring(1, storiesList.length() - 1).split(","))
                .mapToLong(Long::parseLong)
                .toArray();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return new long[0];
        }
    }

    public static String news(long id) {
        try {
            var request = HttpRequest.newBuilder()
                .uri(new URI("https://hacker-news.firebaseio.com/v0/item/" + id + ".json"))
                .GET()
                .header("AcceptEncoding", "gzip")
                .build();
            var response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            String newsInfo = response.body();
            Matcher matcher = TITLE_PATTERN.matcher(newsInfo);

            return (matcher.find()) ? matcher.group(1) : "";
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return "";
        }
    }

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Pattern TITLE_PATTERN = Pattern.compile("\"title\":\"([^\"]*)\",");
}
