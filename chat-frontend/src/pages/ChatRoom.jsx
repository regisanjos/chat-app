import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ChatRoom = ({ room, user }) => {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');

  useEffect(() => {
    const fetchMessages = async () => {
      const response = await axios.get(`/api/rooms/${room.id}/messages`);
      setMessages(response.data);
    };
    fetchMessages();
  }, [room.id]);

  const sendMessage = async (e) => {
    e.preventDefault();
    if (newMessage.trim() === '') return;

    try {
      await axios.post(`/api/rooms/${room.id}/messages`, {
        content: newMessage,
        userId: user.id,
      });
      setMessages([...messages, { content: newMessage, sender: user.username }]);
      setNewMessage('');
    } catch (err) {
      console.error('Erro ao enviar mensagem:', err);
    }
  };

  return (
    <div className="chat-room">
      <h2>{room.name}</h2>
      <div className="message-list">
        {messages.map((msg, index) => (
          <p key={index}>
            <strong>{msg.sender}: </strong>{msg.content}
          </p>
        ))}
      </div>
      <form onSubmit={sendMessage} className="message-input">
        <input
          type="text"
          placeholder="Digite uma mensagem"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
        />
        <button type="submit">Enviar</button>
      </form>
    </div>
  );
};

export default ChatRoom;
