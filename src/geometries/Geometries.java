package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * The Geometries class represents a collection of geometries in the scene.
 */
public class Geometries implements Intersectable{
    private final List<Intersectable> geometries = new LinkedList<>();

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
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;
        for (Intersectable geometry : geometries) {
            List<Point> intersections = geometry.findIntersections(ray);
            if (intersections != null) {
                if (result == null)
                    result = new LinkedList<>();
                result.addAll(intersections);
            }
        }
        return result;
    }
}
