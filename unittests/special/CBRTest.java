package special;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;


import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.*;

class CBRTest {

	@Test
	void test1() {
		Sphere sphere = new Sphere(new Point(1, 1, 1), 2);
		Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
		Plane pln = new Plane(new Point(1, 1, 0), new Vector(0, 0, 1));
		Geometries geo = new Geometries(sphere, triangle, pln);
		geo.minMaxPoints();
	}

	@Test
	void test2() {
		Triangle triangle = new Triangle(new Point(1, -3, 0), new Point(2, 5, 0), new Point(0, 0, 1));
		CBR cbr1 = new CBR(triangle);
		new CBR(cbr1, triangle);
		cbr1.findGeoIntersections(new Ray(new Point(-0.1, 0, 0), new Vector(1, 1, 1)));
	}

	@Test
	void test3() {

		double MINIMIZER = 0.2;

		Geometries geos = new CBR( //
				new Plane(new Point(0, 0, 0), new Vector(0, 0, 1)).setEmission(new Color(249, 237, 176).scale(MINIMIZER)),
				new Triangle(new Point(0, 0, 0), new Point(2, 0, 0), new Point(1, 1, 1.5)).setEmission(new Color(232, 196, 19).scale(MINIMIZER)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.3).setShininess(100)), //
				new Triangle(new Point(0, 0, 0), new Point(0, 2, 0), new Point(1, 1, 1.5)).setEmission(new Color(232, 196, 19).scale(MINIMIZER)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)), //
				new Triangle(new Point(0, 2, 0), new Point(2, 2, 0), new Point(1, 1, 1.5)).setEmission(new Color(232, 196, 19).scale(MINIMIZER)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)), //
				new Triangle(new Point(2, 2, 0), new Point(2, 0, 0), new Point(1, 1, 1.5)).setEmission(new Color(232, 196, 19).scale(MINIMIZER)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)), //
				new Sphere(new Point(-1, 1, 2), 0.5).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Polygon(new Point(1, -1, 0.001), new Point(1, -4, 0.001), new Point(-1, -4, 0.001), new Point(-1, -1, 0.001)).setEmission(new Color(BLUE).scale(MINIMIZER)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.3).setShininess(500).setKr(0.8)),
				new Polygon(new Point(-2, -2, 0), new Point(-2.3, -2, 0), new Point(-2.3, -2, 0.8), new Point(-2, -2, 0.8)).setEmission(new Color(153, 76, 0).scale(MINIMIZER)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.3).setShininess(500)),
				new Polygon(new Point(-2.3, -2, 0), new Point(-2.3, -2, 0.8), new Point(-2.3, -1.7, 0.8), new Point(-2.3, -1.7, 0)).setEmission(new Color(153, 76, 0).scale(MINIMIZER)) //
						.setMaterial(new Material().setKd(0.8).setKs(0.3).setShininess(500))
		);

		geos.findGeoIntersections(new Ray(new Point(-0.1, 0, 0), new Vector(1, 1, 1)));

	}

	@Test
	void test4() {
		Sphere sphere1 = new Sphere(new Point(1, 1, 1), 2);
		Sphere sphere2 = new Sphere(new Point(3, 2, 3), 1);
		Triangle triangle = new Triangle(new Point(1, -3, 0), new Point(2, 5, 0), new Point(0, 0, 1));
		new CBR(triangle, sphere1, sphere2);
	}
}
