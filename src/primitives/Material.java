package primitives;

/**
 * Material class represents the material of a shape in the scene.
 */
public class Material {
	/**
	 * kD is the diffuse coefficient of the material.
	 */
	public Double3 kD = Double3.ZERO;
	/**
	 * kS is the specular coefficient of the material.
	 */
	public Double3 kS = Double3.ZERO;
	/**
	 * kR is the reflective coefficient of the material.
	 */
	public Double3 kR = Double3.ZERO;
	/**
	 * kT is the transparent coefficient of the material.
	 */
	public Double3 kT = Double3.ZERO;
	/**
	 *  Glossiness factor
	 */
	public double kG = 0;
	/**
	 * Blurriness factor
	 */
	public double kB = 0;
	/**
	 * nShininess is the shininess of the material.
	 */
	public int nShininess = 1;

	/**
	 * Sets the kD value.
	 *
	 * @param  kD  the new kD value
	 * @return     the updated Material object
	 */
	public Material setKd(Double3 kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * Sets the kD value.
	 *
	 * @param  kD  the new kD value
	 * @return     the updated Material object
	 */
	public Material setKd(double kD) {
		this.kD = new Double3(kD);
		return this;
	}
	/**
	 * Set the kG value.
	 *
	 * @param  kG  the new kG value
	 * @return     the updated Material object
	 */
	public Material setKg(double kG) {
		this.kG = kG;
		return  this;
	}
	/**
	 * Sets the value of kB and returns the updated Material object.
	 *
	 * @param  kB  the new value for kB
	 * @return     the updated Material object
	 */
	public Material setKb(double kB) {
		this.kB = kB;
		return this;
	}

	/**
	 * Sets the kR value.
	 *
	 * @param  kR  the new kR value
	 * @return     the updated Material object
	 */
	public Material setKr(Double3 kR) {
		this.kR = kR;
		return this;
	}

	/**
	 * Sets the kR value.
	 *
	 * @param  kR  the new kR value
	 * @return     the updated Material object
	 */
	public Material setKr(double kR) {
		this.kR = new Double3(kR);
		return this;
	}
	/**
	 * Sets the kT value.
	 *
	 * @param  kT  the new kD value
	 * @return     the updated Material object
	 */
	public Material setKt(Double3 kT) {
		this.kT = kT;
		return this;
	}

	/**
	 * Sets the kT value.
	 *
	 * @param  kT  the new kD value
	 * @return     the updated Material object
	 */
	public Material setKt(double kT) {
		this.kT = new Double3(kT);
		return this;
	}
	/**
	 * Sets the kS value of the material.
	 *
	 * @param  kS  the new kS value to set
	 * @return     the updated Material object
	 */
	public Material setKs(Double3 kS) {
		this.kS = kS;
		return this;
	}
	/**
	 * Sets the kS value of the material.
	 *
	 * @param  kS  the new kS value to set
	 * @return     the updated Material object
	 */
	public Material setKs(double kS) {
		this.kS = new Double3(kS);
		return this;
	}

	/**
	 * Sets the shininess of the material.
	 *
	 * @param  nShininess  the new shininess value
	 * @return             the updated Material object
	 */
	public Material setnShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}
	/**
	 * Sets the shininess of the material.
	 *
	 * @param  nShininess  the new shininess value
	 * @return             the updated Material object
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}
}
