import * as React from 'react';
import {TokenContext} from "../Contexts";
import {Row, Col, Table, Collapse, Button} from 'reactstrap';
import PropTypes from "prop-types";

export class MyTrades extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            open: true,
        };

        this.toggleCollapse = this.toggleCollapse.bind(this);
        this.onCancelOrder = this.onCancelOrder.bind(this);
    }

    toggleCollapse() {
        this.setState({
            open: !this.state.open
        });
    }

    render() {
        return <TokenContext.Consumer>
            {(value) => this.renderBasedOnValue(value.auth)}
        </TokenContext.Consumer>
    }

    renderBasedOnValue(auth) {
        if (auth) {
            const {buy, sell} = this.props;
            const {open} = this.state;
            let myBuy = buy.filter(value => value.userID === auth.username);
            let mySell = sell.filter(value => value.userID === auth.username);
            let text = open ? "Hide my orders" : "Show my orders";
            return <React.Fragment>
                <Button color="info" onClick={this.toggleCollapse} className={"ml-2 mb-2"}>{text}</Button>
                <Collapse isOpen={open}>
                    <Col md={12}>
                        <Row>
                            <Col md={6}>
                                <h4>Your buy orders</h4>
                                {this.renderMyOrderTable(myBuy)}
                            </Col>
                            <Col md={6}>
                                <h4>Your sell orders</h4>
                                {this.renderMyOrderTable(mySell)}
                            </Col>
                        </Row>
                    </Col>
                </Collapse>
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