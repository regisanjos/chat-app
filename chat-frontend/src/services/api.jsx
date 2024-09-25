import axios from 'axios';


const api = axios.create({
  baseURL: 'http://189.8.205.54:8080',
});

export default api;
