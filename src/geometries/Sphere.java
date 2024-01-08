package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Sphere extends RadialGeometry{
    /**
     * The axis of the sphere
     */
    final protected Point center;

    /**
     * @param radius radius of the sphere
     * @param center axis of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
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
