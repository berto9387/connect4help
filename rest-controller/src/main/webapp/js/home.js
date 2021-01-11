function createHome(){
    const role = localStorage.getItem('role');
    if(role === 'P'){
        homePerformer();
    }else{
        homeRequester();
    }
}

//------------------------ PERFOMER ------------------------------ //

function homePerformer(){
    var text = document.createTextNode("Servizi nella tua zona")
    document.getElementById('home').appendChild(text)

    var text2 = document.createTextNode("Servizi accettati")
    document.getElementById('home2').appendChild(text2)
    //inserimento search bar
    createFormSearchBar()

}

function createFormSearchBar(){
    var formSearch = document.createElement("form");
    formSearch.id = "findServices";
    formSearch.method = "get";

    var divSearch = document.getElementById("divForm");

        var divInnerForm = document.createElement("div")
        divInnerForm.className = "inner-form"

            var divInputField = document.createElement("div")
            divInputField.className = "input-filed first-wrap"

            var label = document.createElement("label")
            var textLabel = document.createTextNode("Insert address or city where you want to search services")
            label.appendChild(textLabel)

            var inputAddress = document.createElement("input")
            inputAddress.id = "search"
            inputAddress.name = "address"
            inputAddress.type = "text"
            inputAddress.placeholder = "Via, number, city ...."

            divInputField.appendChild(label)
            divInputField.appendChild(inputAddress)

        divInnerForm.appendChild(divInputField)

            var inputFieldSecond = document.createElement("div")
            inputFieldSecond.className = "input-filed second-wrap"

            var label2 = document.createElement("label")
            var textLabel2 = document.createTextNode("Radius")

            label2.appendChild(textLabel2)

            var select = document.createElement("select")
            select.name = "radius"
            select.id = "location"
            var optionValue = [1, 2, 5, 10, 20];
            for (var i = 0; i < optionValue.length; i++){
                var option = document.createElement("option")
                option.value = optionValue[i].toString();
                option.appendChild(document.createTextNode(""+optionValue[i]+" Km"))
                select.appendChild(option)
            }

            inputFieldSecond.appendChild(label2)
            inputFieldSecond.appendChild(select)

        divInnerForm.appendChild(inputFieldSecond)

            var divSubmit = document.createElement("div")
            divSubmit.className = "input-field third-wrap"

            var buttonSubmit = document.createElement("button")
            buttonSubmit.className = "btn-search"
            buttonSubmit.type = "button"

            var textSubmit = document.createTextNode("Search")
            buttonSubmit.appendChild(textSubmit)

            divSubmit.appendChild(buttonSubmit)

    divInnerForm.appendChild(divInputField)
    divInnerForm.appendChild(inputFieldSecond)
    divInnerForm.appendChild(divSubmit)

    formSearch.appendChild(divInnerForm)
    divSearch.appendChild(formSearch)

}


//------------------------ REQUESTER ------------------------------ //

function homeRequester(){
    var text = document.createTextNode("Stato dei tuoi servizi")
    var text2 = document.createTextNode("Richiedi un servizio")

    document.getElementById('home').appendChild(text)
    document.getElementById('home').href = "HomeUnique.html"
    document.getElementById('home2').appendChild(text2)
    document.getElementById('home2').href = "RequestService.html"

    setNavBarBorder();

}

function setNavBarBorder(){

    if(window.location.href.indexOf("HomeUnique") === -1 ) { // Se NON siamo in HomeUnique.html togliamo la classe current_page_item

        var homeUniqueBarButton = document.getElementById('home').parentElement
        if (homeUniqueBarButton.classList.contains('current_page_item'))
            homeUniqueBarButton.classList.remove('current_page_item')

    } else {                                                  // Se siamo in HomeUnique.html aggiungiamo la classe current_page_item

        homeUniqueBarButton = document.getElementById('home').parentElement
        homeUniqueBarButton.classList.add('current_page_item')

    }
    if(window.location.href.indexOf("RequestService") === -1 ) {// Se NON siamo in ReqeustService.html togliamo la classe current_page_item

        var requestServiceBarButton = document.getElementById('home2').parentElement
        if (requestServiceBarButton.classList.contains('current_page_item'))
            requestServiceBarButton.classList.remove('current_page_item')

    } else {                                                        // Se siamo in ReqeustService.html aggiungiamo la classe current_page_item

        requestServiceBarButton = document.getElementById('home2').parentElement
        requestServiceBarButton.classList.add('current_page_item')

    }

    if(window.location.href.indexOf("RequestService") === -1 ) {// Se NON siamo in Chat.html togliamo la classe current_page_item

        var requestServiceBarButton = document.getElementById('home3').parentElement
        if (requestServiceBarButton.classList.contains('current_page_item'))
            requestServiceBarButton.classList.remove('current_page_item')

    } else {                                                        // Se siamo in Chat.html aggiungiamo la classe current_page_item

        requestServiceBarButton = document.getElementById('home3').parentElement
        requestServiceBarButton.classList.add('current_page_item')

    }
}


/**
<form id="findServices" method="get">
    <div class="inner-form">
        <div class="input-field first-wrap">
            <label>Insert address or city where you want to search services</label>
            <input id="search" name = "address" type="text" placeholder="Via, number, city ...." />
        </div>
        <div class="input-field second-wrap">
            <label>Radius</label>
            <select name = "radius" id="location">
                <option value="1">1 Km</option>
                <option value="2">2 Km</option>
                <option value="5">5 Km</option>
                <option value="10">10 Km</option>
                <option value="20">20 Km</option>
            </select>
        </div>
        <div class="input-field third-wrap">
            <button class="btn-search" type="button" style="margin-top: 17px;" onclick="fs()">Search</button>
        </div>
    </div>
</form>

 */