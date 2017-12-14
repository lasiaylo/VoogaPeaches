package database.filehelpers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A Static helper class used for conversion of files stored within the database
 * into their appropriate byte[], and vice versa
 *
 * @author Walker Willetts
 */
public class FileConverter {

    /**
     * Converts a given image to its corresponding byte[] for use
     * with the {@code FileDataManager}
     * @param img is an {@code Image} representing the image to be
     *            converted
     * @return A {@code byte[]} that represents the bytes of the passed
     * in Image
     */
    public static byte[] convertImageToByteArray(Image img) {
        BufferedImage bi = SwingFXUtils.fromFXImage(img, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] imageBytes;
        try {
            ImageIO.write(bi, "png", outputStream);
            imageBytes = outputStream.toByteArray();
            outputStream.close();
        } catch (IOException e) {
            imageBytes = new byte[0];
        }
        return imageBytes;
    }

    /**
     * Converts the given InputStream to a JavaFX {@code Image}
     * @param stream is an {@code InputStream} representing the stream that
     *               contains the Image's bytes for the Image's creation
     * @return An {@code Image} created from the passed in {@code InputStream}
     */
    public static Image convertInputStreamToImage(InputStream stream) {
        return new Image(stream);
    }
}
