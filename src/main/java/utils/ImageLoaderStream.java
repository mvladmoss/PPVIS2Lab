package utils;

import javafx.scene.image.Image;

public class ImageLoaderStream implements ImageLoader {
    @Override
    public Image loadImage(String path) {
        return new Image(getClass().getClassLoader().getResourceAsStream(path));
    }
}
