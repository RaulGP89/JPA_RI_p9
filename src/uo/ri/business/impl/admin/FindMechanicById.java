package uo.ri.business.impl.admin;

import uo.ri.model.Mecanico;
import uo.ri.model.exception.BusinessException;
import uo.ri.persistence.util.Jpa;

public class FindMechanicById {

	private Long id;

	public FindMechanicById(Long id) {
		this.id = id;
	}

	public Object execute() throws BusinessException {
		return Jpa.getManager().find(Mecanico.class, id);
	}

}
