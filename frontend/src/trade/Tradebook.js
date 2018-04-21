import * as React from 'react';
import {tradeURL} from "../ServiceURLS";
import {Table, Col} from 'reactstrap';
import axios from "axios/index";

export class Tradebook extends React.Component {

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

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(prevProps.market !== this.props.market) {
            this.updateMarketBook();
        }

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

            this.setState({
                sell: sell.data,
                buy: buy.data,
                loading: false,
                loadingFail: false
            });
        }).catch(() => {
            this.setState({
                loading: false,
                loadingFail: true,
            });
        });

    }

    render() {
        console.log("Tradebook rerender");
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
                        {Tradebook.getTable(buy)}
                    </Col>
                    <Col md={6}>
                        <h4>Sell</h4>
                        {Tradebook.getTable(sell)}
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