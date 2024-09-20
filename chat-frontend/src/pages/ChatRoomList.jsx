import React from 'react';

const ChatRoomList = ({ rooms }) => {


  const openChatRoomWindow = (room) => {

    const chatWindow = window.open(
      '',
      '_blank',
      `width=400,height=600,scrollbars=yes,resizable=yes`
    );


    chatWindow.document.write(`
      <html>
        <head>
          <title>${room}</title>
          <style>
            body { font-family: 'Arial', sans-serif; background-color: #f4f4f4; padding: 20px; }
            .chat-messages { border: 1px solid #ccc; padding: 10px; height: 300px; overflow-y: auto; }
            .message { padding: 5px 0; }
            .chat-input { width: 100%; padding: 10px; margin-top: 10px; }
            .send-button { width: 100%; padding: 10px; background-color: #333; color: white; border: none; cursor: pointer; margin-top: 10px; }
          </style>
        </head>
        <body>
          <h2>${room}</h2>
          <div id="chat-room-root"></div>
          <div class="chat-messages" id="messages-${room}">
          </div>
          <input type="text" id="message-input-${room}" class="chat-input" placeholder="Escreva sua mensagem" />
          <button id="send-button-${room}" class="send-button">Enviar</button>

          <script>
            // Script para capturar o envio de mensagens
            const sendMessage = () => {
              const messageInput = document.getElementById('message-input-${room}');
              const message = messageInput.value;
              if (message.trim() !== '') {
                const messageContainer = document.getElementById('messages-${room}');
                const newMessageElement = document.createElement('p');
                newMessageElement.textContent = message;
                newMessageElement.className = 'message';
                messageContainer.appendChild(newMessageElement);
                messageInput.value = '';
              }
            };

            // Listener do botão de envio de mensagem
            document.getElementById('send-button-${room}').addEventListener('click', sendMessage);

            // Opcional: permitir enviar mensagens ao pressionar Enter
            document.getElementById('message-input-${room}').addEventListener('keypress', function(e) {
              if (e.key === 'Enter') {
                sendMessage();
              }
            });
          </script>
        </body>
      </html>
    `);
  };

  return (
    <div className="chat-room-list">
      <h2>Escolha uma Sala de Chat</h2>
      <ul>
        {rooms.map((room, index) => (
          <li key={index} onClick={() => openChatRoomWindow(room)} className="chat-room-item">
            {room}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ChatRoomList;
