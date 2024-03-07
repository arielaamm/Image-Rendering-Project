package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.stream.IntStream;

/**
 * The Camera class represents a camera in the scene.
 */
public class Camera implements Cloneable{
    /**
     * The location of the camera
     */
    public static class Builder {
        private final Camera camera = new Camera();

        /**
         * Builds a new Camera object
         */
        public Builder() {}


        public Builder setMultithreading(int thread)
        {
            if(thread < -2) throw new IllegalArgumentException("Multithreading must be -2 or higher");
            if(thread >=-1) camera.threadsCount = thread;
            else{ // -2
                int cores = Runtime.getRuntime().availableProcessors() - camera.SPARE_THREADS;
                camera.threadsCount = cores <= 2 ? 1 : cores;
            }
            return this;
        }

        public Builder setDebugPrint( double interval)
        {
            camera.printInterval = interval;
            return this;
        }
        /**
         * Set the location of the camera
         * @param location
         * @return this
         */
        public Builder setLocation(Point location){
            camera.viewPlane.location = location;
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
            camera.viewPlane.vTo = Vto.normalize();
            camera.viewPlane.vUp = Vup.normalize();
            camera.viewPlane.vRight = Vto.crossProduct(Vup).normalize();
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
            camera.viewPlane.heightViewPlane = heightViewPlane;
            camera.viewPlane.widthViewPlane = widthViewPlane;
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
            camera.viewPlane.distanceFromViewPlane = distanceFromViewPlane;
            return this;
        }

        /**
         * Sets the image writer for the camera.
         *
         * @param  imageWriter  the image writer to be set
         * @return              the updated Builder instance
         */
        public Builder setImageWriter(ImageWriter imageWriter){
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the ray tracer for the camera and returns the updated Builder.
         *
         * @param  rayTracer  the ray tracer to be set
         * @return            the updated Builder
         */
        public Builder setRayTracer(RayTracerBase rayTracer){
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Build the camera
         * @return Camera
         */
        public Camera build() {
            String missingResource = "Missing Resource";
            if(camera.viewPlane.location == null)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"location");
            if(camera.viewPlane.vTo == null || camera.viewPlane.vUp == null)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"direction");
            if(camera.viewPlane.heightViewPlane == 0.0 || camera.viewPlane.widthViewPlane == 0.0)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"vpSize");
            if(camera.viewPlane.distanceFromViewPlane == 0.0)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"vpDistance");
            if(camera.imageWriter == null)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"imageWriter");
            if (camera.rayTracer == null)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"rayTracer");

            if(camera.viewPlane.vTo.crossProduct(camera.viewPlane.vUp).length() == 0)
                throw new IllegalArgumentException("Vto and Vup are parallel");
            if(camera.viewPlane.heightViewPlane < 0.0 || camera.viewPlane.widthViewPlane < 0.0)
                throw new IllegalArgumentException("Negative size");
            if(camera.viewPlane.distanceFromViewPlane < 0.0)
                throw new IllegalArgumentException("Negative distance");

            camera.viewPlane.viewPlaneCenter = camera.viewPlane.location.add(camera.viewPlane.vTo.scale(camera.viewPlane.distanceFromViewPlane));

            return camera.clone();
        }
    }


    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private final TargetArea viewPlane = new TargetArea();
    private PixelManager pixelManager;

    private int threadsCount = 0; // -2 auto, -1 range/stream, 0 no threads, 1+ number of threads
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private double printInterval = 0; // printing progress percentage interval


    private Camera(){}

    /**
     * Get the height of the view plane
     * @return Height of the view plane
     */
    public double getHeightViewPlane() {
        return viewPlane.heightViewPlane;
    }

    /**
     * Get the width of the view plane
     * @return Width of the view plane
     */
    public double getWidthViewPlane() {
        return viewPlane.widthViewPlane;
    }

    /**
     * get Location of the camera
     * @return Distance of the view plane
     */
    public double getDistanceFromViewPlane() {
        return viewPlane.distanceFromViewPlane;
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
        return viewPlane.constructRay(nX, nY, j, i);
    }

    /**
     * Renders the image using ray casting.
     *
     * @return  the Camera object after rendering the image
     */
    public Camera renderImage(){
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();
        pixelManager = new PixelManager(ny, nx, printInterval);
        if (threadsCount == 0) {
            for (int i = 0; i < ny; i++) {
                for (int j = 0; j < nx; j++) {
                    castRay(nx, ny, j, i);
                }
            }
        } else if (threadsCount == -1) {
            IntStream.range(0,ny).parallel()
                    .forEach(i -> IntStream.range(0,nx).parallel()
                            .forEach(j -> castRay(nx, ny, j, i)));
        } else {
            var threads = new LinkedList<Thread>();
            while (threadsCount --> 0)
                threads.add(new Thread(() -> {
                    PixelManager.Pixel pixel;
                    while ((pixel = pixelManager.nextPixel()) != null)
                        castRay(nx, ny, pixel.col(), pixel.row());
                }));
            for (var thread : threads)
                thread.start();
            try {
                for (var thread : threads) {
	                thread.join();
                }
            }
            catch (InterruptedException ignore) {}
        }
        return this;
    }

    /**
     * Casts a ray and writes the resulting color to the image.
     *
     * @param  nx  the x coordinate of the pixel
     * @param  ny  the y coordinate of the pixel
     * @param  i   the x coordinate of the image
     * @param  j   the y coordinate of the image
     */
    private void castRay(int nx, int ny, int i, int j) {
        imageWriter.writePixel(j, i, rayTracer.traceRay(constructRay(nx, ny, j, i)));
        pixelManager.pixelDone();
    }

    /**
     * Prints a grid on the imageWriter with a given color.
     *
     * @param  nX     the spacing of the grid lines
     * @param  color  the color of the grid lines
     * @return       the Camera object for method chaining
     */
    public Camera printGrid(int nX, Color color){
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if (i % nX == 0 || j % nX == 0)
                    imageWriter.writePixel(i, j , color);
            }
        }
        return this;
    }

    /**
     * Writes to the image.
     *
     * @param  -   No parameters
     * @return  -   No return value
     */
    public void writeToImage(){
        imageWriter.writeToImage();
    }
    @Override
    public Camera clone() {
        try {
            Camera clonedCamera = (Camera) super.clone();
            // Deep clone mutable fields
            clonedCamera.viewPlane.location = this.viewPlane.location != null ? this.viewPlane.location : null;
            clonedCamera.viewPlane.vTo = this.viewPlane.vTo != null ? this.viewPlane.vTo : null;
            clonedCamera.viewPlane.vRight = this.viewPlane.vRight != null ? this.viewPlane.vRight : null;
            clonedCamera.viewPlane.vUp = this.viewPlane.vUp != null ? this.viewPlane.vUp : null;
            clonedCamera.viewPlane.viewPlaneCenter = this.viewPlane.viewPlaneCenter != null ? this.viewPlane.viewPlaneCenter : null;
            clonedCamera.imageWriter = this.imageWriter != null ? this.imageWriter : null;
            clonedCamera.rayTracer = this.rayTracer != null ? this.rayTracer : null;

            return clonedCamera;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
