package primitives;

final class Point {
    protected Double3 xyz;
    public Point(double x,double y,double z) {
        this.xyz = new Double3(x,y,z);
    }
    Point(Double3 xyz) {
        this.xyz = xyz;
    }
    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    public Point add(Point p) {
        return new Point(xyz.add(p.xyz));
    }
    public double distanceSquared(Point p) {
        Point temp = new Point(xyz.subtract(p.xyz).product(xyz.subtract(p.xyz)));
        return temp.xyz.
    }
    public double distance(Point p) {
        return Math.sqrt(this.distanceSquared(p));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return xyz.toString();
    }
}
