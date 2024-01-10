package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Optional;

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
    @Override
    public List<Point> findIntersections(Ray ray) {
        double Tm = ray.direction.dotProduct(center.subtract(ray.head));
        double d = Math.sqrt(ray.direction.lengthSquared() - Tm * Tm);
        if (d > radius) {
            return null;
        }
        double Th = Math.sqrt(radius * radius - d * d);
        double t1 = Tm - Th;
        double t2 = Tm + Th;
        if (t1 > 0 && t2 > 0) {
            return List.of(ray.getPoint(t1), ray.getPoint(t2));
        }
        if (t1 > 0) {
            return List.of(ray.getPoint(t1));
        }
        if (t2 > 0) {
            return List.of(ray.getPoint(t2));
        }
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
