package uo.ri.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TMetalico")
public class Metalico extends MedioPago {
	
	Metalico() {}
	public Metalico(Cliente cliente){
		Association.Pagar.link(cliente, this);
	}
	
}
