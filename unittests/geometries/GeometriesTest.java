package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {
    Geometries geometries;

    @Test
    void findIntersections() {
        //============== Equivalence Partitions Tests ===============
        Geometries geometries01 = new Geometries(
                new Sphere(1, new Point(0,-2,0)),
                new Sphere(1, new Point(2,0,0)),
                new Triangle(new Point(0,0,0), new Point(0,1,0), new Point(-1,0,0)));
        List<Point> result01 = geometries01.findIntersections(new Ray(new Point(-1,-4,0),new Vector(2,2,0)));

        //TC01: Test some of the geometries are intersecting but other don't
        assertEquals(4,result01.size(),"ERROR: some of the geometries are not intersecting");
        assertEquals(List.of(new Point(0,-3,0),
                new Point(1,-2,0),
                new Point(2,-1,0),
                new Point(3,0,0)),result01,"ERROR: some of the intersections are not correct");
        //============= Boundary Values Tests ==================
        //TC11: Test empty geometries collection
        Geometries geometries11 = new Geometries();
        assertNull(geometries11.findIntersections(new Ray(new Point(-1,-4,0),new Vector(2,2,0))),"ERROR: some of the geometries are not intersecting");

        //TC12: Test none of the geometries are intersecting
        Geometries geometries12 = new Geometries(
                new Sphere(1, new Point(0,-2,0)),
                new Sphere(1, new Point(2,0,0)),
                new Triangle(new Point(0,0,0), new Point(0,1,0), new Point(-1,0,0)));
        assertNull(geometries12.findIntersections(new Ray(new Point(-1,-4,0),new Vector(-2,-2,0))),"ERROR: some of the geometries are not intersecting");

        //TC13: Test only 1 of the geometries are intersecting
        Geometries geometries13 = new Geometries(
                new Sphere(1, new Point(2,0,0)),
                new Triangle(new Point(0,0,0), new Point(0,1,0), new Point(-1,0,0)));
        List<Point> result13 = geometries13.findIntersections(new Ray(new Point(-1,-4,0),new Vector(2,2,0)));
        assertEquals(2,result13.size(),"ERROR: some of the geometries are not intersecting");
        assertEquals(List.of(
                new Point(2,-1,0),
                new Point(3,0,0)),result13,"ERROR: some of the intersections are not correct");
        //TC 14: Test all the geometries are intersecting
        Geometries geometries14 = new Geometries(
                new Sphere(1, new Point(0,-2,0)),
                new Sphere(1, new Point(2,0,0)),
                new Triangle(new Point(7,2.5,2), new Point(7,2.5,-2), new Point(4,4,0)));
        List<Point> result14 = geometries14.findIntersections(new Ray(new Point(-1,-4,0),new Vector(2,2,0)));
        assertEquals(5,result14.size(),"ERROR: some of the geometries are not intersecting");
        assertEquals(List.of(new Point(0,-3,0),
                new Point(1,-2,0),
                new Point(2,-1,0),
                new Point(3,0,0),new Point(6,3,0)),result14,"ERROR: some of the intersections are not correct");

    }
}