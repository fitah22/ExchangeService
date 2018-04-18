import * as React from 'react';
import {tradeURL} from "../ServiceURLS";
import {Table, Col} from 'reactstrap';
import axios from "axios/index";

export class Marketbook extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            loadingFail: true,
            buy: [],
            sell: [],
        };
    }

    componentDidMount() {
        this.updateMarketBook()
    }

    updateMarketBook() {
        const {market} = this.props;
        this.setState({
            loading: true
        });

        axios.all([
            axios.get(tradeURL + market.toUpperCase() + "/SELL"),
            axios.get(tradeURL + market.toUpperCase() + "/BUY")
        ]).then(([sell, buy]) => {
            debugger;
            this.setState({
                sell: sell.data,
                buy: buy.data,
                loading: false,
                loadingFail: false
            });
        }).catch((e) => {
            debugger;
            this.setState({
                loading: false,
                loadingFail: true,
            });
        });

    }

    render() {
        const {loading, loadingFail, sell, buy} = this.state;
        if (loading) {
            return <Col md={12}><p>Loading market book...</p></Col>
        } else if (loadingFail) {
            return <Col md={12}><p>Something went wrong with loading market book. Please try again.</p></Col>
        } else {
            return (
                <React.Fragment>
                    <Col md={6}>
                        <h4>Buy</h4>
                        {Marketbook.getTable(buy)}
                    </Col>
                    <Col md={6}>
                        <h4>Sell</h4>
                        {Marketbook.getTable(sell)}
                    </Col>
                </React.Fragment>
            )
        }
    }

    static getTable(data) {
        return <Table striped>
            <thead>
            <tr>
                <td>Price</td>
                <td>Amount</td>
                <td>Total</td>
            </tr>
            </thead>
            <tbody>
            {data.map((order, i) => {
                return (
                    <tr key={i}>
                        <td>{order.price}</td>
                        <td>{order.remainingAmount}</td>
                        <td>{order.remaningTotal}</td>
                    </tr>
                );
            })}
            </tbody>
        </Table>
    }
}