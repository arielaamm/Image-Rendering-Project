package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Class Sphere is the basic class representing a sphere in Euclidean geometry
 */
public class Sphere extends RadialGeometry{
    /**
     * The axis of the sphere
     */
    protected final Point center;

    /**
     * @param radius radius of the sphere
     * @param center axis of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
    /**
     * @param p the point at which to calculate the normal vector
     * @return the normal vector of the sphere at the point
     */
    @Override
    public Vector getNormal(Point p) {
        Vector v = p.subtract(this.center);
        return v.normalize();
    }

}
