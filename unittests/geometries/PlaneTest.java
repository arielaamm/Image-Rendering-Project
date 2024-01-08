package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    /**
     * Unit tests for geometries.Plane class
     */

    Point p1 = new Point(0,0,1);
    Point p11_sameLine = new Point(0,0,2);
    Point p12_sameLine = new Point(0,0,3);
    Point p1_collide = new Point(0,0,1);
    Point p2 = new Point(1,0,0);
    Point p3 = new Point(0,1,0);

    @Test
    void  testConstructor() {
        //=============== Boundary Values Tests ==================
        //TC01: test two or more point are collided and it's not allowed
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1,p1_collide,p2),"ERROR: two or more point are collided and it's not allowed");
        //TC02: test two or more point are on the same line and it's not allowed
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1,p11_sameLine,p12_sameLine),"ERROR: two or more point are on the same line and it's not allowed");
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
}