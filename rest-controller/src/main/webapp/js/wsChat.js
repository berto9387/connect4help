var websocket;
var server = document.getElementById("server");
var message = document.getElementById("message");
var connecting = document.getElementById("connecting");
var connected = document.getElementById("connected");
var content = document.getElementById("content");
var output = document.getElementById("output");

server.value = "ws://localhost:8080";
connected.style.display = "none";
content.style.display = "none";

var timerID = 0;
function keepAlive() {
    var timeout = 20000;
    if (webSocket.readyState == webSocket.OPEN) {
        webSocket.send('!PING');
    }
    timerId = setTimeout(keepAlive, timeout);
}
function cancelKeepAlive() {
    if (timerId) {
        clearTimeout(timerId);
    }
}

function connect()
{
    wsHost = server.value;
    websocket = new WebSocket(wsHost);
    showScreen('<b>Connecting to: ' +  wsHost + '</b>');
    websocket.onopen = function(evt) { onOpen(evt) };
    websocket.onclose = function(evt) { onClose(evt) };
    websocket.onmessage = function(evt) { onMessage(evt) };
    websocket.onerror = function(evt) { onError(evt) };
};

function disconnect() {
    websocket.close();
};

function toggle_connection(){
    if (websocket && websocket.readyState == websocket.OPEN) {
        disconnect();
    } else {
        connect();
    };
};

function sendTxt() {
    if (websocket.readyState == websocket.OPEN) {
        var msg = message.value;
        websocket.send(msg);
        showScreen('sending: ' + msg);
    } else {
        showScreen('websocket is not connected');
    };
};

function onOpen(evt) {
    showScreen('<span style="color: green;">CONNECTED </span>');
    connecting.style.display = "none";
    connected.style.display = "";
    content.style.display = "";
};

function onClose(evt) {
    showScreen('<span style="color: red;">DISCONNECTED</span>');
};

function onMessage(evt) {
    showScreen('<span style="color: blue;">RESPONSE: ' + evt.data + '</span>');
};

function onError(evt) {
    showScreen('<span style="color: red;">ERROR: ' + evt.data + '</span>');
};

function showScreen(html) {
    var el = document.createElement("p");
    el.innerHTML = html;
    output.insertBefore(el, output.firstChild);
};

function clearScreen() {
    output.innerHTML = "";
};