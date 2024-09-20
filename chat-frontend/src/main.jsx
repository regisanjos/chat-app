
import React, { useState } from 'react';
import LoginPage from './pages/LoginPage.jsx';
import ChatRoomList from './pages/ChatRoomList.jsx';
import ChatRoom from './pages/ChatRoom.jsx';


const App = () => {
  const [user, setUser] = useState(null);
  const [selectedRoom, setSelectedRoom] = useState(null);

  const handleLoginSuccess = (userData) => {
    setUser(userData);
  };

  const handleRoomSelect = (room) => {
    setSelectedRoom(room);
  };

  return (
    <div className="app-container">
      {!user ? (
        <LoginPage onLoginSuccess={handleLoginSuccess} />
      ) : !selectedRoom ? (
        <ChatRoomList onSelectRoom={handleRoomSelect} />
      ) : (
        <ChatRoom room={selectedRoom} user={user} />
      )}

    </div>
  );
};

export default App;
