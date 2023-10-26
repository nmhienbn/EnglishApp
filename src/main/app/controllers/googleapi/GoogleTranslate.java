package controllers.googleapi;
// Free Endpoint: https://github.com/ssut/py-googletrans/issues/268
// Java Request: https://www.baeldung.com/java-http-request

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleTranslate {
    /**
     * Translate text from source language to target language.
     * sourceLanguage = en (English), vn (Vietnamese), ... or an auto (auto detect)
     * targetLanguage = en (English), vn (Vietnamese), ...
     *
     * @param text           text to translate
     * @param sourceLanguage source language
     * @param targetLanguage target language
     * @return translated text
     */
    public static String translate(String text, String sourceLanguage, String targetLanguage) throws IOException {
        // init link
        String urlStr = String.format("https://clients5.google.com/translate_a/t?client=dict-chrome-ex&sl=%s&tl=%s&q=%s",
                sourceLanguage, targetLanguage, text);
        // make it to a link
        urlStr = urlStr.replace(" ", "%20");

        // create connection
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // send request
        con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        con.setRequestMethod("GET");

        // now get response
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream())
        );
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        con.disconnect();

        String trans = "-1";
        try {
            trans = content.toString().substring(2, content.toString().length() - 2);
        } catch (Exception e) {
            System.out.print("Error: " + e.toString());
            return "-1";
        }

        return trans;
    }
}
