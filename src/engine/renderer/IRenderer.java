package engine.renderer;

import javafx.scene.image.Image;

public interface IRenderer {
    /**
     * Render a tile
     *
     * @param x: x coordinate
     * @param y: y coordinate
     * @return new tile
     */
    public Image render(int x, int y);

    /**
     * Render a tiles in a bounding box
     *
     * @param s: south
     * @param w: west
     * @param n: north
     * @param e: east
     * @return new tiles
     */
    public Image render(int s, int w, int n, int e);

    /**
     * Render a frame
     *
     * @param x: x coordinate of frame center
     * @param y: y coordinate of frame center
     * @param level: distance from surface
     * @return new tiles
     */
    public Image render(int x, int y, double level);
}
