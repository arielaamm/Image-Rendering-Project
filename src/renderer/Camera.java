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
        private static Camera camera;

        /**
         * Create a new camera
         * @param camera camera
         */
        public Builder(Camera camera) {
            Builder.camera = camera;
        }

        /**
         * Create a new camera
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
            camera.Vto = Vto.normalize();
            camera.Vup = Vup.normalize();
            camera.Vright = Vto.crossProduct(Vup).normalize();
            return this;
        }

        /**
         * Set the size of the view plane
         * @param heightViewPlane
         * @param widthViewPlane
         * @return this
         */
        public Builder setVpSize(double heightViewPlane, double widthViewPlane){
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
            camera.distanceFromViewPlane = distanceFromViewPlane;
            return this;
        }
        public Camera build() throws CloneNotSupportedException {
            if(camera.location == null)
                throw new MissingResourceException("Missing Resource",Camera.class.getSimpleName(),"location");
            if(camera.Vto == null || camera.Vup == null)
                throw new MissingResourceException("Missing Resource",Camera.class.getSimpleName(),"direction");
            if(camera.heightViewPlane == 0.0 || camera.widthViewPlane == 0.0)
                throw new MissingResourceException("Missing Resource",Camera.class.getSimpleName(),"vpSize");
            if(camera.distanceFromViewPlane == 0.0)
                throw new MissingResourceException("Missing Resource",Camera.class.getSimpleName(),"vpDistance");

            if(camera.Vto.crossProduct(camera.Vup).length() == 0)
                throw new IllegalArgumentException("Vto and Vup are parallel");
            if(camera.heightViewPlane < 0.0 || camera.widthViewPlane < 0.0)
                throw new IllegalArgumentException("Negative size");
            if(camera.distanceFromViewPlane < 0.0)
                throw new IllegalArgumentException("Negative distance");
            if (camera.heightViewPlane == 0.0 || camera.widthViewPlane == 0.0)
                throw new IllegalArgumentException("Zero size");
            if (camera.distanceFromViewPlane == 0.0)
                throw new IllegalArgumentException("Zero distance");
            return (Camera)camera.clone();
        }
    }

    private Point location;
    private Vector Vto;
    private Vector Vright;
    private Vector Vup;
    private double heightViewPlane = 0.0;
    private double widthViewPlane = 0.0;
    private double distanceFromViewPlane = 0.0;

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
        return new Ray(location, Vto);
    }
}
