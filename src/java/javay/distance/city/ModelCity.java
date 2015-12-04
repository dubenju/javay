package javay.distance.city;

import javay.util.Strings;

public class ModelCity {
	/** 城市 */
	private String name;
	/** 经度 */
	private double longitude;
	/** 纬度 */
	private double atitude;
	/** 海拔 */
	private double altitude;

	public ModelCity(String name, double longitude, double atitude, double altitude) {
		this.name = name;
		this.longitude = longitude;
		this.atitude = atitude;
		this.altitude = altitude;
	}

	/**
	 * 城市
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 城市
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 经度
	 * @return longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude 经度
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * 纬度
	 * @return atitude
	 */
	public double getAtitude() {
		return atitude;
	}

	/**
	 * @param atitude 纬度
	 */
	public void setAtitude(double atitude) {
		this.atitude = atitude;
	}

	/**
	 * 海拔
	 * @return altitude
	 */
	public double getAltitude() {
		return altitude;
	}

	/**
	 * @param altitude 海拔
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(Strings.format(this.name, 4));
		buf.append("(");
		buf.append(this.longitude);
		buf.append(",");
		buf.append(this.atitude);
		buf.append(")海拔:");
		buf.append(this.altitude);
		return buf.toString();
	}

}
