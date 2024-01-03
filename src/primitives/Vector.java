package primitives;

import static java.lang.Math.sqrt;

final public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x,y,z);
        if (Double3.ZERO.equals(xyz))
        { throw new IllegalArgumentException("the vector is the zero vector");}
    }
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
        { throw new IllegalArgumentException("the vector is the zero vector");}

    }

    public Vector add (Vector v){
        return new Vector(super.add(v).xyz);
    }

    public Vector scale (double d){
        return new Vector(xyz.scale(d));
    }

    public double dotProduct(Vector v){
        return xyz.d1 * v.xyz.d1
                + xyz.d2 * v.xyz.d2
                + xyz.d3 * v.xyz.d3;
    }

    public Vector crossProduct(Vector v){
        return new Vector(
                xyz.d2 * v.xyz.d3 - xyz.d3 * v.xyz.d2,
                xyz.d3 * v.xyz.d1 - xyz.d1 * v.xyz.d3,
                xyz.d1 * v.xyz.d2 - xyz.d2 * v.xyz.d1);

    }

    public double lengthSquared(){
        return new Vector(xyz).dotProduct(new Vector(xyz));
    }

    public double length(){
        return sqrt(lengthSquared());
    }

    public Vector normalize(){
        return new Vector(xyz.reduce(length()));
    }


    /**
     * Class Vector is the basic class representing a vector of Euclidean geometry in Cartesian
     * 3-Dimensional coordinate system.
     * @author Ariel and Yayir
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector other)) return false;
        return super.equals(other);
    }
    @Override
    public String toString() { return "->" + super.toString(); }


}
