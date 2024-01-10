package geometries;


import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Intersectable interface for geometries that can be intersected.
 */
public interface Intersectable {
    /**
     * Finds the intersections of a given ray with a list of points.
     *
     * @param  ray  the ray to find intersections with
     * @return      a list of points representing the intersections
     */
    List<Point> findIntersections(Ray ray);

}
