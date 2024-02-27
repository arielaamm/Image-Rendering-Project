package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;

/**
 * TargetArea object is targeted the VP
 */
public class TargetArea {
	private final static int DENSITY = 7;
	Point location;
	Vector vTo;
	Vector vRight;
	Vector vUp;
	double heightViewPlane = 0.0;
	double widthViewPlane = 0.0;
	double distanceFromViewPlane = 100;
	Point viewPlaneCenter;

	/**
	 * constructor
	 */
	public TargetArea(Ray ray, double size) {
		location = ray.head;
		vTo = ray.direction;
		viewPlaneCenter = ray.head.add(vTo.scale(distanceFromViewPlane));
		double a = vTo.getX();
		double b = vTo.getY();
		double c = vTo.getZ();
		vRight = (a == b && b == c) ? new Vector(0, -a, a).normalize() : new Vector(b - c, c - a, a - b).normalize();
		vUp = vRight.crossProduct(vTo);
		this.heightViewPlane = this.widthViewPlane = size;
	}

	/**
	 * diffolt costractor
	 */
	public TargetArea() {}


	/**
	 * Set the location of the camera
	 * @param nX - width of the pixel
	 * @param nY - height of the pixel
	 * @param j - index in the column
	 * @param i - index in the row
	 * @return Ray
	 */
	public Ray constructRay(int nX, int nY, double j, double i){
		double rY = heightViewPlane / nY;
		double rX = widthViewPlane / nX;
		double xJ = (j - ((nX - 1) / 2.0))* rX;
		double yI = -(i - ((nY - 1) / 2.0)) * rY;
		Point pIJ = viewPlaneCenter;
		if (!isZero(xJ))
			pIJ = pIJ.add(vRight.scale(xJ));
		if (!isZero(yI))
			pIJ = pIJ.add(vUp.scale(yI));
		Vector Vij = pIJ.subtract(location);
		return new Ray(location, Vij);
	}

	/**
	 * This function constructs a grid of rays.
	 *
	 * @return         List of Ray objects representing the grid of rays
	 */
	public List<Ray> constructRayBeamGrid() {
		Random rand = new Random();
		List<Ray> rays = new LinkedList<>();
		for (int i = 0; i < DENSITY; i++)
			for (int j = 0; j < DENSITY; j++)
				rays.add(constructRay(DENSITY, DENSITY, rand.nextDouble(0, 1) + j - 0.5, rand.nextDouble(0, 1) + i - 0.5));
		return rays;
	}
}
