package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(2, 3, 4);
    @Test
    void testAdd() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: test that two vector are added
        Vector v3 = v1.add(v2);
        assertEquals(new Vector(3, 5, 7), v3, "add() wrong result");

        // =============== Boundary Values Tests ==================
        // TC11: test that two vector are added
        Vector v4 = v1.add(v1);
        assertEquals(new Vector(2, 4, 6), v4, "add() wrong result");

    }

    @Test
    void scale() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: test that two vector are scaled
        Vector v5 = v1.scale(2);
        assertEquals(new Vector(2, 4, 6), v5, "scale() wrong result");

        // =============== Boundary Values Tests ==================
        // TC11: test that vector is scaled to zero
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test the dotProduct() method of the Vector class.
     */
    @Test
    void dotProduct() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: Test that the dot product of a vector
        double v7 = v1.dotProduct(v2);
        assertEquals(20, v7, "dotProduct() returned wrong result");

        // =============== Boundary Values Tests ==================

        // TC11: Test that the dot product of a vector with itself is calculated correctly
        double v8 = v1.dotProduct(v1);
        assertEquals(14, v8, "dotProduct() returned wrong result");
    }
    /**
     * Test method for
     * {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the cross product of two vectors
        Vector v9 = v1.crossProduct(v2);
        assertEquals(new Vector(-1, 2, -1), v9, "crossProduct() returned wrong result");

        // =============== Boundary Values Tests ==================


    }

    @Test
    void lengthSquared() {
    }

    @Test
    void length() {
    }

    @Test
    void normalize() {
    }
}