function findServices(){

    //**********************************************SISTEMARE CODICE*****************************************************************************
    var elements = document.getElementById("findServices").elements;
    var obj ={};
    for(var i = 0 ; i < (elements.length-1) ; i++){
        var item = elements.item(i);
        obj[item.name] = item.value;
    }

    var raw= JSON.stringify(obj);
    var obj2 = JSON.parse(raw)
    console.log(raw);
    console.log(obj2.address, obj2.radius)
    var myHeaders = new Headers();

    //myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authentication", "Bearer "+localStorage.getItem("token"));
    var requestOptions = {
        method: 'GET',
        headers: myHeaders,
        redirect: 'follow'
    };

    fetch("http://localhost:8080/rest/api/services?address="+obj2.address+"&radius="+2000, requestOptions)
        .then(response => response.json()
            .then(jsonBody => ({
                status: response.status,
                jsonBody
            })))
        .then(result => setServiceInformation(result.jsonBody, result.status))

}

function setServiceInformation(json, status){
    if(status == 200 || status == 201){
        console.log(json)
        createCards(json, localStorage.getItem("role"))
    }
}


function loadAddress() {
    if(localStorage.getItem('role').toString() === "P" ){
        //console.log(localStorage.getItem('address'))
        document.getElementById("search").value = localStorage.getItem("address").toString();
    }
}

