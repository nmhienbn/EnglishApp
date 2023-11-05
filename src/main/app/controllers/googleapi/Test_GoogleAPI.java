package controllers.googleapi;

import java.io.IOException;

public class Test_GoogleAPI {
    public static void main(String[] args) throws IOException {
        // Test translate
        System.out.println(GoogleTranslate.translate("tôi đi ăn cơm", "vi", "en"));
    }
}
