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
        if (!err) {
            console.log('msg');
        }
    });
}

function getChat(idReceiver){
    db.find({
        selector: {idReceiver: idReceiver},
        fields: ['_id', 'name'],
        sort: ['name']
    }).then(function (result) {
        // handle result
    }).catch(function (err) {
        console.log(err);
    });
}