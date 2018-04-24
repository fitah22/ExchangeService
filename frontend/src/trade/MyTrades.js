import * as React from 'react';
import {TokenContext} from "../Contexts";
import {Row, Col, Table, Collapse, Button} from 'reactstrap';
import PropTypes from "prop-types";
import {tradeURL} from "../ServiceURLS";
import axios from "axios/index";

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
            {(value) => this.renderBasedOnValue(value.auth, value.updateWholeClientData)}
        </TokenContext.Consumer>
    }

    renderBasedOnValue(auth, updateClientData) {
        if (auth) {
            const {buy, sell} = this.props;
            const {open} = this.state;
            let myBuy = buy.filter(value => value.userID === auth.username);
            let mySell = sell.filter(value => value.userID === auth.username);
            let text = open ? "Hide orders" : "Show orders";
            return <React.Fragment>
                <h2>Your orders</h2>
                <Button color="info" onClick={this.toggleCollapse} className={"ml-2 mb-2"}>{text}</Button>
                <Collapse isOpen={open}>
                    <Col md={12}>
                        <Row>
                            <Col md={12} lg={12} xl={6}>
                                <h4>Your buy orders</h4>
                                {this.renderMyOrderTable(myBuy, updateClientData)}
                            </Col>
                            <Col md={12} lg={12} xl={6}>
                                <h4>Your sell orders</h4>
                                {this.renderMyOrderTable(mySell, updateClientData)}
                            </Col>
                        </Row>
                    </Col>
                </Collapse>
            </React.Fragment>

        }
        return "";
    }

    renderMyOrderTable(data, updateClientData) {
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
                            <a href="" onClick={(event) => this.onCancelOrder(event, order.id, updateClientData)}>
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


    onCancelOrder(event, orderID, updateClientData) {
        event.preventDefault();
        const {onUpdate} = this.props;
        axios.post(tradeURL + "cancel", {id: orderID})
            .then(response => {
                updateClientData();
                onUpdate();
            });
    }

}

MyTrades.propTypes = {
    buy: PropTypes.array.isRequired,
    sell: PropTypes.array.isRequired,
    onUpdate: PropTypes.func.isRequired,
};