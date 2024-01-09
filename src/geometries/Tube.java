package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Class Tube is the basic class representing a tube in Euclidean geometry
 */
public class Tube extends RadialGeometry {
    /**
     * The radius of the tube
     */
    protected final Ray axis;

    /**
     * @param radius the radius of the tube
     * @param axis the axis of the tube
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    /**
     * @param p the point at which to calculate the normal vector
     * @return the normal vector of the tube at the point
     */
    @Override
    public Vector getNormal(Point p) {
        double t = getT(p);
        return axis.getPoint(t).subtract(p).normalize();
    }

    /**
     * Calculates the value of T for a given point.
     *
     * @param  p    the point for which to calculate T
     * @return      the value of T for the given point
     */
    public double getT(Point p) {
        return p.subtract(axis.getPoint(0)).dotProduct(axis.getDirection());
    }
}
