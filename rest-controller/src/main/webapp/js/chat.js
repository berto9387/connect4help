window.addEventListener("load",loadContact,false);

var idUser;

function loadContact(){
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
                    id=result.requesterUser;
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
    if(id_user==idUser){
        return;
    }
    var chat=document.getElementById("chat");
    clearElem(chat);

}