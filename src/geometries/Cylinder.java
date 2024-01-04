package geometries;

import primitives.Point;
import primitives.Vector;

public class Cylinder extends Tube{
    private final double height;

    public Cylinder(double radius, Point center,double height) {
        super(radius, center);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        return super.getNormal(p);
    }
}
