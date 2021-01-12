
function mensajeDisponible(servicio) {

	// el mensaje esta vacio inicialmente
	var mensaje = '';
	var fecha = new Date();

	var mes = fecha.getMonth();
	var dia = fecha.getDate();
	var dia_semana = fecha.getDay();

	// si estamos en vacaciones de navidad, entre el 23 de diciembre y el 7 de enero
	// no esta disponible
	if ( (mes == 0 && dia < 8) || (mes == 11 && dia > 22) ) {
		mensaje = servicio + ' no se encuentra disponible debido a las vacaciones de Navidad.';
	// si estamos en agosto tambien esta cerrado
	} else if ( mes == 7 ) {
		mensaje = servicio + ' no se encuentra disponible por las vacaciones de verano.';
	// si es fin de semana no está disponible
	} else if ( dia_semana == 6 || dia_semana == 0 ) {
		// en JS el 0 es domingo y el 6 sabado
		mensaje = servicio + ' no se encuentra disponible ya que es fin de semana';
	}

	return mensaje;

}


