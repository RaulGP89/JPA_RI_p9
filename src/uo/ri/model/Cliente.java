package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import javax.persistence.Table;

import uo.ri.model.types.Address;

@Entity
@Table(name="TCLIENTES")
public class Cliente {

	//mal hashcode y tostring de cliente, vehiculo y tipovehiculo
	@Id @GeneratedValue private Long id;
	private String nombre;
	private String apellidos;
	private String dni;
	
	private Address address;

	@OneToMany(mappedBy="cliente") private Set<Vehiculo> vehiculos = new HashSet<Vehiculo>();
	@OneToMany(mappedBy="cliente") private Set<MedioPago> mediosPago = new HashSet<MedioPago>();
	
	Cliente() {}

	public Cliente(String dni) {
		super();
		this.dni = dni;
	}

	public Cliente(String dni, String nombre, String apellidos) {
		this(dni); //llamada en cadena al constructor anterior Cliente(String)
		this.nombre = nombre;
		this.apellidos = apellidos;
	}
	
	public Long getId() {
		return id;
	}

	/**
	 * Los Set no contienen duplicados, de tal forma que un cliente no pueda
	 * tener varios vehículos iguales. Devolver una copia, no el propio set
	 * de vehículos de tal forma que no se rompa la encapsulación (no se pueden
	 * borrar vehículos). 
	 * 
	 * 
	 * @return
	 */
	public Set<Vehiculo> getVehiculos() {
		return new HashSet<Vehiculo> (vehiculos);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", apellidos=" + apellidos
				+ ", dni=" + dni + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Cliente other = (Cliente) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}
	
	public Set<Vehiculo> getVechiculos(){
		return new HashSet<Vehiculo>(vehiculos);
	}
	
	Set<Vehiculo> _getVehiculos() {
		return vehiculos;
	}
	
	public Set<MedioPago> getMediosPago() {
		return new HashSet<MedioPago>(mediosPago);
	}
	
	Set<MedioPago> _getMediosPago() {
		return mediosPago;
	}

}
