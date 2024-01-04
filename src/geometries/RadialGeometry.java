package geometries;

public abstract class RadialGeometry extends Geometry{
    final protected double radius;

    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
