package views.animations;

import javafx.animation.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;
import java.awt.image.BufferedImage;

// https://stackoverflow.com/questions/28183667/how-i-can-stop-an-animated-gif-in-javafx

public final class GameAnimations {

    public static ScaleTransition scaleTrans(Parent label, double fromScale, double toScale) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), label);
        scaleTransition.fromXProperty().setValue(fromScale);
        scaleTransition.toXProperty().setValue(toScale);
        scaleTransition.fromYProperty().setValue(fromScale);
        scaleTransition.toYProperty().setValue(toScale);
        return scaleTransition;
    }

    public static FadeTransition fadeTrans(Parent label, double fromFade, double toFade, double duration) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), label);
        fadeTransition.setFromValue(fromFade);
        fadeTransition.setToValue(toFade);
        return fadeTransition;
    }

    public static RotateTransition rotateTrans(Parent node, double fromAngle, double toAngle) {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000), node);
        rotateTransition.setFromAngle(fromAngle);
        rotateTransition.setToAngle(toAngle);
        return rotateTransition;
    }

    public static class ImgAnimation extends Transition {
        private ImageView imageView;
        private int count;
        private int lastIndex;
        private Image[] sequence;

        protected ImgAnimation() {
        }

        public ImgAnimation(Image[] sequence, double durationMs) {
            init(sequence, durationMs);
        }

        protected void init(Image[] sequence, double durationMs) {
            this.imageView = new ImageView(sequence[0]);
            this.sequence = sequence;
            this.count = sequence.length;

            setCycleCount(1);
            setCycleDuration(Duration.millis(durationMs));
            setInterpolator(Interpolator.LINEAR);

        }

        protected void interpolate(double k) {

            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if (index != lastIndex) {
                imageView.setImage(sequence[index]);
                lastIndex = index;
            }

        }

        public ImageView getView() {
            return imageView;
        }

    }

    public static class AnimatedGif extends ImgAnimation {

        public AnimatedGif(String filename, double durationMs) {

            GifDecoder d = new GifDecoder();
            d.read(filename);

            Image[] sequence = new Image[d.getFrameCount()];
            for (int i = 0; i < d.getFrameCount(); i++) {
                WritableImage wimg = null;
                BufferedImage bimg = d.getFrame(i);
                sequence[i] = SwingFXUtils.toFXImage(bimg, wimg);
            }

            super.init(sequence, durationMs);
        }

        public void playOnce() {
            super.play();
            super.setOnFinished(e -> {
                super.stop();
            });
        }
    }
}
