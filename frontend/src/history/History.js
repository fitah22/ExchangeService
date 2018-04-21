import * as React from 'react';
import axios from "axios";
import {Link} from "react-router-dom";
import {historyURL} from "../ServiceURLS";
import {Table, Col, Row} from 'reactstrap';

export class History extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            log: undefined
        };
    }

    componentDidMount() {
        axios.get(historyURL + "all").then(response => {
            console.log(response);
            this.setState({
                log: response.data
            })
        })
    }


    render() {
        return <React.Fragment>
            <h1>History</h1>
            {this.renderLog()}

        </React.Fragment>
    }

    renderLog() {
        const {log} = this.state;
        if (log) {
            const {apiMessages, payrecords} = log;
            return <React.Fragment>
                <Row>
                    <Col md={6}>
                        <h2>API events</h2>
                        {History.renderAPIEventTable(apiMessages)}
                    </Col>
                    <Col md={6}>
                        <h2>Trade events</h2>
                        {History.renderTradeEventTable(payrecords)}
                    </Col>
                </Row>
            </React.Fragment>
        }

        return <p>Loading or down. Check <Link to={"/"}>home</Link> for microservice status.</p>

    }

    static renderAPIEventTable(data) {
        return <Table hover>
            <thead>
            <tr>
                <th>Username</th>
                <th>Autenticated</th>
                <th>Endpoint</th>
                <th>Timestamp</th>
            </tr>
            </thead>
            <tbody>

            {data.map(value => {
                const {username, authenticated, timestamp, apiEndpoint} = value;
                return <tr>
                    <td>{username}</td>
                    <td>{authenticated ? "true" : "false"}</td>
                    <td>{new Date(timestamp).toUTCString()}</td>
                    <td>{apiEndpoint}</td>

                </tr>
            })}
            </tbody>
        </Table>
    }

    static renderTradeEventTable(data) {
        return <Table hover>
            <thead>
            <tr>
                <th>Username</th>
                <th>Currency</th>
                <th>Timestamp</th>
                <th>Total</th>
                <th>Type</th>
            </tr>
            </thead>
            <tbody>


            {data.map(value => {
                const {email, currency, timestamp, total, transactionType} = value;
                return <tr>
                    <td>{email}</td>
                    <td>{currency}</td>
                    <td>{new Date(timestamp).toUTCString()}</td>
                    <td>{total}</td>
                    <td>{transactionType}</td>

                </tr>
            })}
            </tbody>
        </Table>
    }
}