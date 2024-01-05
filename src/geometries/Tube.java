package geometries;

import primitives.Point;
import primitives.Vector;

public class Tube extends RadialGeometry {
    /**
     * The radius of the tube
     */
    final private Point center;

    /**
     * @param radius the radius of the tube
     * @param center the center of the tube
     */
    public Tube(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * @param p the point at which to calculate the normal vector
     * @return the normal vector of the tube at the point
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
