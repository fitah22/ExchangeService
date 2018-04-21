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
        const {loading, loadingFail, sell, buy} = this.state;
        const buyAggregated = Tradebook.aggregateTradeData(buy);
        const sellAggregated = Tradebook.aggregateTradeData(sell);

        if (loading) {
            return <Col md={12}><p>Loading market book...</p></Col>
        } else if (loadingFail) {
            return <Col md={12}><p>Something went wrong with loading market book. Please try again.</p></Col>
        } else {
            return (
                <React.Fragment>
                    <Col md={6}>
                        <h4>Buy</h4>
                        {Tradebook.getTable(buyAggregated)}
                    </Col>
                    <Col md={6}>
                        <h4>Sell</h4>
                        {Tradebook.getTable(sellAggregated)}
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
                debugger;
                const total = order.price * order.amount;
                return (
                    <tr key={i}>
                        <td>{order.price}</td>
                        <td>{order.amount}</td>
                        <td>{total}</td>
                    </tr>
                );
            })}
            </tbody>
        </Table>
    }

    static aggregateTradeData(data) {
        let result = [];
        for(let order of data.filter(value => value.remaningTotal > 0)) {
            let obj = result.find(value => value.price === order.price);
            if (obj) {
                obj.amount += order.remainingAmount;
            } else {
                obj = {
                    price: order.price,
                    amount: order.remainingAmount,
                };
                result.push(obj);
            }
        }
        return result;
    }
}