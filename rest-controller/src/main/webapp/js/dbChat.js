var db = new PouchDB('my_database');
var remoteCouch = false;

function addMessage(idReceiver,timestamp,msg,sender) {
    var todo = {
        _id: new Date().toISOString(),
        idReceiver: idReceiver,
        timestamp: timestamp,
        msg:msg,
        sender:sender
    };
    db.put(todo, function callback(err, result) {
        return err;
    });
}

function getChat(idReceiver){
    db.createIndex({
        index: {fields: ['idReceiver','timestamp']}
    }).then(function () {
        db.find({
            selector: {idReceiver: idReceiver},
            fields: ['idReceiver','timestamp','msg','sender'],
            sort: ['idReceiver','timestamp']
        }).then(function (result) {
            for(var i=0;i<result.docs.length;i++){
                var aux=result.docs[i];
                var messageStyle=createMessageSent(aux.msg, aux.sender); //aggiungere campo timestamp
                var chatBox = document.getElementById("chat");
                chatBox.appendChild(messageStyle);
            }
            console.log(result);
        }).catch(function (err) {
            console.log(err);
        });
    });

}