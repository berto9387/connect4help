window.addEventListener("load", fs, false);

function fs(){


    var myHeaders = new Headers();
    //myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", "Bearer "+localStorage.getItem("token").toString());
    var requestOptions = {
        method: 'GET',
        headers: myHeaders,
        redirect: 'follow'
    };
    var url = "http://localhost:8080/rest/api/users/"+localStorage.getItem("id")+"/services";



    fetch(url, requestOptions)
        .then(response => response.json()) //Indirizzamentro alla pagina dei servizi
        .then(result => createCards(result,localStorage.getItem("role")))
        .catch(error => console.log('error', error));



    function createCards(result,role) {
        for (var i=0; i<result.length;i++){
            createCard(result[i],role);
        }

    }

    function createCard(resultElement,role) {
        var body = document.getElementById("container");
            var container = document.createElement("div");
            container.setAttribute("class","container_card");
                var serviceBackground = document.createElement("div");
                serviceBackground.setAttribute("class","serviceBackground");
                    var gradients = document.createElement("div");
                    gradients.setAttribute("class","gradients");
                        var gradient = document.createElement("div");
                        gradient.setAttribute("class","gradient second");
                        gradient.setAttribute("color","blue");
                    gradients.appendChild(gradient);
                serviceBackground.appendChild(gradients)
                    var c4h = document.createElement("h1");
                    c4h.setAttribute("class","c4h");
                    c4h.textContent="C4H";
                serviceBackground.appendChild(c4h);
                    var delete_card = document.createElement("a");
                    delete_card.setAttribute("class","delete");
                    delete_card.setAttribute("id",resultElement.idService)
                        var delete_img = document.createElement("i");
                        delete_img.setAttribute("class","fas fa-trash-alt");
                    delete_card.appendChild(delete_img);
                serviceBackground.appendChild(delete_card);
                    var img = document.createElement("div");
                    img.setAttribute("class","shoe show");
                    img.setAttribute("color","blue");
                    img.setAttribute("id","map"+resultElement.idService);

                serviceBackground.appendChild(img);
            container.appendChild(serviceBackground);
                var info = document.createElement("div");
                info.setAttribute("class","info");

                    var serviceName = document.createElement("div");
                    serviceName.setAttribute("class","serviceName");
                info.appendChild(serviceName);
                        var div = document.createElement("div");
                            var big = document.createElement("h1");
                            big.setAttribute("class","big");
                            big.textContent=resultElement.category;
                        div.appendChild(big);
                    serviceName.appendChild(div);
                        var small =document.createElement("small");
                        small.setAttribute("class","small");
                        small.textContent=resultElement.address;
                    serviceName.appendChild(small);

                    var description=document.createElement("div");
                    description.setAttribute("class","description");
                        var title = document.createElement("h3");
                        title.setAttribute("class","title");
                        title.textContent="Date and Slot";
                    description.appendChild(title);
                        var text = document.createElement("p");
                        text.setAttribute("class","text");
                        //TODO data e ora
                        text.textContent=resultElement.details;
                    description.appendChild(text);
                info.appendChild(description);

                    var description=document.createElement("div");
                    description.setAttribute("class","description");
                        var title = document.createElement("h3");
                        title.setAttribute("class","title");
                        title.textContent="Service Details";
                    description.appendChild(title);
                        var text = document.createElement("p");
                        text.setAttribute("class","text");
                        text.textContent=resultElement.details;
                    description.appendChild(text);
                info.appendChild(description);

                    var description=document.createElement("div");
                    description.setAttribute("class","description");
                        var title = document.createElement("h3");
                        title.setAttribute("class","title");
                        console.log(role);
                        if(role==="R")
                            title.textContent="Performer";
                        else if(role==="P")
                            title.textContent="Requester";
                    description.appendChild(title);
                        //TODO prendere nome e cognome
                        var text = document.createElement("p");
                        text.setAttribute("class","text");
                        if(role==="R") {
                            if(resultElement.performerUser==0)
                                text.textContent = "Wait for performer";
                            else{
                                text.textContent = resultElement.performerUser;
                            }
                        }else if(role==="P")
                            text.textContent=resultElement.requestUser;
                    description.appendChild(text);
                info.appendChild(description);

                    var description=document.createElement("div");
                    description.setAttribute("class","description");
                        var title = document.createElement("h3");
                        title.setAttribute("class","title")
                        title.textContent="Expiration Date";
                    description.appendChild(title);
                        var text = document.createElement("p");
                        text.setAttribute("class","text");
                        text.textContent=resultElement.requestUser;
                    description.appendChild(text);
                info.appendChild(description);


            container.appendChild(info);



        body.appendChild(container);
        initMap(parseInt(resultElement.latitude),parseInt(resultElement.longitude),"map"+resultElement.idService);
    }
    function initMap(lat,long,where_put) {
        console.log(lat)
        const myLatLng = { lat: lat, lng: long };
        const map = new google.maps.Map(document.getElementById(where_put), {
            zoom: 4,
            center: myLatLng,
        });
        new google.maps.Marker({
            position: myLatLng,
            map,
            title: "Hello World!",
        });
    }


}

