import * as React from 'react';
import {NavigationMenu} from './NavigationMenu';
import {TokenContext} from "../Contexts";
import {Signup} from "../login/Signup";
import {Login} from "../login/Login";


export class Layout extends React.Component {

    constructor(props) {
        super(props);

        this.resetAuthParams = () => {
            this.setState({
                auth: undefined,
                loginOpen: false,
                signupOpen: false,
            });
        };

        this.setAuthParams = (username, password) => {
            this.setState({
                auth: {
                    username,
                    password
                },
                loginOpen: false,
                signupOpen: false,
            });
        };

        this.setClientData = (clientdata) => {
            this.setState({
                    client: clientdata
                }
            );
        };

        this.state = {
            auth: undefined,//{username:"hello",password:"123"},//undefined,
            setAuthParams: this.setAuthParams,
            resetAuthParams: this.resetAuthParams,
            setClientData: this.setClientData,
            loginOpen: false,
            signupOpen: false,
            toggleLogin: this.toggleLogin.bind(this),
            toggleSignup: this.toggleSignup.bind(this)
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
        const {auth, setAuthParams, setClientData, signupOpen, loginOpen, toggleSignup, toggleLogin, resetAuthParams} = this.state;
        return <TokenContext.Provider value={this.state}>
            <NavigationMenu isAuth={auth !== undefined}
                            signup={toggleSignup}
                            login={toggleLogin}
                            restAuth={resetAuthParams}/>

            <div className='container-fluid'>
                {this.props.children}
                <Login setParams={setAuthParams} setClientData={setClientData} open={loginOpen} toggle={toggleLogin}/>
                <Signup setParams={setAuthParams} setClientData={setClientData} open={signupOpen}
                        toggle={toggleSignup}/>
            </div>
        </TokenContext.Provider>;
    }
}