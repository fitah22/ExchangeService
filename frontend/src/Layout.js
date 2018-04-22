import * as React from 'react';
import axios from "axios/index";
import {Login} from "./login/Login";
import {Signup} from "./login/Signup";
import Cookies from 'universal-cookie';
import {loginURL} from "./ServiceURLS";
import {TokenContext} from "./Contexts";
import {NavigationMenu} from './home/NavigationMenu';

const cookies = new Cookies();

export class Layout extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            auth: cookies.get('auth'),
            setAuthParams: this.setAuthParams.bind(this),
            resetAuthParams: this.resetAuthParams.bind(this),
            client: undefined,
            setClientData: this.setClientData.bind(this),
            updateWholeClientData: this.updateWholeClientData.bind(this),
            updatePassword: this.updatePassword.bind(this),
            updateAddress: this.updateAddress.bind(this),
            loginOpen: false,
            signupOpen: false,
            toggleLogin: this.toggleLogin.bind(this),
            toggleSignup: this.toggleSignup.bind(this)
        };

    }
    componentDidMount(){
        if(this.state.auth) {
            console.log("Auth cookie saved. Now fetching its data");
            this.updateWholeClientData();
        }
    }

    setAuthParams(username, password) {
        const auth = {
            username,
            password
        };
        cookies.set('auth', auth);
        console.log("cookie set");
        this.setState({
            auth,
            loginOpen: false,
            signupOpen: false,
        });
    };

    updateWholeClientData() {
        const config = {
            auth: this.state.auth
        };

        //Use login since it returns this user
        axios.post(loginURL + "login", {}, config).then((response) => {
            this.setClientData(response.data);
            console.log("Client data set");
        }).catch(() => cookies.remove('auth'));
    }

    updatePassword(password) {
        const {auth} = this.state;
        const newAuth = Object.assign({}, auth);
        newAuth.password = password;
        cookies.set('auth', newAuth);
        this.setState({
            auth: newAuth
        });
    }

    updateAddress(address) {
        const {client} = this.state;
        const newClient = Object.assign({}, client);
        newClient.address = address;
        this.setState({
            client: newClient
        });
    }

    resetAuthParams() {
        this.setState({
            auth: undefined,
            loginOpen: false,
            signupOpen: false,
            client: undefined,
        });
        cookies.remove('auth');
    };

    setClientData(clientdata) {
        this.setState({
                client: clientdata
            }
        );
    };

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