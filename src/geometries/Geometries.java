package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
    private final List<Intersectable> geometries = new LinkedList<>();

    public Geometries() {
    }

    public Geometries(Intersectable...geometries) {
        add(geometries);
    }

    public void add(Intersectable...geometries) {
        this.geometries.addAll(List.of(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;
        for (Intersectable geometry : geometries) {
            List<Point> intersections = geometry.findIntersections(ray);
            if (result == null && intersections != null) {
                result = new LinkedList<>();
            }
            if (intersections != null) {
                result.addAll(intersections);
            }
        }
        return result;
    }
}
