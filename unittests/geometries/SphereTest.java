package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    /**
     * Unit tests for geometries.Sphere class
     */
    private final Point p100 = new Point(1, 0, 0);
    Sphere normalSphere = new Sphere(1,new Point(0,0,0));
    Sphere sphere = new Sphere(1d, p100);

    @Test
    void getNormal() {
        Vector normal = sphere.getNormal(new Point(1,0,0));
        // ================ Equivalence Partitions Tests ==============
        //TC01: test that normal is correct
        assertTrue(normal.equals(new Vector(1,0,0).normalize()) || normal.equals(new Vector(1,0,0).normalize().scale(-1)),"ERROR: sphere normal is not correct");
        //TC02: test that normal is in the right length
        assertEquals(1, normal.length(), "ERROR: plane normal is not in the right length");

    }

    private final Point p001 = new Point(0, 0, 1);

    private final Vector v001 = new Vector(0, 0, 1);
    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findIntersections(new Ray(new Point(1,0.5,0),new Vector(1.172498,0.462807,0)))
               .stream().sorted(Comparator.comparingDouble(p -> p.distance(new Point(1,0.5,0)))).toList();
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(exp, result2, "Ray starts inside the sphere");
        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0,0.5,0),new Vector(-0.975317,-0.002016,0))), "Ray starts after the sphere");


        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        final var result3 = sphere.findIntersections(new Ray(new Point(0.154639,-0.534196,0),new Vector(1.581097,-0.143071,0)))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(new Point(1,0.5,0)))).toList();
        assertEquals(1, result3.size(), "Wrong number of points");
        assertEquals(exp, result2, "Ray starts at sphere and goes inside");
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0.154639,-0.534196,0),new Vector(-0.718334,-0.026739,0))), "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        final var result4 = sphere.findIntersections(new Ray(new Point(3,0,0),new Vector(-6,0,0)))
               .stream().sorted(Comparator.comparingDouble(p -> p.distance(new Point(3,0,0) ))).toList();
        assertEquals(2, result4.size(), "Wrong number of points");
        assertEquals(exp, result4, "Ray starts before the sphere");
        // TC14: Ray starts at sphere and goes inside (1 points)
        final var result5 = sphere.findIntersections(new Ray(new Point(2,0,0),new Vector(-4,0,0)))
              .stream().sorted(Comparator.comparingDouble(p -> p.distance(new Point(2,0,0)))).toList();
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(exp, result5, "Ray starts at sphere and goes inside");
        // TC15: Ray starts inside (1 points)
        final var result6 = sphere.findIntersections(new Ray(new Point(0.5,0,0),new Vector(-1,0,0)))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(new Point(0.5,0,0)))).toList();
        assertEquals(1, result6.size(), "Wrong number of points");
        assertEquals(exp, result6, "Ray starts inside");
        // TC16: Ray starts at the center (1 points)
        final var result7 = sphere.findIntersections(new Ray(new Point(1,0,0),new Vector(-1,0,0)))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(new Point(1,0,0)))).toList();
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(exp, result7, "Ray starts at the center");
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,0),new Vector(-1,0,0))), "Ray starts at sphere and goes outside");
        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1,0,0),new Vector(-2,0,0))), "Ray starts at sphere and goes outside");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,-0.5,0),new Vector(0,1,0))), "Ray starts before the tangent point");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,0),new Vector(0,1,0))), "Ray starts at the tangent point");
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,0.5,0),new Vector(0,1,0))), "Ray starts after the tangent point");


        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(-1,0,0),new Vector(0,0,1))), "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
    }
    }