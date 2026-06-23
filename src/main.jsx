// import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { Provider } from "react-redux";
import { BrowserRouter} from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css";
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import "bootstrap-icons/font/bootstrap-icons.css";

import 'primereact/resources/themes/arya-blue/theme.css'
import 'primeicons/primeicons.css';

import "./assets/styles/login.css";
import "./assets/styles/sidebar.css";
import "./assets/styles/dashboard.css";
import "./assets/styles/form.css";
import "./assets/styles/table.css";

import App from './App.jsx'
import { store } from './strore.js';

createRoot(document.getElementById('root')).render(
  <Provider store={store} >
 <BrowserRouter>
  <App />
 </BrowserRouter>
 </Provider>

)
