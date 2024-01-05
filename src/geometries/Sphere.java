package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Sphere extends RadialGeometry{
    /**
     * The axis of the sphere
     */
    final protected Ray axis;

    /**
     * @param radius radius of the sphere
     * @param axis axis of the sphere
     */
    public Sphere(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    /**
     * @param p the point at which to calculate the normal vector
     * @return the normal vector of the sphere at the point
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
