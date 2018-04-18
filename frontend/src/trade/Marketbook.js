import * as React from 'react';
import {tradeURL} from "../ServiceURLS";
import {Table} from 'reactstrap';
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
            return <p>Loading data...</p>
        } else if (loadingFail) {
            return <p>Something went wrong. Please try again.</p>
        } else {
            return (
                <React.Fragment>
                    <h3>Buy</h3>
                    {Marketbook.getTable(buy)}

                    <h3>Sell</h3>
                    {Marketbook.getTable(sell)}
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