window.addEventListener("load",loadContact,false);

var idUser;

function loadContact(){
    document.getElementById("sendMessage").addEventListener("click", sendMessage, false);
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
        .then(result => createContacts(result,localStorage.getItem("role")))
        .catch(error => console.log('error', error));


}

function createContacts(result, role) {
    for (var i=0; i<result.length;i++){
        createContact(result[i],role);
    }
}
function createContact(result,role){
    var id;
    var nome;
    var contacts=document.getElementById("contacts");
    var li=document.createElement("li");
        var div1=document.createElement("div");
        div1.setAttribute("class","d-flex bd-highlight");
            var div2=document.createElement("div");
            div2.className="user_info";
                var span=document.createElement("span");
                if(role=="P") {
                    console.log(result)
                    id=result.requestUser; //Errore nel nome(RISOLTO)
                    nome=result.nameRequester;
                    span.textContent = result.nameRequester + " " + result.surnameRequester;
                }else {
                    id=result.performerUser;
                    nome=result.namePerformer;
                    span.textContent = result.namePerformer + " " + result.surnamePerformer;
                }
                div2.appendChild(span);
        div1.appendChild(div2);
    li.appendChild(div1);
    var selectUserReceive = function (id_user) {
        return function () {
            deleteChat(id_user);
            idUser=id_user;
            var chatWith=document.getElementById("chatWith");
            chatWith.textContent="Chat with "+nome;
            console.log(idUser)

        };
    }(id,nome);
    li.addEventListener("click",selectUserReceive)
    contacts.appendChild(li);
}
function clearElem(elem){
    if(elem.firstChild==null)
        return;

    elem.firstChild.remove();
    clearElem(elem);
}
function deleteChat(id_user){

    console.log(id_user)
    if(id_user==idUser){
        return;
    }
    var chat=document.getElementById("chat");

    clearElem(chat);

}

function sendMessage(){
    var messageBox = document.getElementById("textMessage");
    var message = messageBox.value;

    if(message.length == 0)
        return;

    var chatBox = document.getElementById("chat");

    var messageStyle = createMessageSent(message);

    chatBox.appendChild(messageStyle);

    messageBox.value="";


}

function createMessageSent(message){
    var timeStamp = new Date();
    var divMessageBox = document.createElement("div");
    divMessageBox.setAttribute("class", "d-flex justify-content-end mb-4");

        var divMessageValue = document.createElement("div");
        divMessageValue.setAttribute("class", "msg_cotainer_send");

            var content = document.createTextNode(""+message);



            var spanTime = document.createElement("span")
            spanTime.setAttribute("class", "msg_time_send")
                var contentSpan = document.createTextNode(""+timeStamp.getHours()+":"+timeStamp.getMinutes()
                    +", "+timeStamp.getDate()+"/"+(timeStamp.getMonth()+1))
            spanTime.appendChild(contentSpan)

        divMessageValue.appendChild(content);
        divMessageValue.appendChild(spanTime);
    divMessageBox.appendChild(divMessageValue);
    //divMessageBox.appendChild(spanTime);

    return divMessageBox;
}

