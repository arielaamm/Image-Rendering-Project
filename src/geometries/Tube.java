package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        try {
            Vector V = ray.direction.crossProduct(axis.direction);
        } catch (IllegalArgumentException e) {

        }
        return null;
    }









//        // Get the parameters of the ray
//        Point P0 = ray.head;
//        Vector D = ray.direction;
//
//        // Get the parameters of the tube
//        Point center = axis.head; // Replace with the actual center point of the tube
//        double radius = super.radius; // Replace with the actual radius of the tube
//
//        // Calculate the quadratic equation coefficients
//        Vector PC = P0.subtract(center);
//        double a = D.dotProduct(D);
//        double b = 2 * D.dotProduct(PC);
//        double c = PC.dotProduct(PC) - radius * radius;
//
//        // Calculate the discriminant
//        double discriminant = b * b - 4 * a * c;
//
//        if (discriminant >= 0) {
//            // Calculate the two possible values of t
//            double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
//            double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
//
//            if(t1>=0 && t2>=0){
//                return List.of(ray.getPoint(t1), ray.getPoint(t2));
//            }
//
//            // Check if the t values fall within the valid range of the ray
//            if (t1 >= 0) {
//                // Calculate the intersection point
//                Point intersection1 = ray.getPoint(t1);
//                return List.of(intersection1);
//            }
//
//            if (t2 >= 0) {
//                // Calculate the intersection point
//                Point intersection2 = ray.getPoint(t2);
//                return List.of(intersection2);
//            }
//        }
//
//        return null;
//    }
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
