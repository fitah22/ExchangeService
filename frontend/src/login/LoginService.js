import * as React from 'react';
import {Signup} from "./Signup";
import {Login} from "./Login";


export class LoginService extends React.Component {


    render() {
        return (
            <div>
                <div>
                    Login:
                    <Login/>
                </div>
                <div>
                    Signup:
                    <Signup/>
                </div>
            </div>
        )
    }
}