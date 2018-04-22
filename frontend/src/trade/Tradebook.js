import * as React from 'react';
import {Row, Table, Col} from 'reactstrap';
import {MyTrades} from "./MyTrades";
import PropTypes from "prop-types";

export class Tradebook extends React.Component {

    render() {
        const {sell, buy } = this.props;
        const buyAggregated = Tradebook.aggregateTradeData(buy);
        const sellAggregated = Tradebook.aggregateTradeData(sell);

        return (
            <React.Fragment>
                <h3>Orders</h3>
                <Row>
                    <Col md={6}>
                        <h4>Buy</h4>
                        {Tradebook.getTable(buyAggregated)}
                    </Col>
                    <Col md={6}>
                        <h4>Sell</h4>
                        {Tradebook.getTable(sellAggregated)}
                    </Col>
                </Row>
            </React.Fragment>
        )

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
                const total = order.price * order.amount;
                return (
                    <tr key={i}>
                        <td>{order.price},-</td>
                        <td>{order.amount}</td>
                        <td>{total},-</td>
                    </tr>
                );
            })}
            </tbody>
        </Table>
    }

    static aggregateTradeData(data) {
        let result = [];
        for (let order of data.filter(value => value.remaningTotal > 0)) {
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

Tradebook.propTypes = {
    sell: PropTypes.array.isRequired,
    buy: PropTypes.array.isRequired,
};