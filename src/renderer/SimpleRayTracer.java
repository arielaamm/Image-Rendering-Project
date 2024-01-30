package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * This class is an extended class of a ray tracer.
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * Construct
     * @param scene
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * @param ray
     * @return the color of the closest point
     */
    @Override
    public Color traceRay(Ray ray) {
        List <Point> intersections = scene.geometries.findIntersections(ray);
        if(intersections == null)
            return scene.background;
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     *
     * @param closestPoint
     * @return the ambient light color
     */
    private Color calcColor(Point closestPoint) {
        return scene.ambientLight.getIntensity();
    }
}
