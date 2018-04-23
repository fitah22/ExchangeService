import React from 'react';
import ReactDOM from 'react-dom';
import "./css/bootstrap.4.min.css";
import {HashRouter} from 'react-router-dom';
import {routes} from './routes';

function renderApp() {
    ReactDOM.render(<HashRouter children={routes}/>, document.getElementById('root'));
}

renderApp();