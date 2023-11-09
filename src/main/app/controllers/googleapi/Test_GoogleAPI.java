package controllers.googleapi;

import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;

public class Test_GoogleAPI {
    public static void main(String[] args) throws IOException, JavaLayerException {
        // Test translate
        System.out.println(GoogleTranslate.translate("tôi đi ăn cơm", "vi", "en"));

        GoogleTranslate.speek("Tôi thích H lắm", "en");
    }
}
