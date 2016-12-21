package uo.ri.business.impl.admin;

import uo.ri.business.impl.Command;
import uo.ri.model.Mecanico;
import uo.ri.model.exception.BusinessException;
import uo.ri.persistence.util.Jpa;

public class DeleteMechanic implements Command{

	private Long idMecanico;

	public DeleteMechanic(Long idMecanico) {
		this.idMecanico = idMecanico;
	}

	public Object execute() throws BusinessException {

		Mecanico m = Jpa.getManager().find(Mecanico.class, idMecanico);
		assertNoNull(m);
		assertSinIntervenciones(m);
		assertSinAsignadas(m);
		Jpa.getManager().remove(m);
		return m;

	}

	private void assertNoNull(Mecanico m) throws BusinessException {
		if (m == null) {
			throw new BusinessException("No existe el mecánico");
		}
	}

	private void assertSinIntervenciones(Mecanico m) throws BusinessException {
		if (m.getIntervenciones().size() > 0) {
			throw new BusinessException("No se puede borar porque tiene intervenciones.");
		}
	}

	private void assertSinAsignadas(Mecanico m) throws BusinessException {
		if (m.getAsignadas().size() > 0) {
			throw new BusinessException("No se puede borar porque tiene averías asignadas.");
		}
	}

}
