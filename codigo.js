export default (request, response) => {
    let bodyString = request.body;
    var entradaDialogFlow = JSON.parse(bodyString);
    console.log("Entrada DialogFlow: "+entradaDialogFlow);
    var menu=entradaDialogFlow['queryResult']['parameters'].menus;
    var centros=entradaDialogFlow['queryResult']['parameters'].centro;
    var lugar=entradaDialogFlow['queryResult']['parameters'].lugar_intent;
    var informacion=entradaDialogFlow['queryResult']['parameters'].informacion;
    var informacion=entradaDialogFlow['queryResult']['parameters'].preguntagrado;
    var mensaje;

    if (lugar === "comedores_localizacion"){

        if (centros === "Facultad de Bellas Artes"){
            mensaje = "La Facultad de Bellas Artes no tiene comedor universitario pero puedes ir al comedor de la ETSIIT, que es el más cercano, mediante el siguiente link https://goo.gl/maps/LuukFQGWFVr4NsRr5"
        }
        else if (centros === "Facultad de Ciencias de la Educación"){
            mensaje = "Para ir al comedor universitario de Ciencias de la Educación usa el siguiente enlace: https://goo.gl/maps/ozjVfBUWjnxSv7HW8"
        }
        else if (centros === "Escuela Técnica Superior de Ingeniería de Caminos, Canales y Puertos"){
            mensaje = "El comedor universitario de la ETSICCP es el de Fuentenueva, aquí tiene el enlace para llegar: https://goo.gl/maps/HwMmwWBQYt2MMXM1A"
        }
        else if (centros === "Facultad de ciencias"){
            mensaje = "El comedor universitario de la Facultad de Ciencias es el de Fuentenueva, aquí tiene el enlace para llegar: https://goo.gl/maps/HwMmwWBQYt2MMXM1A"
        }
        else if (centros === "Escuela Técnica Superior de Ingenierías Informática y de Telecomunicación"){
            mensaje = "Puede ir al comedor universitario de la ETSIIT mediante el siguiente link https://goo.gl/maps/LuukFQGWFVr4NsRr5"
        }

    }
    else if (lugar === "comedores_sin_centro"){
        mensaje = "Veo que no me has proporcionado el centro, te recomiendo que vayas a los comedores universitarios de Fuentenueva mediante el siguiente link: https://goo.gl/maps/HwMmwWBQYt2MMXM1A"
    }
    else if (lugar === "comedores_menu"){

        //Lo ideal seria hacer scraping, pero PubNub no ofrece la posibilidad
        //de usar los modulos que necesitamos

        //Obtenemos el dia de la semana en el que estamos
        var d = new Date();
        var n = d.getDay()

        var dia;

        if (n === 1){
            dia = "Lunes"
        }
        else if (n == 2){
            dia = "Martes"
        }
        else if (n == 3){
            dia = "Miércoles"
        }
        else if (n == 4){
            dia = "Jueves"
        }
        else{
            dia = "Viernes"
        }

        mensaje = "Hoy es " + dia + " y el menu correspondiente a " + menu + " es:" + "\n"

        if ( menu === "Almuerzo") {
            mensaje = mensaje + "Primero: Espaguetis a la napolitana, Segundo: Aguja encebollada, Acompañamiento: Menestra, Postre: Melocotón\n"
        }
        else if ( menu === "Cena" ){
            mensaje = mensaje + "Primero: Ensalada de arroz con salmón ahumado, pepino y vinagreta de soja y sésamo, Segundo: Jamón en salsa cazadora, Acompañamiento: Judías, zanahorias y patatas, Postre: Natillas\n"
        }
        else if ( menu === "Ovolactovegetariano"){
            mensaje = mensaje + "Primero: Estofado de patatas y acelgas, Segundo: Rollitos de primavera, Acompañamiento: Ensalada, Postre: Naranja\n"

        }
        else if ( menu === "Vegano" ){
            mensaje = mensaje + "Primero: Arroz con variado de verduras, Segundo: Salteado de tofu, calabaza y guisantes, Acompañamiento: Ensalada Richelieu, Postre: Pera\n"

        }
        else if ( menu === "T-celiaco" ){
            mensaje = mensaje + "Primero: Arroz a la cubana, Segundo: Salteado de ternera, Acompañamiento: Patatas rellenas, Postre: Cóctel de frutas\n"
        }
    }
    else if (lugar === "centro_info"){
        if (centros === "Escuela Técnica Superior de Ingenierías Informática y de Telecomunicación"){
            if (informacion === "Lugar"){
                mensaje = "La ETSIIT esta en Calle Periodista Daniel Saucedo Aranda, s/n, 18014 Granada (http://etsiit.ugr.es/)"
            } else if (informacion === "Contacto"){
                mensaje = "Tfno: +34-242802-240632-241000-240633-242885-242820 (http://etsiit.ugr.es/pages/escuela)"
            } else if (informacion === "Decano"){
                mensaje = "El director de la ETSIIT es D. Pedro García Teodoro (http://etsiit.ugr.es/pages/escuela/organos_gobierno)"
            } else if (informacion == "Delegacion"){
                mensaje = "La delegación de centro de la ETSIIT es la Delegación de Estudiantes de Ingenierías Informáticas y de Telecomunicación o DEIIT (https://deiit.ugr.es/)"
            }
        } else if (centros === "Facultad de Bellas Artes"){
            if (informacion === "Lugar"){
                mensaje = "La Facultad de Bellas Artes esta en Calle Periodista Eugenio Selles, s/n, 18014 Granada (https://bellasartes.ugr.es/)"
            } else if (informacion === "Contacto"){
                mensaje = "Telf. 958 243 819 (https://bellasartes.ugr.es/pages/localizacion-y-contacto)"
            } else if (informacion === "Decano"){
                mensaje = "El decano de la Faculta de Bellas Artes es D. Francisco José Sánchez Moltanbán (https://bellasartes.ugr.es/pages/directorio/decanato)"
            } else if (informacion == "Delegacion"){
                mensaje = "La delegación de centro de la Facultad de Bellas Artes es la Delegación de Estudiantes de la Facultad de Bellas Artes"
            }
        } else if (centros === "Facultad de ciencias"){
            if (informacion === "Lugar"){
                mensaje = "La Facultad de Ciencias esta en Avenida de Fuente Nueva, s/n, 18071 Granada (https://fciencias.ugr.es)"
            } else if (informacion === "Contacto"){
                mensaje = "Telf: 958 24 06 98/99 (https://fciencias.ugr.es/facultad#contacto)"
            } else if (informacion === "Decano"){
                mensaje = "La decana de la Facultad de Ciencias es D.a María del Carmen Carrión Pérez (https://fciencias.ugr.es/facultad/equipo-de-gobierno)"
            } else if (informacion == "Delegacion"){
                mensaje = "La delegación de centro de la Facultad de Ciencias es la Delegación de la Facultad de Ciencias o DEFC (https://defc.ugr.es/)"
            }
        } else if (centros === "Escuela Técnica Superior de Ingeniería de Caminos, Canales y Puertos"){
            if (informacion === "Lugar"){
                mensaje = "La ETSICCP esta en Calle Dr. Severo Ochoa, s/n, 18001 Granada (https://etsiccp.ugr.es/)"
            } else if (informacion === "Contacto"){
                mensaje = "Teléfono: 958 24 41 46 "
            } else if (informacion === "Decano"){
                mensaje = "La directora de la ETSICCP es D.a Montserrat Zamorano Toro (https://etsiccp.ugr.es/la-escuela/organizacion)"
            } else if (informacion == "Delegacion"){
                mensaje = "La delegación de centro de la ETSICCP es la Delegación de Estudiantes de la ETSICCP o DEICCP"
            }
        } else if (centros === "Facultad de Ciencias de la Educación"){
            if (informacion === "Lugar"){
                mensaje = "La Facultad de Ciencias de la Educación esta en Prof. Vicente Callao - Fte Ciencias Educación, 18011, 18011, Granada (https://educacion.ugr.es/)"
            } else if (informacion === "Contacto"){
                mensaje = "Teléfono: 958 24 39 98"
            } else if (informacion === "Decano"){
                mensaje = "EL decano de la Facultad de Ciencias de la Educación es D. Javier Villoria Prieto (https://educacion.ugr.es/pages/facultad/organos_unipersonales)"
            } else if (informacion == "Delegacion"){
                mensaje = "La delegación de centro de la Facultad de Ciencias de la Educación es la Delegación Estudiantes Facultad Educación"
            }
        }
    }
    else{
        mensaje = "Otro"
    }


    let respuesta = mensaje;

    // Set the status code - by default it would return 200
    response.status = 200;

    // Set the headers the way you like
    response.headers['X-Custom-Header'] = 'CustomHeaderValue';
    return request.json().then((body) => {
    return response.send({ fulfillmentText: respuesta });
    //return response.send({speech: "What's up cool developer :)"});

}).catch((err) => {
return response.send("Malformed JSON body.");
});
};

