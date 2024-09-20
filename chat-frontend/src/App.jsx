import React, { useState } from 'react';
import LoginPage from './pages/LoginPage.jsx';
import ChatRoomList from './pages/ChatRoomList.jsx';
import Footer from './components/Footer';
import './styles/main.css';

const App = () => {
  const [user, setUser] = useState(null);  // Estado do usuário logado
  const rooms = ['Sala 1', 'Sala 2', 'Sala 3'];  // Lista de salas de chat

  // Função para quando o login for bem-sucedido
  const handleLoginSuccess = (userData) => {
    setUser(userData);
    localStorage.setItem('user', JSON.stringify(userData));
  };

  return (
    <div className="app-container">
      {!user ? (
        <LoginPage onLoginSuccess={handleLoginSuccess} />
      ) : (
        <ChatRoomList rooms={rooms} />
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
