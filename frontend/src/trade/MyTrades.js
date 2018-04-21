import * as React from 'react';
import {TokenContext} from "../Contexts";
import {Col, Table} from 'reactstrap';
import PropTypes from "prop-types";

export class MyTrades extends React.Component {

    constructor(props) {
        super(props);
        this.onCancelOrder = this.onCancelOrder.bind(this);
    }


    render() {
        return <TokenContext.Consumer>
            {(value) => this.renderBasedOnValue(value.auth)}
        </TokenContext.Consumer>
    }

    renderBasedOnValue(auth) {
        if (auth) {
            const {buy, sell} = this.props;
            let myBuy = buy.filter(value => value.userID === auth.username);
            let mySell = sell.filter(value => value.userID === auth.username);
            return <React.Fragment>
                <Col md={6}>
                    <h4>Your buy orders</h4>
                    {this.renderMyOrderTable(myBuy)}
                </Col>
                <Col md={6}>
                    <h4>Your sell orders</h4>
                    {this.renderMyOrderTable(mySell)}
                </Col>
            </React.Fragment>

        }
        return "";
    }

    renderMyOrderTable(data, cancelOrder) {
        return <Table striped>
            <thead>
            <tr>
                <td>Price</td>
                <td>Remaining amount</td>
                <td>Traded amount</td>
                <td>Total amount</td>
                <td>Total price</td>
                <td/>
            </tr>
            </thead>
            <tbody>
            {data.map((order) => {
                return (
                    <tr key={order.id}>
                        <td>{order.price},-</td>
                        <td>{order.remainingAmount}</td>
                        <td>{order.tradedAmount}</td>
                        <td>{order.amount}</td>
                        <td>{order.total},-</td>
                        <td>
                            {order.remainingAmount > 0 &&
                            <a href="" onClick={(event) => this.onCancelOrder(event, order.id)}>
                                Cancel
                            </a>
                            }
                        </td>
                    </tr>
                );
            })}
            </tbody>
        </Table>
    }

    onCancelOrder(event, orderID) {
        event.preventDefault();
        this.props.onCancel(orderID);
    }

}

MyTrades.propTypes = {
    buy: PropTypes.array.isRequired,
    sell: PropTypes.array.isRequired,
    onCancel: PropTypes.func.isRequired,
};