package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="TMEDIOSPAGO")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)//Joined nos hace el esquema de base de datos con herencia, Table_per_class nos crea una tabala por cada metodo de pago con campos repetidos
@DiscriminatorColumn(name="DISC")//Nombra a la columna discriminatoria (la que nos diferencia los tipos)
public abstract class MedioPago {
	@Id @GeneratedValue private Long id;
	protected double acumulado = 0.0;
	
	@ManyToOne private Cliente cliente;
	@OneToMany(mappedBy="medioPago") 
	private Set<Cargo> cargos = new HashSet<Cargo>();
	
	//NO ES NECESARIO EN LA PADRE EL CONSTRUCTOR SIN PAR�METROS MedioPago() {}
	
	public Cliente getCliente() {
		return cliente;
	}

	void _setCliente(Cliente cliente) {
		this.cliente = cliente;
	}	
	
	public Set<Cargo> getCargos() {
		return new HashSet<Cargo>(cargos);
	}

	Set<Cargo> _getCargos() {
		return cargos;
	}

	public double getAcumulado() {
		return acumulado;
	}

	public void setAcumulado(double acumulado) {
		this.acumulado = acumulado;
	}

	@Override
	public String toString() {
		return "MedioPago [acumulado=" + acumulado + ", cliente=" + cliente + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		MedioPago other = (MedioPago) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		return true;
	}
	
}
