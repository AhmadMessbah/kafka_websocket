let stompClient = null;
let userId = null;

function connect() {
    userId = document.getElementById("user-id").value.trim();
    if (!userId) {
        alert("Please enter your user ID");
        return;
    }

    const socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        // اشتراک در پیام‌های عمومی
        stompClient.subscribe('/topic/public', function (message) {
            const chatMessage = JSON.parse(message.body);
            showMessage(chatMessage.senderId, chatMessage.content, "Public");
        });

        // اشتراک در پیام‌های خصوصی
        stompClient.subscribe('/user/' + userId + '/topic/private', function (message) {
            const chatMessage = JSON.parse(message.body);
            showMessage(chatMessage.senderId, chatMessage.content, "Private");
        });
    });
}

function sendMessage() {
    const messageContent = document.getElementById("message-input").value.trim();
    const recipientId = document.getElementById("recipient-input").value.trim();
    if (messageContent && userId) {
        const chatMessage = {
            senderId: userId,
            recipientId: recipientId || null,
            content: messageContent
        };
        stompClient.send("/app/sendMessage", {}, JSON.stringify(chatMessage));
        document.getElementById("message-input").value = '';
        document.getElementById("recipient-input").value = '';
    }
}

function sendToAll() {
    const messageContent = document.getElementById("message-input").value.trim();
    if (messageContent && userId) {
        const chatMessage = {
            senderId: userId,
            recipientId: null,
            content: messageContent
        };
        stompClient.send("/app/sendMessage", {}, JSON.stringify(chatMessage));
        document.getElementById("message-input").value = '';
    }
}

function showMessage(senderId, content, type) {
    const chatBox = document.getElementById("chat-box");
    const messageElement = document.createElement("div");
    messageElement.textContent = `[${type}] ${senderId}: ${content}`;
    chatBox.appendChild(messageElement);
    chatBox.scrollTop = chatBox.scrollHeight;
}

document.getElementById("user-id").addEventListener("change", connect);