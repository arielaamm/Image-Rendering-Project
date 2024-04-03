package geometries;


import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * The Geometries class represents a collection of geometries in the scene.
 */
public class Geometries extends Intersectable{
    protected List<Intersectable> geometries = new LinkedList<>();

    public Geometries() {
    }

    /**
     * @param geometries - the list of geometries
     */
    public Geometries(Intersectable...geometries) {
        add(geometries);
    }

    /**
     * Adds the given geometries to the list of geometries.
     *
     * @param  geometries  the geometries to add
     */
    public void add(Intersectable...geometries) {
        this.geometries.addAll(List.of(geometries));
    }

    /**
     * Finds intersections of a ray with the geometries in the list.
     *
     * @param  ray  the ray to find intersections with
     * @return      a list of intersection points
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable geometry : geometries) {
            List<GeoPoint> intersections = geometry.findGeoIntersectionsHelper(ray,maxDistance);
            if (intersections != null) {
                if (result == null)
                    result = new LinkedList<>();
                result.addAll(intersections);
            }
        }
        return result;
    }
    @Override
    public List<Point> minMaxPoints() {
        // set the objects to default values
        Point min = Point.POSITIVE_INFINITE;
        Point max = Point.NEGATIVE_INFINITE;

        for (Intersectable obj : geometries) {
            List<Point> objMinMax = obj.minMaxPoints();
            min = Point.findMinimum(List.of(min, objMinMax.get(0)));
            max = Point.findMaximum(List.of(max, objMinMax.get(1)));
        }

        return List.of(min, max);
    }
}
