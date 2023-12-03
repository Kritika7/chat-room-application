let stompClient = null;

function connectWebSocket() {
	const socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		//console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/messages', function(response) {
			
			showMessage(JSON.parse(response.body));
		}, function(error) {	
			//console.error('Error during WebSocket connection:', error);
			// Retry the connection after a delay
			setTimeout(connectWebSocket, 5000); // Retry after 5 seconds
		});
	});
}

function showMessage(message) {
	//console.log(message, "message");
	$("#message-container-table").prepend(`<tr><td><b>${message.sender.username} :</b> ${message.content}</td></tr>`);
}


function sendMessage() {
	let jsonOb = {
		content: $("#message").val(),
		name: localStorage.getItem("name")
	}

	if (stompClient && stompClient.connected) {
		//console.log("sending message : " + JSON.stringify(jsonOb));
		stompClient.send("/app/chat", {}, JSON.stringify(jsonOb));
		$("#message").val(''); // Clear the input field
	} else {
		console.error('WebSocket is not connected');
	}
}

jQuery(document).ready((e) => {
	connectWebSocket();
	$("#login").click(() => {
		let name = $("#username").val();
		localStorage.setItem("name", name);
		$("#name-title").html(`Welcome , <b>${name} </b>`);

	})

	$('#chat-form').click(() => {
		sendMessage();
	});

});