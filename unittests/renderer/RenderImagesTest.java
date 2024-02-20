package renderer;
import geometries.*;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import java.util.List;
import java.util.Random;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;


public class RenderImagesTest {
	Point a = new Point(40, 20, 30);
	Point b = new Point(50, 30, 0);
	Point c = new Point(40, 30, 0);
	Point d = new Point(40, 20, 0);
	Point e = new Point(50, 30, 30);
	Point f = new Point(40, 30, 30);
	Point g = new Point(400, 600, 0);
	Point h = new Point(0, 150, 0);
	Point i = new Point(-100, 0, 0);
	Point j = new Point(500, -1000, 0);

	private final Material material = new Material().setKd(new Double3(0.7, 0.6, 0.4)).setKs(new Double3(0.7, 0.8, 0.3)).setnShininess(300);
	private final Scene scene = new Scene("10AndMoreShapesImage");
	private final Camera.Builder cameraBuilder = Camera.getBuilder()
			.setDirection(new Vector(120, -70, -10), new Vector(0,0,1))
			.setRayTracer(new SimpleRayTracer(scene))
			.setLocation(new Point(-80,100,40))
			.setVpDistance(200)
            .setVpSize(150, 150)
            .setImageWriter(new ImageWriter("10AndMoreShapesImage", 500, 500));
	@Test
	public void renderImagesTest() {
		scene.setAmbientLight(new AmbientLight(new Color(0.15, 0.15, 0.15), new Double3(0.5, 0.5, 0.5)))
				.setBackground(new Color(113, 167, 253));

		for (int i = 0; i < 40; i++) {
			Random rand = new Random();
			Sphere sphere = new Sphere(new Point(rand.nextInt(30, 50), rand.nextInt(15, 40), rand.nextInt(25, 70)), rand.nextInt(7, 10));
			scene.geometries.add(sphere.setMaterial(material
							.setKt(new Double3(rand.nextDouble(0, 0.6)))
							.setKr(new Double3(rand.nextDouble(0, 0.05))))
					.setEmission(new Color(rand.nextInt(40, 100), rand.nextInt(130, 150), rand.nextInt(60, 90))));
		}

		scene.geometries.add(new Polygon(a, e, b, d).setMaterial(material.setKt(new Double3(0.2))).setEmission(new Color(107, 73, 43)),
				new Polygon(b, e, f, c).setMaterial(material.setKt(new Double3(0.2))).setEmission(new Color(107, 73, 43)),
				new Polygon(d, c, f, a).setMaterial(material.setKt(new Double3(0.2))).setEmission(new Color(107, 73, 43)),
				new Triangle(a, e, f).setMaterial(material.setKt(new Double3(0.2))).setEmission(new Color(107, 73, 43)),

				new Polygon(g, h, i, j).setMaterial(material.setKt(new Double3(0.2))).setEmission(new Color(56, 178, 44)));

		scene.lights.add(new SpotLight(new Color(97, 194, 152), new Point(-80, 50, 40), new Vector(50, -70, -10)));

		cameraBuilder.build().renderImage().writeToImage();
	}
}
