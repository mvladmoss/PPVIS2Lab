package view;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import utils.ImageLoader;
import utils.ImageLoaderStream;

public class ButtonCreator {

    private static final double LEFT_ANCHOR = 30d;
    private static final double IMAGE_SIZE = 30d;
    private static final double BUTTON_SIZE = 30d;
    private ImageLoader imageLoader;

    ButtonCreator(){
        imageLoader = new ImageLoaderStream();
    }

    void customButton(Pane pane, String imgUrl, double topAnchor, EventHandler eventHandler){
        Button chooseFileButton = new Button();
        chooseFileButton.setOnAction(eventHandler);
        pane.getChildren().addAll(chooseFileButton);
        AnchorPane.setLeftAnchor(chooseFileButton,LEFT_ANCHOR);
        AnchorPane.setTopAnchor(chooseFileButton,topAnchor);
        setImage(chooseFileButton,imgUrl);
        chooseFileButton.setMinWidth(BUTTON_SIZE);
        chooseFileButton.setMinHeight(BUTTON_SIZE);
    }

    private void setImage(Button button,String imgUrl){
        Image image = imageLoader.loadImage(imgUrl);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(IMAGE_SIZE);
        imageView.setFitWidth(IMAGE_SIZE);
        button.setGraphic(imageView);
    }
}
