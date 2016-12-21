package uo.ri.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import uo.ri.model.types.SustitucionKey;

@Entity
@IdClass(SustitucionKey.class)
@Table(name="TSUSTITUCIONES")
public class Sustitucion {

	@Id @ManyToOne private Repuesto repuesto;
	
	
	@Id @ManyToOne 
	@JoinColumns({
			@JoinColumn(name="INTERVENCION_AVERIA_ID", referencedColumnName="AVERIA_ID"),
			@JoinColumn(name="INTERVENCION_MECANICO_ID", referencedColumnName="MECANICO_ID")
	})
	private Intervencion intervencion;
	private int cantidad;

	Sustitucion(){}
	
	public Sustitucion(Repuesto repuesto, Intervencion intervencion) {
		super();
		this.repuesto = repuesto;
		this.intervencion = intervencion;
		Association.Sustituir.link(repuesto, this, intervencion);
	}

	public Repuesto getRepuesto() {
		return repuesto;
	}

	void _setRepuesto(Repuesto repuesto) {
		this.repuesto = repuesto;
	}

	public Intervencion getIntervencion() {
		return intervencion;
	}

	void _setIntervencion(Intervencion intervencion) {
		this.intervencion = intervencion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getImporte() {
		return getCantidad() * getRepuesto().getPrecio();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intervencion == null) ? 0 : intervencion.hashCode());
		result = prime * result + ((repuesto == null) ? 0 : repuesto.hashCode());
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
		Sustitucion other = (Sustitucion) obj;
		if (intervencion == null) {
			if (other.intervencion != null)
				return false;
		} else if (!intervencion.equals(other.intervencion))
			return false;
		if (repuesto == null) {
			if (other.repuesto != null)
				return false;
		} else if (!repuesto.equals(other.repuesto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sustitucion [repuesto=" + repuesto + ", intervencion=" + intervencion + ", cantidad=" + cantidad + "]";
	}

}
