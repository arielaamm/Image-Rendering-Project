package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Point.getQuadraticequation;

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
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Double> T1T2 = getQuadraticequation(ray.direction, ray.head, radius);
        if (T1T2 == null)
            return null;
        return List.of(ray.getPoint(T1T2.get(0)), ray.getPoint(T1T2.get(1)));
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
