import React, { useState } from 'react';
import LoginPage from './pages/LoginPage.jsx';
import ChatRoomList from './pages/ChatRoomList.jsx';
import ChatRoom from './components/ChatRoom.jsx';
import Footer from './components/Footer';
import './styles/main.css';

const App = () => {
  const [user, setUser] = useState(null);  // Estado do usuário logado
  const [currentRoom, setCurrentRoom] = useState(null); // Sala atual
  const rooms = ['Sala 1', 'Sala 2', 'Sala 3'];


  const handleLoginSuccess = (userData) => {
    setUser(userData);
    localStorage.setItem('user', JSON.stringify(userData));
  };

  return (
    <div className="app-container">
      {!user ? (
        <LoginPage onLoginSuccess={handleLoginSuccess} />
      ) : !currentRoom ? (
        <ChatRoomList rooms={rooms} onRoomSelect={(room) => setCurrentRoom(room)} />
      ) : (
        <ChatRoom
          room={currentRoom}
          user={user}
          onExitRoom={() => setCurrentRoom(null)} // Função para sair da sala
        />
      )}

      {user && (
        <button onClick={() => setUser(null)} className="logout-button">
          Logout
        </button>
      )}

      <Footer />
    </div>
  );
};

export default App;
