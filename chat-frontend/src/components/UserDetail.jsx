import React, { useState, useEffect } from 'react';
import api from '../services/api';

const UserDetail = ({ userId }) => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const getUserById = async () => {
      try {
        const response = await api.get(`/api/users/${userId}`);
        setUser(response.data);
      } catch (error) {
        console.error('Erro ao carregar usuário:', error);
      }
    };

    getUserById();
  }, [userId]);

  return (
    <div>
      {user ? <div>Nome: {user.username}</div> : <p>Carregando usuário...</p>}
    </div>
  );
};

export default UserDetail;
