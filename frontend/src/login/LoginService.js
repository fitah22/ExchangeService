import * as React from 'react';
import {TokenContext} from "../Contexts";
import {Signup} from "./Signup";
import {Login} from "./Login";
import {Client} from "./Client";

export class LoginService extends React.Component {


    render() {
        return (
            <TokenContext.Consumer>
                {value => this.renderBasedOnValue(value)}
            </TokenContext.Consumer>
        );

    }

    renderBasedOnValue = (context) => {
        const {auth, client, setAuthParams, resetAuthParams, setClientData} = context;

        if (!auth) {
            return (
                <React.Fragment>
                    <div className={"col-md-6"}>
                        <h3>Login</h3>
                        <Login setParams={setAuthParams} setClientData={setClientData}/>
                    </div>
                    <div className={"col-md-6"}>
                        <h3>Signup</h3>
                        <Signup setParams={setAuthParams} setClientData={setClientData}/>
                    </div>
                </React.Fragment>
            )
        }
        return (
            <div className={"col-md-12"}>
                <Client data={client} />
                <button onClick={() => resetAuthParams()} className={"btn btn-md"}>Log out</button>

            </div>
        )
    }


}