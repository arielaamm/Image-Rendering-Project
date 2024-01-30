package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * DirectionalLight class represents a light source in the scene.
 */
public class DirectionalLight extends Light implements LightSource{
	/**
	 * @param intensity the intensity color
	 */
	private Vector direction;

	/**
	 * Constructor of the class
	 * @param intensity the intensity color
	 * @param direction the direction
	 */
	protected DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction;
	}

	/**
	 * A method to retrieve the intensity color.
	 *
	 * @param p the point at which to calculate the intensity
	 * @return the intensity color
	 */
	@Override
	public Color getIntensity(Point p) {
		return intensity;
	}

	/**
	 * Retrieves the vector from the specified point.
	 *
	 * @param p the point from which to retrieve the vector
	 * @return the vector retrieved from the specified point
	 */
	@Override
	public Vector getL(Point p) {
		return direction.normalize();
	}
}
