import * as React from 'react';
import {NavigationMenu} from './NavigationMenu';
import {TokenContext} from "../Contexts";


export class Layout extends React.Component {

    constructor(props) {
        super(props);

        this.resetAuthParams = () => {
            this.setState({
                auth: undefined,
            });
        };

        this.setAuthParams = (username, password) => {
            this.setState({
                auth: {
                    username,
                    password
                }
            });
        };

        this.setClientData = (clientdata) => {
            this.setState({
                client: clientdata}
                );
        };

        this.state = {
            auth: undefined,
            setAuthParams: this.setAuthParams,
            resetAuthParams: this.resetAuthParams,
            setClientData: this.setClientData
        };

    }

    render() {
        return <TokenContext.Provider value={this.state}>
            <NavigationMenu/>

            <div className='container-fluid'>
                <div className='row'>
                    <div className='col-md-12'>
                        {this.props.children}
                    </div>
                </div>
            </div>
        </TokenContext.Provider>;
    }
}