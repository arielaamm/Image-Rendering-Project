package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    /**
     * Unit tests for geometries.Tube class
     */
    @Test
    void getNormal() {
        Tube tube = new Tube(1,new Ray(new Point(0,0,-1),new Vector(0,0,1)));

        Vector normal = tube.getNormal(new Point(1,0,0));
        // ================ Equivalence Partitions Tests ==============
        //TC01: test that normal is correct
        assertTrue(normal.equals(new Vector(1,0,0).normalize()) || normal.equals(new Vector(1,0,0).normalize().scale(-1)),"ERROR: tube normal is not correct");
        //TC02: test that normal is in the right length
        assertEquals(1, normal.length(), "ERROR: tube normal is not in the right length");

        // =============== Boundary Values Tests ==================
        normal = tube.getNormal(new Point(1,0,1));
        //TC11: test that normal is correct
        assertTrue(normal.equals(new Vector(1,0,0).normalize()) || normal.equals(new Vector(1,0,0).normalize().scale(-1)),"ERROR: tube normal is not correct");
        //TC12: test that normal is in the right length
        assertEquals(1, normal.length(), "ERROR: tube normal is not in the right length");
    }@Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //**Group: Ray's position relative to the Tube**
        Tube tube = new Tube(1,new Ray(new Point(0,-4,0),new Vector(0,4,3)));
        //1. TC01: Ray is outside the Tube, positioned in front of the beginning of a ray (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(2,-3,0),new Vector(2,2,0))), "Ray starts in front the tube and goes outside");
        //2. TC02: Ray starts before and crosses the Tube (Returns: 2 points)
        final var result4 = tube.findIntersections(new Ray(new Point(2,-3,0),new Vector(-4,0,0)));
        assertEquals(2, result4.size(), "Wrong number of points");
        assertEquals(List.of(new Point(0.8,-3,0), new Point(-0.8,-3,0)), result4, "Ray starts before the sphere");
        //3. TC03: Ray starts inside the Tube (Returns: 1 point)
        final var result5 = tube.findIntersections(new Ray(new Point(0,-3,0),new Vector(-4,0,0)));
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(List.of(new Point(-0.8,-3,0)), result5, "Ray starts before the sphere");
        //4. TC04: Ray starts after the Tube (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-3,-3,0),new Vector(-4,0,0))), "Ray starts in front the tube and goes outside");

        //**Group: General Angles between vectors**
        //5. TC05: Ray is at 0 degrees angle relative to the Tube axis (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(0.5,-4,0),new Vector(0,4,3))), "Ray starts in front the tube and goes outside");
        //6. TC06: Ray is at 90 degrees angle relative to the Tube axis (Returns: 2 points)
        final var result6 = tube.findIntersections(new Ray(new Point(-1.5,-3,0.5),new Vector(4.5,0,0)));
        assertEquals(2, result6.size(), "Wrong number of points");
        assertEquals(List.of(new Point(-0.979795897113271,-3,0.5), new Point(0.979795897113271,-3,0.5)), result6, "Ray starts before the sphere");

        //**Group: Combination of position and angles**
        //7. TC07: Ray starts before the Tube, at 0 degrees angle (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(1.5,-3.5,0.5),new Vector(0,4,3))), "Ray starts in front the tube and goes outside");
        //8. TC08: Ray starts before the Tube, at 90 degrees angle (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(1.5,-3.5,0.5),new Vector(1.5,0,0))), "Ray starts in front the tube and goes outside");
        //9. TC09: Ray starts inside the Tube, at 0 degrees angle (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(0.5,-4,0),new Vector(0,4,3))), "Ray starts in front the tube and goes outside");
        //10. TC10: Ray starts inside the Tube, at 90 degrees angle (Returns: 1 point)
        final var result7 = tube.findIntersections(new Ray(new Point(0,-3,0),new Vector(-2,0,0)));
        assertEquals(1, result7.size(), "Wrong number of points");
        assertEquals(List.of(new Point(-0.8,-3,0)), result7, "Ray starts before the sphere");
        //11. TC11: Ray starts after the Tube, at 0 degrees angle (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-1,-3,0),new Vector(0,4,3))), "Ray starts in front the tube and goes outside");
        //12. TC12: Ray starts after the Tube, at 90 degrees angle (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-1,-3,0),new Vector(1.5,0,0))), "Ray starts in front the tube and goes outside");

        // **Group: Ray's Orientation to Tube's Axis**
        // TC15: Ray is perpendicular to the Tube's axis (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-1,-3,0),new Vector(1.5,0,0))), "Ray starts in front the tube and goes outside");
        // TC16: Ray is parallel to the Tube's axis (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(0.5,-4,0),new Vector(0,4,3))), "Ray starts in front the tube and goes outside");


        //**Group: Special cases**

        //15. TC15: Ray's line is outside, and the ray is orthogonal to the line connecting the ray start to the Tube's axis (Returns: 0)
        assertNull(tube.findIntersections(new Ray(new Point(-1.5,-4,0),new Vector(1.5,0,0))), "Ray starts in front the tube and goes outside");

        // =============== Boundary Values Tests =================

        //**Group: Ray's line crosses the Tube (but not the center)**

        //16. TC16: Ray starts at the Tube and goes inside (Returns: 1 point)
        final var result8 = tube.findIntersections(new Ray(new Point(1,0,3),new Vector(-5,0,1)));
        assertEquals(1, result8.size(), "Wrong number of points");
        assertEquals(List.of(new Point(-0.950078003120125, 0, 2.609984399375975)), result8, "Ray starts before the sphere");
        //17. TC17: Ray starts at the Tube and goes outside (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(1,0,3),new Vector(2,0,-1))), "Ray starts in front the tube and goes outside");

        //**Group: Ray's line goes through the center**
        //18. TC18: Ray starts before the Tube (Returns: 2 points)
        final var result9 = tube.findIntersections(new Ray(new Point(3,0,3),new Vector(-5,0,0)));
        assertEquals(2, result9.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1,0,3), new Point(-1,0,3)), result9, "Ray starts before the tube");
        //19. TC19: Ray starts at the Tube and goes inside (Returns: 1 point)
        final var result10 = tube.findIntersections(new Ray(new Point(1,0,3),new Vector(-5,0,0)));
        assertEquals(1, result10.size(), "Wrong number of points");
        assertEquals(List.of(new Point(-1,0,3)), result10, "Ray starts at the tube and goes inside");
        //20. TC20: Ray starts inside (Returns: 1 point)
        final var result11 = tube.findIntersections(new Ray(new Point(0.5,0,3),new Vector(-5,0,0)));
        assertEquals(1, result11.size(), "Wrong number of points");
        assertEquals(List.of(new Point(-1,0,3)), result11, "Ray starts inside the tube");
        //21. TC21: Ray starts at the center (Returns: 1 point)
        final var result12 = tube.findIntersections(new Ray(new Point(0,0,3),new Vector(-5,0,0)));
        assertEquals(1, result12.size(), "Wrong number of points");
        assertEquals(List.of(new Point(-1,0,3)), result12, "Ray starts inside the tube");
        //22. TC22: Ray starts at the Tube and goes outside (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-1,0,3),new Vector(-5,0,0))), "Ray starts in front the tube and goes outside");
        //23. TC23: Ray starts after the Tube (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-2,0,3),new Vector(-5,0,0))), "Ray starts in front the tube and goes outside");

        //**Group: Ray's line is tangent to the Tube (all tests 0 points)**
        //24. TC24: Ray starts before the tangent point (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-3,-4,2),new Vector(3,1,0))), "Ray starts in front the tube and goes outside");
        //25. TC25: Ray starts at the tangent point (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(0,-3,2),new Vector(3,1,0))), "Ray starts in front the tube and goes outside");
        //26. TC26: Ray starts after the tangent point (Returns: 0 points)
        assertNull(tube.findIntersections(new Ray(new Point(2.4,-2.2,2),new Vector(3,1,0))), "Ray starts in front the tube and goes outside");

        //**Group: Special cases**

        //27. TC27: Ray's line is outside, and the ray is orthogonal to the line connecting the ray start to the Tube's axis (Returns: 2 points)

        //**Group: Intersection at the Tube ends**

        //28. TC28: Ray's line intersects at one end of the Tube (Returns: Varies)
        //29. TC29: Ray's line intersects at the other end of the Tube (Returns: Varies)

        //**Group: Combined boundary cases**

        //30. TC30: Ray starts at the Tube's axis, and the ray is orthogonal to it (Returns: Varies)
        //31. TC31: Ray starts at the Tube's axis, at 0 degrees angle (Returns: Varies)
        //32. TC32: Ray starts at the Tube's axis, at 90 degrees angle (Returns: Varies)

        //**Group: Ray's line is parallel to the Tube axis**

        //33. TC33: Ray is parallel to the Tube axis, outside the Tube (Returns: Varies)
        //34. TC34: Ray is parallel to the Tube axis, intersects the Tube (Returns: Varies)
        //35. TC35: Ray is parallel to the Tube axis, inside the Tube (Returns: Varies)

        //**Group: Ray's line is perpendicular to the Tube axis**

        //36. TC36: Ray is perpendicular to the Tube axis, outside the Tube (Returns: Varies)
        //37. TC37: Ray is perpendicular to the Tube axis, intersects the Tube (Returns: Varies)
        //38. TC38: Ray is perpendicular to the Tube axis, inside the Tube (Returns: Varies)

        //**Group: Ray's line is parallel to the Tube's end**

        //39. TC39: Ray is parallel to the Tube's end, outside the Tube (Returns: Varies)
        //40. TC40: Ray is parallel to the Tube's end, intersects the Tube (Returns: Varies)
        //41. TC41: Ray is parallel to the Tube's end, inside the Tube (Returns: Varies)

        //**Group: Ray's line is perpendicular to the Tube's end**

        //42. TC42: Ray starts perpendicular to the Tube's end (Returns: Varies)

    }


}