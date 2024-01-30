package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static java.lang.Math.max;

public class SpotLight extends PointLight{
	/**
	 * @param intensity the intensity color
	 */
	private Vector direction;

	/**
	 * Constructor of the class
	 * @param intensity the intensity color
	 * @param position the position
	 * @param direction the direction
	 */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction;
	}

	/**
	 * Sets the constant attenuation factor for the point light.
	 *
	 * @param kC the constant attenuation factor to set
	 * @return the updated PointLight object
	 */
	@Override
	public SpotLight setKc(double kC) {
		return (SpotLight) super.setKc(kC);
	}

	/**
	 * Set the attenuation factor for light intensity.
	 *
	 * @param kL the attenuation factor to set
	 * @return the updated PointLight object
	 */
	@Override
	public SpotLight setKl(double kL) {
		return (SpotLight)super.setKl(kL);
	}

	/**
	 * Set the quadratic attenuation factor for the point light.
	 *
	 * @param kQ the new quadratic attenuation factor
	 * @return the updated PointLight object
	 */
	@Override
	public SpotLight setKq(double kQ) {
		return (SpotLight)super.setKq(kQ);
	}

	/**
	 * A method to retrieve the intensity color.
	 *
	 * @param p the point at which to calculate the intensity
	 * @return the intensity color
	 */
	@Override
	public Color getIntensity(Point p) {
		return super.getIntensity(p).scale(max(0, direction.dotProduct(getL(p))));
	}

	/**
	 * Retrieves the vector from the specified point.
	 *
	 * @param p the point from which to retrieve the vector
	 * @return the vector retrieved from the specified point
	 */
	@Override
	public Vector getL(Point p) {
		return super.getL(p);
	}
}
