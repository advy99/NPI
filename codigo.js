export default (request, response) => {
    let bodyString = request.body;
    var entradaDialogFlow = JSON.parse(bodyString);
    console.log("Entrada DialogFlow: "+entradaDialogFlow);
    var biblioteca=entradaDialogFlow['queryResult']['parameters'].biblioteca_centro;
    var mensaje;

    if ( biblioteca !== "") {
        if ( biblioteca === "Facultad de ciencias") {
            mensaje = "La biblioteca de ciencias está en el edificio principal de la facultad de ciencias";
        } else if ( biblioteca === "Escuela Técnica Superior de Ingenierías Informática y de Telecomunicación") {
            mensaje = "La";
        }

    } else {
        mensaje = "otro intent";
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
