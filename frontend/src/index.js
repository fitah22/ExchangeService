import React from 'react';
import ReactDOM from 'react-dom';
import "./css/bootstrap.4.min.css";
import {BrowserRouter} from 'react-router-dom';
import {routes} from './routes';

function renderApp() {
    ReactDOM.render(<BrowserRouter children={routes}/>, document.getElementById('root'));
}

renderApp();