package renderer;

import lighting.AmbientLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {
    private final Scene          scene  = new Scene("Test scene");
    /** Camera builder of the tests */
    private final Camera.Builder camera = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), Vector.Y)
            .setVpDistance(100)
            .setVpSize(500, 500);

    @Test
    public void writeToImage() throws CloneNotSupportedException {
        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), Double3.ONE))
                .setBackground(new Color(YELLOW));
        camera
                .setImageWriter(new ImageWriter("empty image grid", 800, 500))
                .build()
                .renderImage()
                .printGrid(50, new Color(RED))
                .writeToImage();
    }
}