import * as React from 'react';
import {TokenContext} from "../Contexts";
import {Row, Col} from 'reactstrap';
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
                <Row>
                    <Col md={6}>
                        <h3>Login</h3>
                        <Login setParams={setAuthParams} setClientData={setClientData}/>
                    </Col>
                    <Col md={6}>
                        <h3>Signup</h3>
                        <Signup setParams={setAuthParams} setClientData={setClientData}/>
                    </Col>
                </Row>
            )
        }
        return (
            <Row>
                <Col md={12}>
                    <Client data={client}/>
                    <button onClick={() => resetAuthParams()} className={"btn btn-md"}>Log out</button>

                </Col>
            </Row>
        )
    }


}