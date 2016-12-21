package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import uo.ri.model.exception.BusinessException;
import uo.ri.model.types.AveriaStatus;
import uo.ri.model.types.FacturaStatus;
import alb.util.date.DateUtil;
import alb.util.math.Round;
@Entity
@Table(name="TFACTURAS")
public class Factura {
	@Id @GeneratedValue private Long id;
	private Long numero;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	private double importe;
	private double iva;
	@Enumerated(EnumType.STRING)
	private FacturaStatus status = FacturaStatus.SIN_ABONAR;
	
	@OneToMany(mappedBy="factura") private Set<Averia> averias = new HashSet<Averia>();
	@OneToMany(mappedBy="factura") private Set<Cargo> cargos = new HashSet<Cargo>();
	
	Factura(){}
	
	public Factura(long numero, Date fecha) {
		this.fecha = fecha;
		this.numero = numero;
	}
	
	public Factura(long numero){
		this(numero, DateUtil.today());
	}
	
	public Factura(long numero, Date fecha, List<Averia> averias) throws BusinessException {
		this(numero, fecha);
		for (Averia averia:averias){
			/*if(averia.getStatus() == AveriaStatus.TERMINADA){
				Association.Facturar.link(this, averia);
				averia.setStatus(AveriaStatus.FACTURADA);
			}*/
			addAveria(averia);
		}
	}
	
	public Factura(long numero, List<Averia> averias) throws BusinessException {
		this(numero, DateUtil.today(), averias);
	}

	public Set<Cargo> getCargos() {
		return new HashSet<Cargo>(cargos);
	}

	Set<Cargo> _getCargos() {
		return cargos;
	}

	public Set<Averia> getAverias() {
		return new HashSet<Averia>(averias);
	}
	
	public Set<Averia> _getAverias(){
		return averias;
	}

	public Long getNumero() {
		return numero;
	}

	public Date getFecha() {
		return fecha;
	}

	public double getImporte() {
		return Round.twoCents(importe);
	}

	public double getIva() {
		return iva;
	}

	public FacturaStatus getStatus() {
		return status;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public void setStatus(FacturaStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Factura [numero=" + numero + ", fecha=" + fecha + ", importe=" + importe + ", iva=" + iva + ", status="
				+ status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Factura other = (Factura) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	/**
	 * Añade  la averia a la factura
	 * @param averia
	 * @throws BusinessException 
	 */
	public void addAveria(Averia averia) throws BusinessException {
		// Verificar que la factura está en estado SIN_ABONAR
		// Verificar que La averia está TERMINADA
		// linkar factura y averia
		// marcar la averia como FACTURADA ( averia.markAsInvoiced() )
		// calcular el importe
		if(this.getStatus() == FacturaStatus.SIN_ABONAR){
			if(averia.getStatus() != AveriaStatus.TERMINADA){
				throw new BusinessException("La avería no está terminada");
			} 
			Association.Facturar.link(this, averia);
			averia.markAsInvoiced();
			calcularImporte(averia);
		}
	}

	/**
	 * Calcula el importe de la avería y su IVA, teniendo en cuenta la fecha de 
	 * factura
	 */
	void calcularImporte(Averia averia) {
		// iva = ...
		// importe = ...
		if (DateUtil.isBefore(fecha, DateUtil.fromString("1/7/2012"))) {
			this.setIva(18.0);
		}
		else {
			this.setIva(21.0);
		}
		
		double incremento = averia.getImporte() * (this.getIva()/100.0);
		this.setImporte(this.getImporte() + averia.getImporte() + incremento);
	}

	/**
	 * Elimina una averia de la factura, solo si está SIN_ABONAR y recalcula 
	 * el importe
	 * @param averia
	 */
	public void removeAveria(Averia averia) {
		// verificar que la factura está sin abonar
		// desenlazar factura y averia
		// la averia vuelve al estado FINALIZADA ( averia.markBackToFinished() )
		// volver a calcular el importe
		if(this.getStatus() == FacturaStatus.SIN_ABONAR){
			Association.Facturar.unlink(this, averia);
			averia.markBackToFinished();
			this.setImporte(0.0);
		}
	}
}
