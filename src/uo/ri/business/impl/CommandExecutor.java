package uo.ri.business.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import uo.ri.model.exception.BusinessException;
import uo.ri.persistence.util.Jpa;

public class CommandExecutor {

	public Object execute(Command cmd) throws BusinessException {
		EntityManager mapper = Jpa.createEntityManager();
		EntityTransaction trs = mapper.getTransaction();
		trs.begin();

		try {
			Object res = cmd.execute();
			trs.commit();
			return res;
		} catch (PersistenceException be) {
			if (trs.isActive())
				trs.rollback();
			throw be;
		} catch (BusinessException be) {
			if (trs.isActive())
				trs.rollback();
			throw be;
		} finally {
			if (mapper.isOpen())
				mapper.close();
		}
	}

}
