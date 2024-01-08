package geometries;

import primitives.Point;
import primitives.Vector;

public class Triangle extends Polygon{
    /**
     * @param a point in the triangle
     * @param b point in the triangle
     * @param c point in the triangle
     */
    public Triangle(Point a,Point b,Point c) {
        super(a,b,c);
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
