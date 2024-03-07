package renderer;

import geometries.Intersectable.*;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;
import java.util.stream.Collectors;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * This class implements the simple version of the ray tracer.
 */
public class SimpleRayTracer extends RayTracerBase {

	private static final double EPS = 0.1;
	private static final double MIN_CALC_COLOR_K = 0.007;
	private static final int MAX_CALC_COLOR_LEVEL = 7;
	private static final Double3 INITIAL_K = Double3.ONE;

	/**
	 * @param scene the scene to render
	 */
	public SimpleRayTracer(Scene scene) {
		super(scene);
	}

	/**
	 * Calculate the color of the closest intersection point
	 * with the given ray.
	 *
	 * @param  ray  the ray for which to calculate the color
	 * @return      the color of the closest intersection point
	 */
	@Override
	public Color traceRay(Ray ray) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		if (closestPoint == null) {
			return scene.background;
		}
		return calcColor(closestPoint, ray);

	}

	/**
	 * Calculates the color for a given GeoPoint and Ray.
	 *
	 * @param  geoPoint    the GeoPoint to calculate color for
	 * @param  ray         the Ray to calculate color for
	 * @return             the calculated color
	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray) {
		return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
				.add(scene.ambientLight.getIntensity());
	}

	/**
	 * Calculate the color for a given GeoPoint, Ray, level, and k.
	 *
	 * @param  geoPoint  the GeoPoint to calculate color for
	 * @param  ray       the Ray representing the direction
	 * @param  level     the level of recursion
	 * @param  k         the k value
	 * @return           the calculated color
	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
		Color color = geoPoint.geometry.getEmission();

		Vector v = ray.direction;
		Vector n = geoPoint.geometry.getNormal(geoPoint.point);

		// check that ray is not parallel to geometry
		double nv = alignZero(n.dotProduct(v));

		if (isZero(nv)) {
			return color;
		}
		Material material = geoPoint.geometry.getMaterial();
		color = color.add(calcLocalEffects(geoPoint, material, n, v, nv, k));
		return 1 == level ? color : color.add(calcGlobalEffect(geoPoint,material, n, v, level, k));
	}

	/**
	 * //add here the lights effects
	 *
	 * @param gp  geoPoint of the intersection
	 * @param v ray direction
	 * @return resulting color with diffuse and specular
	 */
	private Color calcLocalEffects(GeoPoint gp, Material material, Vector n, Vector v, double nv, Double3 k) {
		Color color = Color.BLACK;

		Point point = gp.point;

		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(point);
			double nl = alignZero(n.dotProduct(l));
			if (alignZero(nl * nv) > 0) { // sign(nl) == sign(nv)
				Double3 ktr = transparency(lightSource, l, n, gp);
				if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
//                if (unshaded(gp, lightSource, l, n)) {
					Color iL = lightSource.getIntensity(point).scale(ktr);
					color = color.add(
							calcDiffusive(material.kD, nl, iL),
							calcSpecular(material.kS, n, l, nl, v, material.nShininess, iL));
				}
			}
		}
		return color;
	}

	/**
	 * Calculates the specular reflection of a light source on a surface.
	 *
	 * @param  kS        the specular reflection coefficient
	 * @param  n         the normal vector of the surface
	 * @param  l         the light vector
	 * @param  nl        the dot product of n and l
	 * @param  v         the view vector
	 * @param  shininess the shininess of the surface
	 * @param  intensity the intensity of the light source
	 * @return           the color of the specular reflection
	 */
	private Color calcSpecular(Double3 kS, Vector n, Vector l, double nl,Vector v,int shininess,Color intensity) {
		Vector r = l.add(n.scale(-2 * nl)); // nl must not be zero!
		double minusVR = -alignZero(r.dotProduct(v));
		if (minusVR <= 0)
			return Color.BLACK; // view from direction opposite to r vector
		Double3 amount =kS.scale(Math.pow(minusVR, shininess));
		return intensity.scale(amount);
	}

	/**
	 * Calculates the diffusive color.
	 *
	 * @param  kD       the diffuse reflection coefficient
	 * @param  nl       the dot product of the normal and light direction vectors
	 * @param  intensity the color intensity of the light source
	 * @return          the diffusive color
	 */
	private Color calcDiffusive(Double3 kD, double nl,  Color intensity) {
		double abs_nl = Math.abs(nl);
		Double3 amount =kD.scale(abs_nl);
		return intensity.scale(amount);
	}

	/**
	 * Calculates the global effect for the given parameters.
	 *
	 * @param  geoPoint   the geometric point
	 * @param  material   the material
	 * @param  n          the normal vector
	 * @param  v          the view vector
	 * @param  level      the recursion level
	 * @param  k          the coefficient
	 * @return            the color representing the global effect
	 */
	private Color calcGlobalEffect(GeoPoint geoPoint,Material material,Vector n,Vector v, int level, Double3 k) {
		Double3 kR = material.kR;
		Double3 kT = material.kT;
		return calcRayBeamColor(level,k,kR,constructReflectedRays(geoPoint, v, n,material.kG))
				.add(calcRayBeamColor(level, k, kT, constructRefractedRays(geoPoint, v, n, material.kB)));
	}
	/**
	 * Calculates the color of a ray beam based on the given parameters.
	 *
	 * @param  level  the level of the ray beam
	 * @param  k      the k parameter
	 * @param  kx     the kx parameter
	 * @param  rays   the list of rays
	 * @return        the calculated color of the ray beam
	 */
	private Color calcRayBeamColor(int level, Double3 k, Double3 kx, List<Ray> rays) {
		if (rays.size() == 1)
			return calcGlobalEffect(rays.getFirst(), kx, level, k);
		Color color = Color.BLACK;
		for (Ray ray : rays) {
			color = color.add(calcGlobalEffect(ray, kx, level, k));
		}
		return color.scale(1.0 / rays.size());
	}


	/**
	 * Calculate the global effects for the given parameters.
	 *
	 * @param  ray      the ray
	 * @param  level    the level of recursion
	 * @param  k        the Double3 parameter
	 * @return          the calculated Color
	 */
	private Color calcGlobalEffect(Ray ray,Double3 kx, int level, Double3 k) {
		Color color = Color.BLACK;
		GeoPoint gp = findClosestIntersection(ray);
		Double3 kkx = k.product(kx);
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return color;
		if (gp == null)
			return scene.background.scale(kx);
		return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.direction)) ? Color.BLACK
				: calcColor(gp, ray, level - 1, kkx).scale(kx);
	}


	/**
	 * Constructs a refracted ray.
	 *
	 * @param  gp     the point of origin
	 * @param  v      the vector
	 * @param  n      the normal vector
	 * @return       a new Ray object
	 */
	private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
		return new Ray(gp.point, n, v);
	}

	/**
	 * Constructs a reflected ray based on the given parameters.
	 *
	 * @param  geoPoint  the point in 3D space
	 * @param  v         the incident vector
	 * @param  n         the normal vector
	 * @return           the reflected ray
	 */
	private Ray constructReflectedRay(GeoPoint geoPoint, Vector v, Vector n) {
		//r = v - 2.(v.n).n
		double vn = v.dotProduct(n);

		if (vn == 0) {
			return null;
		}

		Vector r = v.subtract(n.scale(2 * vn));
		return new Ray(geoPoint.point, n, r);
	}

	/**
	 * The method checks whether there is any object shading the light source from a
	 * point
	 *
	 * @param gp the point with its geometry
	 * @param lightSource the light source
	 * @param l  direction from light to the point
	 * @param n normal vector to the surface of gp
	 * @return accumulated transparency attenuation factor
	 */
	@SuppressWarnings("unused")
	private boolean unshaded(GeoPoint gp,LightSource lightSource ,Vector l, Vector n) {

		Vector lightDirection = l.scale(-1); // from point to light source
		double nl = n.dotProduct(lightDirection);

		Vector delta = n.scale(nl > 0 ? EPS : -EPS);
		Point pointRay = gp.point.add(delta);
		Ray lightRay = new Ray(pointRay, lightDirection);

		double maxdistance = lightSource.getDistance(gp.point);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay ,maxdistance);

		if (intersections == null){
			return true;
		}

		for (var item : intersections){
			if (item.geometry.getMaterial().kT.lowerThan(MIN_CALC_COLOR_K)){
				return false;
			}
		}

		return true;
	}

	/**
	 * The method checks whether there is any object shading the light source from a
	 * point
	 *
	 * @param gp the point with its geometry
	 * @param lightSource light source
	 * @param l  direction from light to the point
	 * @param n normal vector from the surface towards the geometry
	 *
	 * @return accumulated transparency attenuation factor
	 */
	private Double3 transparency(LightSource lightSource, Vector l, Vector n, GeoPoint gp) {
		// Pay attention to your method of distance screening
		Vector lightDirection = l.scale(-1); // from point to light source
		Point point = gp.point;
		Ray lightRay = new Ray(point, n, lightDirection);

		double maxdistance = lightSource.getDistance(point);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,maxdistance);

		if (intersections == null)
			return Double3.ONE;

		Double3 ktr = Double3.ONE;
		for (var geo : intersections) {
			ktr = ktr.product(geo.geometry.getMaterial().kT);
			if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
				return Double3.ZERO;
			}
		}
		return ktr;
	}

	/**
	 * Finds the closest intersection point of a ray with the scene geometries.
	 *
	 * @param  ray  the ray for which to find the closest intersection
	 * @return      the closest intersection point, or null if no intersection is found
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null) {
			return null;
		}
		return ray.findClosestGeoPoint(intersections);
	}
	/**
	 * Constructs reflected rays based on the given parameters.
	 *
	 * @param  geoPoint  the geometric point
	 * @param  v         the vector
	 * @param  n         the normal vector
	 * @param  kG        the constant factor
	 * @return           a list of reflected rays
	 */
	private List<Ray> constructReflectedRays(GeoPoint geoPoint, Vector v, Vector n, double kG) {
		Ray reflectedRay = constructReflectedRay(geoPoint, v, n);
		double res = reflectedRay.direction.dotProduct(n);
		if (kG == 0){
			return List.of(reflectedRay);
		}
		else{
			return new TargetArea(reflectedRay, kG).constructRayBeamGrid().stream()
					.filter(r -> r.direction.dotProduct(n) * res > 0).collect(Collectors.toList());

		}
	}
	/**
	 * Construct refracted rays based on the given parameters.
	 *
	 * @param  geoPoint  the geometric point
	 * @param  v         the vector v
	 * @param  n         the normal vector
	 * @param  kB        the value of k
	 * @return           the list of refracted rays
	 */
	private List<Ray> constructRefractedRays(GeoPoint geoPoint, Vector v, Vector n, double kB) {
		Ray reflectedRay = constructRefractedRay(geoPoint, v, n);
		double res = reflectedRay.direction.dotProduct(n);
		if (kB == 0){
			return List.of(reflectedRay);
		}
		else{
			return new TargetArea(reflectedRay, kB).constructRayBeamGrid().stream()
					.filter(r -> r.direction.dotProduct(n) * res > 0).collect(Collectors.toList());

		}
	}
}

