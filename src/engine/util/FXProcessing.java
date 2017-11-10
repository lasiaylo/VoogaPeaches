package engine.util;

import javafx.scene.image.ImageView;

public class FXProcessing {
    public static double getXImageCenter(ImageView image) {
        return image.getX() + image.getBoundsInLocal().getWidth() / 2;
    }

    public static double getYImageCenter(ImageView image) {
        return image.getY() + image.getBoundsInLocal().getHeight() / 2;
    }

    public static double getXImageCoord(double xCenter, ImageView image) {
        return xCenter - image.getBoundsInLocal().getWidth() / 2;
    }

    public static double getYImageCoord(double yCenter, ImageView image) {
        return yCenter - image.getBoundsInLocal().getHeight() / 2;
    }
}
