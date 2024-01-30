package geometries;


import primitives.Color;
import primitives.Vector;
import primitives.Point;

/**
 * Abstract class Geometry is the base class for all geometries in this project.
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;

    /**
     * Retrieves the emission color.
     *
     * @return         	the emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param  emission  the color to set as the emission
     * @return           the updated Geometry object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Returns the normal vector of the geometry at the given point.
     *
     * @param p the point at which to calculate the normal vector
     * @return the normal vector of the geometry at the point
     */
    public abstract Vector getNormal(Point p);


}
