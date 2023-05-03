package com.d0remi.k5naauthbot.wikiSearch;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class gitbookSearch {
    private static final String GITBOOK_API_BASE_URL = "https://api.gitbook.com/v1/spaces/jZX64u5w1liTtAtXl4of";
    private static final String GITBOOK_API_ACCESS_TOKEN = "gb_api_GvmXR7KP0KQTj1UAeui2UelSD7QopDarv7YRsIKL";
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    @NotNull
    public static String search(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            System.out.println(GITBOOK_API_ACCESS_TOKEN);
            String url = GITBOOK_API_BASE_URL + "/search?query=" + encodedQuery;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(java.net.URI.create(url))
                    .header("Authorization", "Bearer " + GITBOOK_API_ACCESS_TOKEN)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return "Error: " + response.statusCode() + " " + response.body();
            }
            String responseBody = response.body();
            System.out.println(responseBody);
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray results = jsonObject.getJSONArray("items");
            if (results.length() == 0) {
                return "No results found.";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Math.min(5, results.length()); i++) {
                JSONObject result = results.getJSONObject(i);

                String urls = result.getJSONObject("urls").getString("app");
                String urlStr = urls.replaceFirst("https://app.gitbook.com/o/dzLWuKX6zLjn0q9LpHLX/s/jZX64u5w1liTtAtXl4of/", "https://k5nawiki.gitbook.io/");

                String title = result.getString("title");
                String description = result.optString("body");
                sb.append(String.format("[%s]\n%s\n%s\n\n", title, description, urlStr));

            }
            return sb.toString();
        } catch (IOException | InterruptedException | JSONException e) {
            return "Error: " + e.getMessage();
        }
    }
}
