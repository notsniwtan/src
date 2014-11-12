package edu.ntu.androidsecure;


public class Method {
	private String name;
	private double centroid_X;
	private double centroid_Y;
	private double centroid_Z;
	private double centroid_weight;
	private double ext_centroid_X;
	private double ext_centroid_Y;
	private double ext_centroid_Z;
	private double ext_centroid_weight;
	
	public Method(String name, double centroid_X, double centroid_Y, double centroid_Z, double centroid_weight, double ext_centroid_X, double ext_centroid_Y, double ext_centroid_Z, double ext_centroid_weight){
		this.name = name;
		this.centroid_X = centroid_X;
		this.centroid_Y = centroid_Y;
		this.centroid_Y = centroid_Y;
		this.centroid_weight = centroid_weight;
		this.ext_centroid_X = ext_centroid_X;
		this.ext_centroid_Y = ext_centroid_Y;
		this.ext_centroid_Z = ext_centroid_Z;
		this.ext_centroid_weight = ext_centroid_weight;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public double getCentroid_X() {
		return centroid_X;
	}

	public void setCentroid_X(double centroid_X) {
		this.centroid_X = centroid_X;
	}

	public double getCentroid_Y() {
		return centroid_Y;
	}

	public void setCentroid_Y(double centroid_Y) {
		this.centroid_Y = centroid_Y;
	}

	public double getCentroid_Z() {
		return centroid_Z;
	}

	public void setCentroid_Z(double centroid_Z) {
		this.centroid_Z = centroid_Z;
	}

	public double getExt_centroid_X() {
		return ext_centroid_X;
	}

	public void setExt_centroid_X(double ext_centroid_X) {
		this.ext_centroid_X = ext_centroid_X;
	}

	public double getExt_centroid_Y() {
		return ext_centroid_Y;
	}

	public void setExt_centroid_Y(double ext_centroid_Y) {
		this.ext_centroid_Y = ext_centroid_Y;
	}

	public double getExt_centroid_Z() {
		return ext_centroid_Z;
	}

	public void setExt_centroid_Z(double ext_centroid_Z) {
		this.ext_centroid_Z = ext_centroid_Z;
	}

	public double getCentroid_weight() {
		return centroid_weight;
	}

	public void setCentroid_weight(double centroid_weight) {
		this.centroid_weight = centroid_weight;
	}

	public double getExt_centroid_weight() {
		return ext_centroid_weight;
	}

	public void setExt_centroid_weight(double ext_centroid_weight) {
		this.ext_centroid_weight = ext_centroid_weight;
	}
	

}
