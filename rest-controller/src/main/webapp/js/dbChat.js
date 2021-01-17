var db = new PouchDB('my_database');
var remoteCouch = false;
db.createIndex({
    index: {fields: ['idReceiver','timestamp']}
});
function addMessage(idReceiver,timestamp,msg,sender) {
    var todo = {
        _id: new Date().toISOString(),
        idReceiver: idReceiver,
        timestamp: timestamp,
        msg:msg,
        sender:sender
    };
    db.put(todo, function callback(err, result) {
        if (!err) {
            console.log('msg');
        }
    });
}

function getChat(idReceiver){
    db.find({
        selector: {idReceiver: idReceiver},
        fields: ['idReceiver','timestamp','msg','sender'],
        sort: ['timestamp']
    }).then(function (result) {
        console.log(result.docs[0]);
    }).catch(function (err) {
        console.log(err);
    });
}