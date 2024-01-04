package geometries;

import primitives.Point;
import primitives.Vector;

public class Tube extends RadialGeometry {
    final private Point center;

    public Tube(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
