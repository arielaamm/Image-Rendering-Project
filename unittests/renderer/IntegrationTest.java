package renderer;

import geometries.Geometry;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1);

    List<Point> pointsIntersections;

    @Test
    void testConstructRayWithShpere() throws CloneNotSupportedException {
        Sphere sphere;

        //TC01: First test case
        cameraBuilder.setLocation(new Point(0,0,0));
        cameraBuilder.build();
        assertEquals(2,getIntersections(new Sphere(1, new Point(0, 0, -3))).size());

        //TC02: Second test case
        cameraBuilder.setLocation(new Point(0,0,0.5));
        cameraBuilder.build();
        assertEquals(18, getIntersections(new Sphere(2.5, new Point(0, 0, -2.5))).size());

        //TC03: Third test case
        cameraBuilder.setLocation(new Point(0,0,0.5));
        cameraBuilder.build();
        assertEquals(10, getIntersections(new Sphere(2.5, new Point(0, 0, -2))).size());

        //TC04: Fourth test case
        cameraBuilder.setLocation(new Point(0,0,0.5));
        cameraBuilder.build();
        assertEquals(9, getIntersections(new Sphere(4, new Point(0, 0, 0))).size());

        //TC05: Fifth test case
        cameraBuilder.setLocation(new Point(0,0,0.5));
        cameraBuilder.build();
        assertEquals(0, getIntersections(new Sphere(4, new Point(0, 0, 1))).size());
    }

    @Test
    void testConstructRayWithPlane() {

    }
    @Test
    void testConstructRayWithTriangle() {

    }
    private List<Point> getIntersections(Geometry geometry) throws CloneNotSupportedException {
        pointsIntersections = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = cameraBuilder.build().constructRay(3,3, j, i);
                pointsIntersections.addAll(geometry.findIntersections(ray));
            }
        }
        return pointsIntersections;
    }
}