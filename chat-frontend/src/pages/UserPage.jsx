import React, { useState } from 'react';
import api from '../services/api';

const UserPage = () => {
  const [username, setUsername] = useState('');

  const createUser = async () => {
    try {
      const response = await api.post('/api/users', { username });
      console.log('Usuário criado:', response.data);
    } catch (error) {
      console.error('Erro ao criar o usuário:', error);
    }
  };

  return (
    <div>
      <input
        type="text"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        placeholder="Nome de Usuário"
      />
      <button onClick={createUser}>Criar Usuário</button>
    </div>
  );
};

export default UserPage;
