package controllers.GoogleTab;

public class ButtonSwapLang {
    GoogleTab_ctrl googleTabCtrl;
    public ButtonSwapLang(GoogleTab_ctrl GoogleTabCtrl) {
        this.googleTabCtrl = GoogleTabCtrl;
    }
    void init() {
        var textToTranslate = googleTabCtrl.textToTranslate;
        var translatedText = googleTabCtrl.translatedText;
        var speak_button1 = googleTabCtrl.speak_button1;
        var speak_button2 = googleTabCtrl.speak_button2;

        googleTabCtrl.swap_lang_button.setOnAction(e -> {
            String tmp = googleTabCtrl.lang1;
            googleTabCtrl.lang1 = googleTabCtrl.lang2;
            googleTabCtrl.lang2 = tmp;
            textToTranslate.setText(translatedText.getText());
            new Translate(googleTabCtrl).doTranslate();
            googleTabCtrl.setLang_label();
            new ButtonSpeak().init(speak_button1, textToTranslate, googleTabCtrl.lang1);
            new ButtonSpeak().init(speak_button2, translatedText, googleTabCtrl.lang2);
        });
    }
}
