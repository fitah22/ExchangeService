import * as React from 'react';
import {TokenContext} from "../Contexts";
import {Row, Col, Button} from 'reactstrap';
import {Signup} from "./Signup";
import {Login} from "./Login";
import {Client} from "./Client";

export class LoginService extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loginOpen: false,
            signupOpen: false
        };
    }

    toggleLogin() {
        this.setState({
            loginOpen: !this.state.loginOpen
        });
    }

    toggleSignup() {
        this.setState({
            signupOpen: !this.state.signupOpen
        });
    }


    render() {
        return (
            <TokenContext.Consumer>
                {value => this.renderBasedOnValue(value)}
            </TokenContext.Consumer>
        );

    }

    renderBasedOnValue = (context) => {
        const {auth, client, setAuthParams, resetAuthParams, setClientData} = context;
        const {loginOpen, signupOpen} = this.state;
        if (!auth) {
            return (<React.Fragment>
                    <Row>
                        <Col md={12}>
                            <h1>You are not logged in yet</h1>
                            Please login or sign up.
                            <br/>
                            <Button className={"mr-2"} color={"primary"}
                                    onClick={() => this.toggleLogin()}>Login</Button>
                            <Button color={"primary"} onClick={() => this.toggleSignup()}>Sign up</Button>
                            <Login setParams={setAuthParams} setClientData={setClientData} open={loginOpen} toggle={() =>this.toggleLogin()}/>
                            <Signup setParams={setAuthParams} setClientData={setClientData} open={signupOpen} toggle={() => this.toggleSignup()}/>
                        </Col>
                    </Row>
                </React.Fragment>
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