package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    Point  p1         = new Point(1, 2, 3);
    Point  p2         = new Point(2, 4, 6);
    Point  p3         = new Point(2, 4, 5);
    // Test point operations ================================================
    @Test
    void subtract() {
        // Subtract points
        assertEquals(p2.subtract(p1), v1, "ERROR: (point2 - point1) does not work correctly");

        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), //
                "ERROR: (point - itself) throws wrong exception");
    }

    @Test
    void add() {
        // Add vector to point
        assertEquals(p1.add(v1), p2, "ERROR: (point + vector) = other point does not work correctly");
        assertEquals(p1.add(v1Opposite), Point.ZERO, "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    @Test
    void distanceSquared() {
        assertEquals(p1.distanceSquared(p1), 0, "ERROR: point squared distance to itself is not zero");
        //if (!isZero(p1.distanceSquared(p1)))
        assertEquals(p1.distanceSquared(p3), 9, "ERROR: squared distance between points is wrong");
        assertEquals(p3.distanceSquared(p1), 9, "ERROR: squared distance between points is wrong");
    }

    @Test
    void distance() {
        assertEquals(p1.distance(p1), 0, "ERROR: point distance to itself is not zero");
        assertEquals(p1.distance(p3), 3, "ERROR: squared distance between points to itself is wrong");
        assertEquals(p3.distance(p1), 3, "ERROR: squared distance between points to itself is wrong");
    }
}