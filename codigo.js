export default (request, response) => {
    let bodyString = request.body;
    var entradaDialogFlow = JSON.parse(bodyString);
    console.log("Entrada DialogFlow: "+entradaDialogFlow);
    var menu=entradaDialogFlow['queryResult']['parameters'].menus;
    var mensaje;
    
    
    if ( menu !== "" ){
        
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

