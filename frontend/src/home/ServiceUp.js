import * as React from 'react';
import {Table } from 'reactstrap';
import {loginURL, tradeURL, historyURL} from "../ServiceURLS";
import {BounceLoader} from "react-spinners";
import axios from "axios/index";

export class ServiceUp extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loginUp: undefined,
            tradeUp: undefined,
            historyUp: undefined,
        };
    }


    componentDidMount() {

        axios.get(loginURL + "actuator/health").then(response => {
            this.setState({
                loginUp: response.data.status === "UP",
            });
        }).catch(err => this.setState({
            loginUp: false,
        }));


        axios.get(tradeURL + "actuator/health").then(response => {
            this.setState({
                tradeUp: response.data.status === "UP",
            });
        }).catch(err => this.setState({
            tradeUp: false,
        }));


        axios.get(historyURL + "actuator/health").then(response => {
            this.setState({
                historyUp: response.data.status === "UP",
            });
        }).catch(err => this.setState({
            historyUp: false,
        }));

    }


    render() {
        const {loginUp, tradeUp, historyUp} = this.state;
        return <Table>
            <thead>
            <tr>
                <th>Service</th>
                <th>Is up</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Login</td>
                <td>{this.getSymbolFromState(loginUp)}</td>
            </tr>
            <tr>
                <td>Trade</td>
                <td>{this.getSymbolFromState(tradeUp)}</td>
            </tr>
            <tr>
                <td>History</td>
                <td>{this.getSymbolFromState(historyUp)}</td>
            </tr>
            </tbody>
        </Table>

    }

    getSymbolFromState(isUp) {
        if (isUp === undefined) {
            return <BounceLoader size={25}/>
        }
        if (isUp === true) {
            return <span role="img" aria-label="Yes">✅</span>;
        }

        return <span role="img" aria-label="No">❌</span>;

    }
}