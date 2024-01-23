package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

/**
 * The Camera class represents a camera in the scene.
 */
public class Camera implements Cloneable{
    /**
     * The location of the camera
     */
    public static class Builder{
        private final Camera camera = new Camera();

        /**
         * The default constructor
         */
        public Builder() {}


        /**
         * Set the location of the camera
         * @param location
         * @return this
         */
        public Builder setLocation(Point location){
            camera.location = location;
            return this;
        }

        /**
         * Set the direction of the camera
         * @param Vto
         * @param Vup
         * @return this
         */
        public Builder setDirection(Vector Vto,Vector Vup){
            if (Vto.crossProduct(Vup).length() == 0)
                throw new IllegalArgumentException("Vto and Vup are parallel");
            camera.vTo = Vto.normalize();
            camera.vUp = Vup.normalize();
            camera.vRight = Vto.crossProduct(Vup).normalize();
            return this;
        }

        /**
         * Set the size of the view plane
         * @param heightViewPlane
         * @param widthViewPlane
         * @return this
         */
        public Builder setVpSize(double heightViewPlane, double widthViewPlane){
            if (heightViewPlane < 0 || widthViewPlane < 0)
                throw new IllegalArgumentException("The height and width of the view plane must be positive");
            camera.heightViewPlane = heightViewPlane;
            camera.widthViewPlane = widthViewPlane;
            return this;
        }

        /**
         * Set the distance of the view plane
         * @param distanceFromViewPlane distance from the view plane
         * @return this
         */
        public Builder setVpDistance(double distanceFromViewPlane){
            if (distanceFromViewPlane < 0)
                throw new IllegalArgumentException("The distance from the view plane must be positive");
            camera.distanceFromViewPlane = distanceFromViewPlane;
            return this;
        }

        /**
         * Build the camera
         * @return Camera
         * @throws CloneNotSupportedException
         */
        public Camera build() throws CloneNotSupportedException {
            String missingResource = "Missing Resource";
            if(camera.location == null)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"location");
            if(camera.vTo == null || camera.vUp == null)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"direction");
            if(camera.heightViewPlane == 0.0 || camera.widthViewPlane == 0.0)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"vpSize");
            if(camera.distanceFromViewPlane == 0.0)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"vpDistance");

            if(camera.vTo.crossProduct(camera.vUp).length() == 0)
                throw new IllegalArgumentException("Vto and Vup are parallel");
            if(camera.heightViewPlane < 0.0 || camera.widthViewPlane < 0.0)
                throw new IllegalArgumentException("Negative size");
            if(camera.distanceFromViewPlane < 0.0)
                throw new IllegalArgumentException("Negative distance");

            camera.viewPlaneCenter = camera.location.add(camera.vTo.scale(camera.distanceFromViewPlane));

            return (Camera) camera.clone();
        }
    }

    private Point location;
    private Vector vTo;
    private Vector vRight;
    private Vector vUp;
    private double heightViewPlane = 0.0;
    private double widthViewPlane = 0.0;
    private double distanceFromViewPlane = 0.0;
    private Point viewPlaneCenter;
    private Camera(){}

    /**
     * Get the height of the view plane
     * @return Height of the view plane
     */
    public double getHeightViewPlane() {
        return heightViewPlane;
    }

    /**
     * Get the width of the view plane
     * @return Width of the view plane
     */
    public double getWidthViewPlane() {
        return widthViewPlane;
    }

    /**
     * get Location of the camera
     * @return Distance of the view plane
     */
    public double getDistanceFromViewPlane() {
        return distanceFromViewPlane;
    }

    /**
     * Get the builder
     * @return Builder
     */
    public static Builder getBuilder(){
        return new Builder();
    }

    /**
     * Set the location of the camera
     * @param nX - width of the view plane
     * @param nY - height of the view plane
     * @param j - index in the column
     * @param i - index in the row
     * @return Ray
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        double rY = heightViewPlane / nY;
        double rX = widthViewPlane / nX;
        double xJ = (j - ((nX - 1) / 2.0))* rX;
        double yI = -(i - ((nY - 1) / 2.0)) * rY;
        Point pIJ = this.viewPlaneCenter;
        if (xJ != 0)
            pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0)
            pIJ = pIJ.add(vUp.scale(yI));
        Vector Vij = pIJ.subtract(location);
        return new Ray(location, Vij);
    }
}
