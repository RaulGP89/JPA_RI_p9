package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import uo.ri.model.types.AveriaStatus;

@Entity
@Table(name="TAVERIAS")
public class Averia {

	@Id @GeneratedValue private Long id;
	private String descripcion;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	private double importe = 0.0;
	@Enumerated(EnumType.STRING)
	private AveriaStatus status = AveriaStatus.ABIERTA;
	
	@ManyToOne private Vehiculo vehiculo;
	@ManyToOne private Mecanico mecanico;
	@ManyToOne private Factura factura;
	
	@OneToMany(mappedBy="averia") private Set<Intervencion> intervenciones = new HashSet<Intervencion>();
	
	Averia() {}
	
	/**
	 * Los constructores reciben la identidad
	 * @param fecha
	 * @param vehiculo
	 */
	public Averia(Date fecha, Vehiculo vehiculo) {
		super();
		this.fecha = fecha;
		this.vehiculo = vehiculo;
		Association.Averiar.link(vehiculo, this);
		vehiculo.incrementarAveria();
	}
	
	public Averia(Vehiculo vehiculo, String descripcion){
		this(new Date(), vehiculo);
		this.descripcion = descripcion;
	}
	
	public Set<Intervencion> getIntervenciones() {
		return new HashSet<Intervencion>(intervenciones);
	}

	Set<Intervencion> _getIntervenciones() {
		return intervenciones;
	}
	
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	void _setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	
	public Factura getFactura(){
		return factura;
	}
	
	void _setFactura(Factura factura){
		this.factura = factura;
	}
	
	public Mecanico getMecanico(){
		return mecanico;
	}
	
	void _setMecanico(Mecanico mecanico){
		this.mecanico = mecanico;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public double getImporte() {
		return importe;
	}

	public AveriaStatus getStatus() {
		return status;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public void setStatus(AveriaStatus status) {
		this.status = status;
	}

	public void setIntervenciones(Set<Intervencion> intervenciones) {
		this.intervenciones = intervenciones;
	}

	@Override
	public String toString() {
		return "Averia [descripcion=" + descripcion + ", fecha=" + fecha + ", importe=" + importe + ", status=" + status
				+ ", vehiculo=" + vehiculo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((vehiculo == null) ? 0 : vehiculo.hashCode());
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
		Averia other = (Averia) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (vehiculo == null) {
			if (other.vehiculo != null)
				return false;
		} else if (!vehiculo.equals(other.vehiculo))
			return false;
		return true;
	}

	/**
	 * Asigna la averia al mecanico
	 * @param mecanico
	 */
	public void assignTo(Mecanico mecanico) {	
		// Solo se puede asignar una averia que está ABIERTA
		// linkado de averia y mecanico
		// la averia pasa a ASIGNADA
		if(getStatus() == AveriaStatus.ABIERTA){
			Association.Asignar.link(mecanico, this);
			setStatus(AveriaStatus.ASIGNADA);
		}
	}

	/**
	 * El mecánico da por finalizada esta avería, entonces se calcula el 
	 * importe
	 * 
	 */
	public void markAsFinished() {
		// Se verifica que está en estado ASIGNADA
		// se calcula el importe
		// se desvincula mecanico y averia
		// el status cambia a TERMINADA
		if(this.getStatus() == AveriaStatus.ASIGNADA){
			double importe = 0.0;
			for(Intervencion intervencion:intervenciones){
				importe += intervencion.getImporte();
			}
			this.setImporte(importe);
			Association.Asignar.unlink(mecanico, this);
			this.setStatus(AveriaStatus.TERMINADA);
		}
	}

	/**
	 * Una averia en estado terminada se puede asignar a otro (el primero no ha
	 * podido terminar la reparación), pero debe ser pasada a abierta primero
	 */
	public void reopen() {
		// Solo se puede reabrir una averia que est� TERMINADA
		if(this.getStatus() == AveriaStatus.TERMINADA){
			// la averia pasa a ABIERTA
			this.setStatus(AveriaStatus.ABIERTA);
		}	
	}

	/**
	 * Una avería ya facturada se elimina de la factura
	 */
	public void markBackToFinished() {
		// verificar que la averia está FACTURADA
		// cambiar status a TERMINADA
		if(this.getStatus() == AveriaStatus.FACTURADA)
			this.setStatus(AveriaStatus.TERMINADA);
	}
	
	public void markAsInvoiced(){
		if(this.getStatus() == AveriaStatus.TERMINADA)
			this.setStatus(AveriaStatus.FACTURADA);
	}

	public void desassign() {
		// desasigna la aver�a de un mecanico
		Association.Asignar.unlink(mecanico, this);
		this.setStatus(AveriaStatus.ABIERTA);
	}

	public Long getId() {
		return id;
	}
}
