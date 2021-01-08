function fs(){

    //**********************************************SISTEMARE CODICE*****************************************************************************
    var elements = document.getElementById("findServices").elements;
    var obj ={};
    for(var i = 0 ; i < (elements.length-1) ; i++){
        var item = elements.item(i);
        obj[item.name] = item.value;
    }

    var raw= JSON.stringify(obj);
    console.log(raw);
    var myHeaders = new Headers();

    //myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authentication", "Bearer "+localStorage.getItem("token"));
    var requestOptions = {
        method: 'GET',
        headers: myHeaders,
        //body:raw,
        redirect: 'follow'
    };

    fetch("http://localhost:8080/rest/api/services?address="+raw.address+"&radius="+raw.radius, requestOptions)
        .then(response => response.status) //Indirizzamentro alla pagina dei servizi
        .then(result => console.log(result))
        .catch(error => console.log('error', error));

}

