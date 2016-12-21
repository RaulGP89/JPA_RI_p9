package uo.ri.model;

public class Association {

	public static class Poseer {

		public static void link(Cliente cliente, Vehiculo vehiculo) {
			vehiculo._setCliente(cliente);
			cliente._getVehiculos().add(vehiculo);
		}

		public static void unlink(Cliente cliente, Vehiculo vehiculo) {
			// se deshace la unión al revés que la unión
			cliente._getVehiculos().remove(vehiculo);
			vehiculo._setCliente(null);
		}
	}

	public static class Clasificar {
		public static void link(TipoVehiculo tipoVehiculo, Vehiculo vehiculo) {
			vehiculo._setTipo(tipoVehiculo);
			tipoVehiculo._getVehiculos().add(vehiculo);
		}

		public static void unlink(TipoVehiculo tipoVehiculo, Vehiculo vehiculo) {
			tipoVehiculo._getVehiculos().remove(vehiculo);
			vehiculo._setTipo(null);
		}
	}

	public static class Pagar {

		public static void link(Cliente cliente, MedioPago metalico) {
			metalico._setCliente(cliente);
			cliente._getMediosPago().add(metalico);
		}

		public static void unlink(Cliente cliente, MedioPago metalico) {
			cliente._getMediosPago().remove(metalico);
			metalico._setCliente(null);
		}
	}

	public static class Averiar {
		public static void link(Vehiculo vehiculo, Averia averia) {
			averia._setVehiculo(vehiculo);
			vehiculo._getAverias().add(averia);
		}

		public static void unlink(Vehiculo vehiculo, Averia averia) {
			vehiculo._getAverias().remove(averia);
			vehiculo.decrementarAveria();
			averia._setVehiculo(null);
		}

	}

	public static class Facturar {
		public static void link(Factura factura, Averia averia) {
			averia._setFactura(factura);
			factura._getAverias().add(averia);
		}

		public static void unlink(Factura factura, Averia averia) {
			factura._getAverias().remove(averia);
			averia._setFactura(null);
		}

	}

	public static class Cargar {
		public static void link(Factura factura, Cargo cargo, MedioPago medioPago) {
			cargo._setFactura(factura);
			cargo._setMedioPago(medioPago);
			factura._getCargos().add(cargo);
			medioPago._getCargos().add(cargo);
		}

		public static void unlink(Cargo cargo) {
			cargo.getMedioPago()._getCargos().remove(cargo);
			cargo.getFactura()._getCargos().remove(cargo);

			cargo._setFactura(null);
			cargo._setMedioPago(null);
		}
	}

	public static class Asignar {

		public static void link(Mecanico mecanico, Averia averia) {
			averia._setMecanico(mecanico);
			mecanico._getAsignadas().add(averia);
		}

		public static void unlink(Mecanico mecanico, Averia averia) {
			mecanico._getAsignadas().remove(averia);
			averia._setMecanico(null);
		}

	}

	public static class Intervenir {

		public static void link(Averia averia, Intervencion intervencion, Mecanico mecanico) {
			// el orden ha de ser este
			intervencion._setAveria(averia);
			intervencion._setMecanico(mecanico);
			averia._getIntervenciones().add(intervencion);
			mecanico._getIntervenciones().add(intervencion);
		}

		public static void unlink(Intervencion intervencion) {
			intervencion.getAveria()._getIntervenciones().remove(intervencion);
			intervencion.getMecanico()._getIntervenciones().remove(intervencion);

			intervencion._setAveria(null);
			intervencion._setMecanico(null);
		}
	}

	public static class Sustituir {

		public static void link(Repuesto repuesto, Sustitucion sustitucion, Intervencion intervencion) {
			sustitucion._setIntervencion(intervencion);
			sustitucion._setRepuesto(repuesto);

			sustitucion.getIntervencion()._getSustituciones().add(sustitucion);
			sustitucion.getRepuesto()._getSustituciones().add(sustitucion);
		}

		public static void unlink(Sustitucion sustitucion) {
			sustitucion.getIntervencion()._getSustituciones().remove(sustitucion);
			sustitucion.getRepuesto()._getSustituciones().remove(sustitucion);

			sustitucion._setIntervencion(null);
			sustitucion._setRepuesto(null);
		}
	}

}
