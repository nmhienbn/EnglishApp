package controllers.GoogleTab;

import models.facades.WordListFacade;

public class Translate {
    final GoogleTab_ctrl googleTabCtrl;

    public Translate(GoogleTab_ctrl GoogleTabCtrl) {
        this.googleTabCtrl = GoogleTabCtrl;
    }

    void doTranslate() {
        String translated = WordListFacade.translate(googleTabCtrl.textToTranslate.getText(),
                googleTabCtrl.lang1, googleTabCtrl.lang2);
        System.out.println("Translate:");
        translated = translated.replaceAll("\\\\n", "\n");
        System.out.println(translated);

        googleTabCtrl.translatedText.setText(translated);
    }
}
