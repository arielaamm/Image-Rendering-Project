package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class PlaneTest {
    /**
     * Unit tests for geometries.Plane class
     */

    Point p1 = new Point(0,0,1);
    Point p11_sameLine = new Point(0,0,2);
    Point p12_sameLine = new Point(0,0,3);
    Point p2 = new Point(1,0,0);
    Point p3 = new Point(0,1,0);

    @Test
    void  testConstructor() {
        //=============== Boundary Values Tests ==================
        //TC01: test two or more point are collided and it's not allowed
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1,p1,p2),"ERROR: two or more point are collided and it's not allowed");
        //TC02: test 3 points are on the same line and it's not allowed
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1,p11_sameLine,p12_sameLine),"ERROR: 3 Points on the same line and it's not allowed");
    }

    @Test
    void getNormal() {
        Plane plane = new Plane(p1,p2,p3);
        Vector normal = plane.getNormal();
        // ================ Equivalence Partitions Tests ==============
        //TC01: test that normal is correct
        assertTrue(normal.equals(new Vector(1,1,1).normalize()) || normal.equals(new Vector(1,1,1).normalize().scale(-1)), "ERROR: plane normal is not correct");
        //TC02: test that normal is in the right length
        assertEquals(1, normal.length(), "ERROR: plane normal is not in the right length");

    }

    Point p10 = new Point(0,0,2);
    Point p11 = new Point(2,2,2);
    Point p12 = new Point(2,0,0);
    Plane plane = new Plane(p10,p11,p12);
    Point p13 = new Point(4,0,0);
    Vector v = new Vector(-4,0,4);
    Ray ray1 = new Ray(p13,v);
    Ray ray2 = new Ray(p10,v);

    @Test
    void findIntsersections() {
        //=============== Equivalence Partitions Tests ==============
        //TC01: test of parallel ray intersects the plane
        assertTrue(isZero(plane.findIntsersections(ray1).size()), "ERROR: parallel ray does not intersect the plane");
        assertTrue(isZero(plane.findIntsersections(ray2).size()), "ERROR: parallel ray does not intersect the plane");
        //TC02: test of orthogonal ray intersects the plane
        assertEquals(0,plane.findIntsersections(plane.getNormal().scale(-1)).size() , "ERROR: orthogonal ray intersect the plane more than 1 time");


    }
}