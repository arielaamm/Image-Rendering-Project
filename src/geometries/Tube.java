package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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
     * Find intersections of a ray with geo points.
     *
     * @param  ray   the ray to find intersections with
     * @return      a list of geo points representing the intersections
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        Point p = ray.head;
        Point pAxis = axis.head;
        Vector delta = p.subtract(pAxis);
        Vector v = ray.direction;
        Vector vAxis = axis.direction;
        double vDotProductVAxis = v.dotProduct(vAxis);
        Vector aVector = v.subtract(vAxis.scale(vDotProductVAxis));
        double A = aVector.lengthSquared();
        Vector scale = vAxis.scale(delta.dotProduct(vAxis));
        double B = 2 * aVector.dotProduct(delta.subtract(scale));
        double C = delta.subtract(scale).lengthSquared() - radius * radius;
        double discriminant = B * B - 4 * A * C;
        if (discriminant < 0)
            return null;
        double t1 = alignZero((-B + Math.sqrt(discriminant)) / (2 * A));
        double t2 = alignZero((-B - Math.sqrt(discriminant)) / (2 * A));
        if (t1 > 0 && t2 > 0 && t1 != t2 && alignZero(t2 - maxDistance) < 0 && alignZero(t1 - maxDistance) < 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        if (t1 > 0 && alignZero(t1 - maxDistance) < 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        if (t2 > 0 && alignZero(t2 - maxDistance) < 0)
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        return null;
    }
    /**
     * @param p the point at which to calculate the normal vector
     * @return the normal vector of the tube at the point
     */
    @Override
    public Vector getNormal(Point p) {
        double t = axis.getT(p);
        Point o =  axis.getPoint(t);
        return o.subtract(p).normalize();
    }

}
