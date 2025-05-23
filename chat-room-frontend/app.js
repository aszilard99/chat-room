const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ep-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/global', (greeting) => {
    console.log(JSON.parse(greeting.body))
    console.log(JSON.parse(greeting.body).message)
        showGreeting(JSON.parse(greeting.body).message);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

async function login() {
    const url = "http://localhost:8080/login"
    let username = "username"
    let password = "password"
    const response = await fetch(url, {
                                        method: "POST",
                                        credentials: "include",
                                        headers: {
                                            "Content-Type": "application/json"
                                        },
                                        body: JSON.stringify({ username: username, password: password})
                                    }
                                )
    console.log(response)
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/chat/global",
        body: JSON.stringify({'message': $("#name").val()})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
    $( "#login" ).click(() => login());
});