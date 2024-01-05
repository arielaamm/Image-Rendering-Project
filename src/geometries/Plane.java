package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane extends Geometry{
    /**
     * The point in the plane
     */
    private final Point q;
    /**
     * The normal vector of the plane
     */
    private final Vector normal;

    /**
     * @param a point in the plane
     * @param b point in the plane
     * @param c point in the plane
     */
    public Plane(Point a,Point b,Point c) {
        this.q = a;
        this.normal = null;
    }

    /**
     * @param q the point in the plane
     * @param normal the normal vector of the plane
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    /**
     * @param p the point at which to calculate the normal vector
     * @return the normal vector of the plane at the point
     */
    @Override
    public Vector getNormal(Point p) {
        return this.normal;
    }

    /**
     * @return the normal vector of the plane
     */
    public Vector getNormal() {
        return this.normal;
    }
}
