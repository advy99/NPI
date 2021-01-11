
function mensajeDisponible(servicio) {

	var mensaje = '';
	var fecha = new Date();

	var mes = fecha.getMonth();
	var dia = fecha.getDate();
	var dia_semana = fecha.getDay();

	if ( (mes == 0 && dia < 8) || (mes == 11 && dia > 22) ) {
		mensaje = servicio + ' no se encuentra disponible debido a las vacaciones de Navidad.';
	} else if ( mes == 7 ) {
		mensaje = servicio + ' no se encuentra disponible por las vacaciones de verano.';
	} else if ( dia_semana == 6 || dia_semana == 0 ) {
		// en JS el 0 es domingo y el 6 sabado
		mensaje = servicio + ' no se encuentra disponible ya que es fin de semana';
	}

	return mensaje;

}


