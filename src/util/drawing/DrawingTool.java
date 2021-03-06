package util.drawing;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

/**
 * Represents a tool to draw on an ImageCanvas. 
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public abstract class DrawingTool {
	protected ImageCanvas canvas;
	private String name;

	protected DrawingTool(String name, ImageCanvas canvas) {
		this.name = name;
		this.canvas = canvas;
	}
	
	/**
	 * Sets the tool to start working on the canvas to which it's assigned. 
	 */
	public abstract void use();
	
	/**
	 * Sets the tool to tileMovementWithStop working on the canvas.
	 */
	public abstract void drop();
	
	protected Point2D point(MouseEvent e) {
		return new Point2D(e.getX(), e.getY());
	}
	
	public String toString() {
		return name;
	}
}
