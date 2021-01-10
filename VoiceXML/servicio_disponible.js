
function mensajeDisponible(servicio) {

	var mensaje = '';
	var fecha = new Date();

	var mes = fecha.getMonth();
	var dia = fecha.getDate();

	if ( (mes == 0 && dia < 8) || (mes == 11 && dia > 22) ) {
		mensaje = servicio + ' no se encuentra disponible debido a las vacaciones de Navidad.';
	} else if ( mes == 7 ) {
		mensaje = servicio + ' no se encuentra disponible por las vacaciones de verano.';
	}

	return mensaje;

}


