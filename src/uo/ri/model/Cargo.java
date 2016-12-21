package uo.ri.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import uo.ri.model.exception.BusinessException;
import uo.ri.model.types.CargoKey;
import uo.ri.model.types.FacturaStatus;
@Entity
@Table(name="TCARGOS")
@IdClass(CargoKey.class)
public class Cargo {
	
	@Id @ManyToOne private Factura factura;
	@Id @ManyToOne private MedioPago medioPago;
	private double importe = 0.0;
	
	Cargo(){}
	
	public Cargo(Factura factura, MedioPago medioPago) {
		super();
		this.factura = factura;
		this.medioPago = medioPago;
	}
	
	public Cargo(Factura factura, MedioPago medioPago, double importe) throws BusinessException {
		this(factura, medioPago);
		// incrementar el importe en el acumulado del medio de pago
		medioPago.setAcumulado(medioPago.getAcumulado() + importe);
		// guardar el importe
		this.importe = importe;
		// enlazar (link) factura, este cargo y medioDePago
		Association.Cargar.link(factura, this, medioPago);		
	}
	
	@Override
	public String toString() {
		return "Cargo [factura=" + factura + ", medioPago=" + medioPago + ", importe=" + importe + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((factura == null) ? 0 : factura.hashCode());
		result = prime * result + ((medioPago == null) ? 0 : medioPago.hashCode());
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
		Cargo other = (Cargo) obj;
		if (factura == null) {
			if (other.factura != null)
				return false;
		} else if (!factura.equals(other.factura))
			return false;
		if (medioPago == null) {
			if (other.medioPago != null)
				return false;
		} else if (!medioPago.equals(other.medioPago))
			return false;
		return true;
	}

	public Factura getFactura() {
		return factura;
	}

	public MedioPago getMedioPago() {
		return medioPago;
	}

	public double getImporte() {
		return importe;
	}

	void _setFactura(Factura factura) {
		this.factura = factura;
	}

	void _setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}
	
	
	
	/**
	 * Anula (retrocede) este cargo de la factura y el medio de pago
	 * Solo se puede hacer si la factura no está abonada
	 * Decrementar el acumulado del medio de pago
	 * Desenlazar el cargo de la factura y el medio de pago 
	 * @throws BusinessException
	 */
	public void rewind() throws BusinessException {
		// verificar que la factura no esta ABONADA
		// decrementar acumulado en medio de pago
		// desenlazar factura, cargo y medio de pago
		if(!this.factura.equals(FacturaStatus.ABONADA)){
			this.getMedioPago().setAcumulado(this.getMedioPago().getAcumulado() - this.getImporte());
			Association.Cargar.unlink(this);
		}
	}
	

}
