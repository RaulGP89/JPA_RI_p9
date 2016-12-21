package uo.ri.model.types;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String stree;
	private String city;
	private String zipcode;
	
	Address() {}
	
	public Address(String stree, String city, String zipcode) {
		super();
		this.stree = stree;
		this.city = city;
		this.zipcode = zipcode;
	}
	public String getStree() {
		return stree;
	}
	public String getCity() {
		return city;
	}
	public String getZipcode() {
		return zipcode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((stree == null) ? 0 : stree.hashCode());
		result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (stree == null) {
			if (other.stree != null)
				return false;
		} else if (!stree.equals(other.stree))
			return false;
		if (zipcode == null) {
			if (other.zipcode != null)
				return false;
		} else if (!zipcode.equals(other.zipcode))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Address [stree=" + stree + ", city=" + city + ", zipcode="
				+ zipcode + "]";
	}
	
	
}
