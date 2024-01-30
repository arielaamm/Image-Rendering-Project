package primitives;

import java.util.List;
import java.util.Objects;

/**
 * Class Ray is the basic class representing a ray in 3D space.
 */
public class Ray {
    /**
     * head point of the ray
     */
    public final Point head;
    /**
     * direction of the ray
     */
    public final Vector direction;

    /**
     * @param head head point of the ray
     * @param direction direction of the ray
     */
    public Ray(Point head,Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * @param t
     * @return the point "o" of the ray
     */
    public Point getPoint(double t) {
        return head.add(direction.scale(t));
    }

    /**
     * Calculates the value of T for a given point.
     *
     * @param  p    the point for which to calculate T
     * @return      the value of T for the given point
     */
    public double getT(Point p) {
        return (p.subtract(head)).dotProduct(direction);
    }

    /**
     * Finds the closest Point in the given list.
     *
     * @param  list  the list of Points
     * @return       the closest Point in the list
     */
    public Point findClosestPoint(List<Point> list){
        if (list == null) {
            return null;
        }
        Point closestPoint = list.getFirst();
        double minDistance = Double.MAX_VALUE;
        for (Point p : list) {
            if (head.distance(p) < minDistance) {
                minDistance = head.distance(p);
                closestPoint = p;
            }
        }
        return closestPoint;
    }
    /**
     * Overrides the equals() method to compare if two Ray objects are equal.
     *
     * @param  obj the object to compare with
     * @return     true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, direction);
    }

    /**
     * Returns a string representation of this object.
     *
     * @return          a string representation of this object
     */
    @Override
    public String toString() {
        return "head=" + head.toString() +
                ", direction=" + direction.toString();

    }



}
