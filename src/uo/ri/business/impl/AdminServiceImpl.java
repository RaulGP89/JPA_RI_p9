package uo.ri.business.impl;

import java.util.List;

import uo.ri.business.AdminService;
import uo.ri.business.impl.admin.AddMechanic;
import uo.ri.business.impl.admin.DeleteMechanic;
import uo.ri.business.impl.admin.FindAllMechanics;
import uo.ri.business.impl.admin.FindMechanicById;
import uo.ri.business.impl.admin.UpdateMechanic;
import uo.ri.model.Mecanico;
import uo.ri.model.exception.BusinessException;

public class AdminServiceImpl implements AdminService {

	@Override
	public void newMechanic(Mecanico mecanico) throws BusinessException {
		new AddMechanic( mecanico ).execute();
	}

	@Override
	public void updateMechanic(Mecanico mecanico) throws BusinessException {
		new UpdateMechanic( mecanico ).execute();
	}

	@Override
	public void deleteMechanic(Long idMecanico) throws BusinessException {
		new DeleteMechanic(idMecanico).execute();
	}

	@Override
	public List<Mecanico> findAllMechanics() throws BusinessException {
		return (List<Mecanico>) new FindAllMechanics().execute();
	}

	@Override
	public Mecanico findMechanicById(Long id) throws BusinessException {
		return (Mecanico) new FindMechanicById(id).execute();
	}

}
