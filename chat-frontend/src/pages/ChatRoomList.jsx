import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ChatRoomList = ({ onSelectRoom }) => {
  const [rooms, setRooms] = useState([]);

  useEffect(() => {
    const fetchRooms = async () => {
      const response = await axios.get('/api/rooms');
      setRooms(response.data);
    };
    fetchRooms();
  }, []);

  return (
    <div className="room-list">
      <h2>Salas de Chat</h2>
      <ul>
        {rooms.map((room) => (
          <li key={room.id} onClick={() => onSelectRoom(room)}>
            {room.name}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ChatRoomList;
