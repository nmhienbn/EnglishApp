package controllers.googleapi;
// Free Endpoint: https://github.com/ssut/py-googletrans/issues/268
// Java Request: https://www.baeldung.com/java-http-request

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

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
        String urlStr = String.format("https://clients5.google.com/translate_a/t?client=dict-chrome-ex&sl=%s&tl=%s&dt=t&q=%s",
                sourceLanguage, targetLanguage, URLEncoder.encode(text, StandardCharsets.UTF_8));

        // create connection
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // send request
        con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36");
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

    /**
     * Speek text.
     * @param text content of text to speek
     * @param language language of text, vi for vietnamese, en for english, ...
     * @throws IOException
     * @throws JavaLayerException
     */
    public static void speek(String text, String language) throws IOException {
        // https://translate.google.com.vn/translate_tts?ie=UTF-8&q=Ki%E1%BA%BFt%20gi%C3%A0%20Tr%C3%AAn&tl=vi&client=tw-ob
        String urlStr = String.format("https://translate.google.com.vn/translate_tts?ie=UTF-8&q=%s&tl=%s&client=tw-ob",
                URLEncoder.encode(text, StandardCharsets.UTF_8), language);

        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            InputStream audio = con.getInputStream();
            new Player(audio).play();
            con.disconnect();
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
