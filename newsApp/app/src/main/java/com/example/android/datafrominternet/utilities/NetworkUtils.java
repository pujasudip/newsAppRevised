/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.datafrominternet.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.android.datafrominternet.models.ArticleField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String NEWS_URL =
            "https://newsapi.org/v1/articles";

    final static String PARAM_QUERY = "source";
    final static String THE_NEXT_WEB = "the-next-web";

    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    final static String PARAM_SORT = "sort";
    final static String sortBy = "latest";
    final static String apiKey = "apiKey";
    final static String API = "4eacd48df11f47208cd8e29599f6b0f9";

    /**
     * Builds the URL used to query GitHub.
     */
    public static URL buildUrl() {
        Uri builtUri = Uri.parse(NEWS_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, THE_NEXT_WEB).appendQueryParameter(PARAM_SORT, sortBy)
                .appendQueryParameter(apiKey, API)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }

    public static ArrayList<ArticleField> parseJSON(String json) throws JSONException{
        ArrayList<ArticleField> string = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray items = main.getJSONArray("articles");
        String source = main.getString("source");

        for(int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String title = item.getString("title");
            String author = item.getString("author");
            String description = item.getString("description");
            String urlToImage = item.getString("urlToImage");
            String url = item.getString("url");
            ArticleField article = new ArticleField(title, author, description, url, urlToImage);
            string.add(article);
        }

        return string;
    }


}
