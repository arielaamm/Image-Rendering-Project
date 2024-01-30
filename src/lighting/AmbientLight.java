package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {

    public AmbientLight(Color iP, Double3 kA) {
        this.intensity = iP.scale(kA);
    }
    public AmbientLight(Color iP, double kA) {
        this.intensity = iP.scale(kA);
    }
    private final Color intensity;
    /**
     * The default constructor
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    public Color getIntensity() {
        return intensity;
    }
}
