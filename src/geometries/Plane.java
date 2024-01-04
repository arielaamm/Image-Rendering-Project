package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane extends Geometry{
    final private Point q;
    final private Vector normal;
    @Override
    public Vector getNormal(Point p) {
        return this.normal;
    }
    public Vector getNormal() {
        return this.normal;
    }

    public Plane(Point a,Point b,Point c) {
        this.q = a;
        this.normal = null;
    }
    public Plane(Point q, Vector normal) {
        this.q = q;
        normal.normalize();
        this.normal = normal;
    }
}
