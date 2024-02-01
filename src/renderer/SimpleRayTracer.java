package renderer;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

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
     * @param point - GoePoint
     * @return the ambient light color
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return calcLocalEffects(point, ray).add(super.scene.ambientLight.getIntensity());
    }
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Color color = geoPoint.geometry.getEmission();
        Vector v = ray.direction;
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;
        Material material = geoPoint.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Color iL = lightSource.getIntensity(geoPoint.point);
                Color calcDiffusive = calcDiffusive(material, nl,iL);
                Color calcSpecular = calcSpecular(material, n, l, nl, v, iL);
                color = color.add(calcDiffusive, calcSpecular);
            }

        }
        return color;
    }

    /**
     * Calculation of specular light component
     *
     * @param material Attenuation coefficient for specular light component
     * @param n        normal to point
     * @param l        direction vector from light to point
     * @param v        direction of ray shot to point
     * @param intensity
     * @return Color of specular light component
     */
    private Color calcSpecular(Material material, Vector n, Vector l, double nl, Vector v, Color intensity) {
        Vector r = l.add(n.scale(-2 * nl));
        double minusVR = -alignZero(r.dotProduct(v));
        if (minusVR <= 0)
            return Color.BLACK; // view from direction opposite to r vector
        double pow = Math.pow(minusVR, material.nShininess);
        Double3 amount = material.kS.scale(pow);
        return intensity.scale(amount);
    }

    /**
     * Calculation of diffusion light component
     *
     * @param material normal to point
     * @param nl       dot product between n-normal to point and l-direction vector from light to point
     * @param intensity
     * @return Color of diffusion light component
     */
    private Color calcDiffusive(Material material, double nl, Color intensity) {
        double abs = Math.abs(nl);
        Double3 scale = material.kD.scale(abs);
        return intensity.scale(scale);
    }
}
