package engine.renderer.DBRenderer;

import engine.renderer.IRenderer;
import javafx.scene.image.Image;

public class Renderer implements IRenderer {

    public Renderer(String dbname) {

    }

    /**
     * Validate DB schema
     *
     * @return is the DB valid?
     */
    private boolean validate(String dbname) {
        return true;
    }

    @Override
    public Image render(int x, int y) {
        return null;
    }

    @Override
    public Image render(int s, int w, int n, int e) {
        return null;
    }

    @Override
    public Image render(int x, int y, double level) {
        return null;
    }
}
