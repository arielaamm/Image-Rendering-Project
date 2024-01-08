package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Class Cylinder is the basic class representing a cylinder in Euclidean geometry
 */
public class Cylinder extends Tube{
    /**
     * The height of the Cylinder
     */
    private final double height;

    /**
     * @param radius the radius of the cylinder
     * @param axis the center of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }


    /**
     * @param p the point at which to calculate the normal vector
     * @return the normal vector of the Cylinder at the point
     */
    @Override
    public Vector getNormal(Point p) {
        return super.getNormal(p);
    }
}
