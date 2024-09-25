import React, { useState, useEffect } from 'react';

const ChatRoom = ({ room, user, onExitRoom }) => {
  const [messages, setMessages] = useState([]);
  const [socket, setSocket] = useState(null); // Estado para armazenar a conexão WebSocket


    useEffect(() => {

    const ws = new WebSocket(`ws://localhost:8080/ws/chat?room=${room}&user=${user.username}`);

    ws.onmessage = (event) => {
      //  uma mensagem é recebida do servidor, ela é adicionada à lista de mensagens
      const newMessage = event.data;
      setMessages((prevMessages) => [...prevMessages, newMessage]);
    };


    setSocket(ws);


    return () => {
      ws.close();
    };
  }, [room, user.username]);

  const sendMessage = () => {
    const message = `Mensagem de ${user.username}`;
    if (socket) {
      socket.send(message); //  mensagem via WebSocket para o servidor
    }
    setMessages([...messages, message]);
  };

  return (
    <div className="chat-room">
      <h2>{room}</h2>
      <div className="messages">
        {messages.map((msg, index) => (
          <p key={index}>{msg}</p>
        ))}
      </div>
      <input
        type="text"
        placeholder="Digite sua mensagem"
        onKeyPress={(e) => {
          if (e.key === 'Enter') sendMessage();
        }}
      />
      <button onClick={sendMessage}>Enviar Mensagem</button>

      {/* Botão para sair da sala de chat */}
      <button onClick={onExitRoom} className="exit-room-button">
        Sair da Sala
      </button>
    </div>
  );
};

export default ChatRoom;
