package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Sphere extends RadialGeometry{
    final protected Ray axis;
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
