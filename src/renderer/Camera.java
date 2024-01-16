package renderer;

import primitives.Point;
import primitives.Vector;

public class Camera implements Cloneable{
    private Point location = new Point(0,0,0);
    private Vector Vto;
    private Vector Vright;
    private Vector Vup;
    private double heightFromViewPlane = 0.0;
    private double widthFromViewPlane = 0.0;
    private double distanceFromViewPlane = 0.0;

    private Camera(){}

    public double getHeightFromViewPlane() {
        return heightFromViewPlane;
    }

    public double getWidthFromViewPlane() {
        return widthFromViewPlane;
    }

    public double getDistanceFromViewPlane() {
        return distanceFromViewPlane;
    }

}
