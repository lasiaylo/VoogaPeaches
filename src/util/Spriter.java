package util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import util.exceptions.ValueException;
import util.pubsub.PubSub;
import util.pubsub.messages.ExceptionMessage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Spriter {
    HashMap<String, BufferedImage> cache;

    Spriter() {
        cache = new HashMap<>();
    }

    /**
     * Get a rectangular sprite from an image
     *
     * @param path: absolute file path
     * @param x: x coordinate of the left-top corner
     * @param y: y coordinate of the left-top corner
     * @param h: height
     * @param w: width
     * @return new sprite
     */
    public Image getSprite(String path, int x, int y, int h, int w) {
        BufferedImage image = getBufferedImage(path);
        try {
            return SwingFXUtils.toFXImage(image.getSubimage(x, y, h, w), null);
        } catch (RasterFormatException e) {
            PubSub.getInstance().publish(PubSub.Channel.EXCEPTION_MESSAGE,
                    new ExceptionMessage(new ValueException("Invalid sprite coordinates", e)));
        }

        return null;
    }

    private BufferedImage getBufferedImage(String path) {
        if (cache.containsKey(path))
            return cache.get(path);

        try {
            cache.put(path, ImageIO.read(new File(path)));
        } catch (IOException e) {
            PubSub.getInstance().publish(PubSub.Channel.EXCEPTION_MESSAGE,
                    new ExceptionMessage(new IOException("Could not load image in " + path, e)));
        }

        return cache.get(path);
    }
}
