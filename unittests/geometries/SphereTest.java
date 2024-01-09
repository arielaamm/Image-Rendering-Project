package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    /**
     * Unit tests for geometries.Sphere class
     */
    Sphere sphere = new Sphere(1,new Point(0,0,0));
    @Test
    void getNormal() {
        Vector normal = sphere.getNormal(new Point(1,0,0));
        // ================ Equivalence Partitions Tests ==============
        //TC01: test that normal is correct
        assertTrue(normal.equals(new Vector(1,0,0).normalize()) || normal.equals(new Vector(1,0,0).normalize().scale(-1)),"ERROR: sphere normal is not correct");
        //TC02: test that normal is in the right length
        assertEquals(1, normal.length(), "ERROR: plane normal is not in the right length");

    }
}