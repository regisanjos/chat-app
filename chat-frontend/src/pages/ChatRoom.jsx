import React, { useState } from 'react';

const ChatRoom = ({ room, user, onExitRoom }) => {
  const [messages, setMessages] = useState([]);

  const sendMessage = () => {
    setMessages([...messages, `Mensagem de ${user.username}`]);
  };

  return (
    <div className="chat-room">
      <h2>{room}</h2>
      <div className="messages">
        {messages.map((msg, index) => (
          <p key={index}>{msg}</p>
        ))}
      </div>
      <button onClick={sendMessage}>Enviar Mensagem</button>

      {/* Botão para sair da sala de chat */}
      <button onClick={onExitRoom} className="exit-room-button">
        Sair da Sala
      </button>
    </div>
  );
};

export default ChatRoom;
