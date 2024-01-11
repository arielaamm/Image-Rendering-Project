package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Class Triangle is the basic class representing a triangle in Euclidean geometry
 */
public class Triangle extends Polygon{
    /**
     * @param a point in the triangle
     * @param b point in the triangle
     * @param c point in the triangle
     */
    public Triangle(Point a,Point b,Point c) {
        super(a,b,c);
    }

    public List<Point> findIntersections(Ray ray) {
        Vector v1 = vertices.get(0).subtract(ray.head);
        Vector v2 = vertices.get(1).subtract(ray.head);
        Vector v3 = vertices.get(2).subtract(ray.head);
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();
        double number = n1.dotProduct(ray.direction);
        double number1 = n2.dotProduct(ray.direction);
        double number2 = n3.dotProduct(ray.direction);
        if (isZero(number) || isZero(number1) || isZero(number2)) {
            return null;
        }
        else if (n1.dotProduct(ray.direction) < 0 && n2.dotProduct(ray.direction) < 0 && n3.dotProduct(ray.direction) < 0) {
            return super.findIntersections(ray);
        }
        else if (n1.dotProduct(ray.direction) > 0 && n2.dotProduct(ray.direction) > 0 && n3.dotProduct(ray.direction) > 0) {
            return plane.findIntersections(ray);
        }
        return null;
    }
}