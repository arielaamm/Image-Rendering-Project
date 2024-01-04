package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane extends Geometry{
    private final Point q;
    private final Vector normal;
    public Plane(Point a,Point b,Point c) {
        this.q = a;
        this.normal = null;
    }
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }
    @Override
    public Vector getNormal(Point p) {
        return this.normal;
    }
    public Vector getNormal() {
        return this.normal;
    }
}
