package renderer;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import lighting.LightSource;
import primitives.*;
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
        List <GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if(intersections == null)
            return scene.background;
        return calcColor(ray.findClosestGeoPoint(intersections),ray);
    }

    /**
     *
     * @param gp - GoePoint
     * @return the ambient light color
     */
    private Color calcColor(GeoPoint gp,Ray ray) {
        return scene.ambientLight.getIntensity().add(calcColorEffect(gp,ray));
    }

    private Color calcColorEffect(GeoPoint gp,Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector normal = gp.geometry.getNormal(gp.point);
        Vector direction = ray.direction;
        Material material = gp.geometry.getMaterial();
        double nv = normal.dotProduct(direction);
        if (isZero(nv))
            return Color.BLACK;
        for (LightSource light : scene.lights) {
            Vector l = light.getL(gp.point);
            double ln = alignZero(l.dotProduct(normal));
            if (ln * nv > 0) {
                Color iL = light.getIntensity(gp.point);
                Vector r = l.subtract(l.scale(2 * ln));
                Double3 Specular = material.kS.scale(Math.pow(Math.max(0, direction.scale(-1).dotProduct(r)), material.nShininess));
                Double3 Diffuse = material.kD.scale(Math.abs(ln));
                color = color.add(iL.scale(Diffuse.add(Specular)));
            }
        }
        return color;
    }
}
