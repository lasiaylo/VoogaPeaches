package util;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import java.util.Random;
import java.io.File;

public class ErrorPopup {

    private final int Width = 400;
    private final int Height = 350;
    private final String Audio = "resources/cox/error.mp3";
    private final String Text = "You've been coxblocked!";
    private String myImage = "resources/cox/cox";
    private StackPane pane;

    public ErrorPopup() {
        pane = new StackPane();
        pane.setPrefSize(Width,Height);
        setText();
        setCoxImage();
        MediaPlayer audioPlayer = getMediaPlayer();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(
                e -> {
                    e.consume();
                    audioPlayer.stop();
                    stage.close();
                }
        );
        stage.showAndWait();
    }

    private MediaPlayer getMediaPlayer() {

        File audioFile = new File(Audio);
        Media audio = new Media(audioFile.toURI().toString());
        MediaPlayer audioPlayer = new MediaPlayer(audio);
        System.out.println(audio.toString());
        System.out.println(audioPlayer.toString());
        System.out.println(audioPlayer.getTotalDuration().toHours());
        audioPlayer.play();
        audioPlayer.setAutoPlay(true);
        return audioPlayer;
    }

    private void setText() {
        Text text = new Text(Text);
        text.setFont(Font.font ("Verdana", 25));
        pane.getChildren().add(text);
        StackPane.setAlignment(text, Pos.BOTTOM_CENTER);
    }

    private void setCoxImage() {
        Random rand = new Random();
        int n = rand.nextInt(3) + 1;
        myImage += n + ".jpg";
        File imageFile = new File(myImage);
        Image image = new Image(imageFile.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(Width);
        pane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.TOP_CENTER);    }
}
