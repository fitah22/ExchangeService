import * as React from 'react';
import {TokenContext} from "../Contexts";
import {Signup} from "./Signup";
import {Login} from "./Login";

export class LoginService extends React.Component {


    render() {
        return (
            <TokenContext.Consumer>
                {value => this.renderBasedOnValue(value)}
            </TokenContext.Consumer>
        );

    }

    renderBasedOnValue = (context) => {
        const {auth, setAuthParams, resetAuthParams, setClientData} = context;

        if (!auth) {
            return (
                <div>
                    <div>
                        Login:
                        <Login setParams={setAuthParams} setClientData={setClientData}/>
                    </div>
                    <div>
                        Signup:
                        <Signup setParams={setAuthParams} setClientData={setClientData}/>
                    </div>
                </div>
            )
        }
        return (
            <div>
                <button onClick={() => resetAuthParams()} className={"btn btn-md"}>Log out</button>

            </div>
        )
    }


}