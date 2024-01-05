package primitives;

/**
 * Class Ray is the basic class representing a ray in 3D space.
 */
public class Ray {
    /**
     * head point of the ray
     */
    private final Point head;
    /**
     * direction of the ray
     */
    private final Vector direction;

    /**
     * @param head head point of the ray
     * @param direction direction of the ray
     */
    public Ray(Point head,Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
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
