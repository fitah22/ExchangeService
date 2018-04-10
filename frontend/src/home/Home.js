import * as React from 'react';

export class Home extends React.Component {
    render() {
        return <div>
            <h1>Final project - Software Architecture and Frameworks</h1>
            <p>Welcome to the home screen of my final project.</p>
            <p>
                A application communicating with several microservices that are coded in spring boot.
                Please see the assignment PDF for detalis.
            </p>

            <br/>
            <hr/>

            <p>In this project i have used these technologies:</p>
            <ul>
                <li><a href='https://projects.spring.io/spring-boot/'>Spring boot</a> as backend (with MySQL database)
                </li>
                <li><a href='https://facebook.github.io/react/'>React</a> as frontend</li>

            </ul>
            <p><a href='https://github.com/facebook/create-react-app'>create-react-app</a> for easy
                configuration.</p>
            <p>
                Please read the README for more information.
            </p>
        </div>;
    }
}
