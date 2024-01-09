package geometries;


import primitives.Vector;
import primitives.Point;

/**
 * Abstract class Geometry is the base class for all geometries in this project.
 */
public abstract class Geometry implements Intersectable {


    /**
     * Returns the normal vector of the geometry at the given point.
     *
     * @param p the point at which to calculate the normal vector
     * @return the normal vector of the geometry at the point
     */
    public abstract Vector getNormal(Point p);


}
