package engine.graphics;

import javafx.scene.image.Image;

import java.util.HashMap;

import engine.util.Spriter;

public class Graphics {
	
    private HashMap<String, Image> cache;

    public Image getImage(String name) {
        return cache.get(name);
    }

    public void add(Sprite[] list) {
        for (Sprite sprite : list)
            add(sprite);
    }

    private void add(Sprite sprite) {
        cache.put(sprite.getName(), sprite.getImage());
    }
}
