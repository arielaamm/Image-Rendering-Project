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
    Tube tube = new Tube(1,new Ray(new Point(0,0,-1),new Vector(0,0,1)));
    @Test
    void getNormal() {
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

        //1. TC01: Ray is outside the Tube, positioned in front of the beginning of a ray (Returns: 0 points)

        //2. TC02: Ray starts before and crosses the Tube (Returns: 2 points)
        //3. TC03: Ray starts inside the Tube (Returns: 1 point)
        //4. TC04: Ray starts after the Tube (Returns: 0 points)

        //**Group: Angles between vectors**

        //5. TC05: Ray is at 0 degrees angle relative to the Tube axis (Returns: Varies)
        //6. TC06: Ray is at 90 degrees angle relative to the Tube axis (Returns: Varies)

        //**Group: Combination of position and angles**

        //7. TC07: Ray starts before the Tube, at 0 degrees angle (Returns: Varies)
        //8. TC08: Ray starts before the Tube, at 90 degrees angle (Returns: Varies)
        //9. TC09: Ray starts inside the Tube, at 0 degrees angle (Returns: Varies)
        //10. TC10: Ray starts inside the Tube, at 90 degrees angle (Returns: Varies)
        //11. TC11: Ray starts after the Tube, at 0 degrees angle (Returns: Varies)
        //12. TC12: Ray starts after the Tube, at 90 degrees angle (Returns: Varies)

        //**Group: Intersection at the Tube ends**

        //13. TC13: Ray's line intersects at one end of the Tube (Returns: Varies)
        //14. TC14: Ray's line intersects at the other end of the Tube (Returns: Varies)

        //**Group: Special cases**

        //15. TC15: Ray's line is outside, and the ray is orthogonal to the line connecting the ray start to the Tube's axis (Returns: Varies)

        // =============== Boundary Values Tests =================

        //**Group: Ray's line crosses the Tube (but not the center)**

        //16. TC16: Ray starts at the Tube and goes inside (Returns: 1 point)
        //17. TC17: Ray starts at the Tube and goes outside (Returns: 0 points)

        //**Group: Ray's line goes through the center**

        //18. TC18: Ray starts before the Tube (Returns: 2 points)
        //19. TC19: Ray starts at the Tube and goes inside (Returns: 1 point)
        //20. TC20: Ray starts inside (Returns: 1 point)
        //21. TC21: Ray starts at the center (Returns: 1 point)
        //22. TC22: Ray starts at the Tube and goes outside (Returns: 0 points)
        //23. TC23: Ray starts after the Tube (Returns: 0 points)

        //**Group: Ray's line is tangent to the Tube (all tests 0 points)**

        //24. TC24: Ray starts before the tangent point (Returns: 0 points)
        //25. TC25: Ray starts at the tangent point (Returns: 0 points)
        //26. TC26: Ray starts after the tangent point (Returns: 0 points)

        //**Group: Special cases**

        //27. TC27: Ray's line is outside, and the ray is orthogonal to the line connecting the ray start to the Tube's axis (Returns: 0 points)

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