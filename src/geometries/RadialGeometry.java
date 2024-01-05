package geometries;

public abstract class RadialGeometry extends Geometry{
    /**
     * The radius of the geometry
     */
    final protected double radius;

    /**
     * @param radius the radius of the geometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
