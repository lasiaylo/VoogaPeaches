package engine.util;


import javafx.scene.image.Image;

public enum StandardGraphics {
    Test("sprite_test", 0, 0, 50, 50);

    private static final String RESOURCES_PATH = "resources.graphics.";

    private Image image;

    StandardGraphics(String path, int x, int y, int h, int w) {
        image = Spriter.getInstance().getSprite(RESOURCES_PATH + path, x, y, h, w);
    }

    public Image getImage() {
        return image;
    }
}
