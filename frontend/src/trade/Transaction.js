import * as React from 'react';
import {Form} from 'react-form';
import {
    Button,
    Form as FormStyled,
    FormGroup,
    Label,
    Input,
    InputGroup,
    InputGroupAddon,
    Col,
    Alert
} from 'reactstrap';
import {TokenContext} from "../Contexts";
import axios from "axios";
import {tradeURL} from "../ServiceURLS";


export class Transaction extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            amount: "",
            price: "",
            currentMarketPrice: undefined,
            message: undefined,
            messageVisible: false
        };
        this.onDismissAlert = this.onDismissAlert.bind(this);
    }

    onDismissAlert() {
        this.setState({messageVisible: false});
    }

    handleSubmit = (auth, updateWholeClientData, onUpdate, formapi) => {
        if (auth === undefined) return;
        const {amount, price} = this.state;
        const {type, main, secondary} = this.props;
        const order = {
            username: auth.username,
            password: auth.password,
            order: {
                userID: auth.username,
                amount,
                price,
                market: `${main}_${secondary}`,
                transactionType: type.toUpperCase()
            }
        };
        axios.post(tradeURL, order).then((response) => {
            this.setState({
                message: "Trade OK.",
                messageVisible: true
            });
            updateWholeClientData();
            onUpdate();
        }).catch(() => {
            this.setState({
                message: "Something went wrong. Try again later.",
                messageVisible: true
            });

        });
    };

    handleNumberChange(event) {
        const {value, name} = event.currentTarget;
        this.setState({
            [name]: Number(value),
            messageVisible: false,
        });
    }

    render() {
        return (
            <TokenContext.Consumer>
                {value => this.renderBasedOnValue(value)}
            </TokenContext.Consumer>
        )
    }

    renderBasedOnValue(context) {
        const {type, main, secondary, onUpdate} = this.props;
        const {amount, price, message, messageVisible} = this.state;
        const {auth, updateWholeClientData, client} = context;

        let currentBalance = <p></p>;
        if (client) {
            let curr = type.toUpperCase() === "BUY" ? secondary : main;
            let account = client.accounts.find(acc => acc.currency === curr);
            if (account) {
                currentBalance = <p>Your current balance of {curr}: {account.balance},-</p>;
            } else {
                currentBalance = <p>You do not have an {curr} account. Go to the user page to make an account.</p>;
            }
        }

        let disabled = auth === undefined;
        let total = amount * price;
        let buttontext = auth === undefined ? "Login to trade" : `${type} ${main}`;

        return <Form
            onSubmit={(values, e, formApi) => this.handleSubmit(auth, updateWholeClientData, onUpdate, formApi)}>
            {
                (formApi) => (
                    <FormStyled onSubmit={formApi.submitForm}>
                        <h3>{type} {main}</h3>
                        {currentBalance}
                        <FormGroup row>
                            <Label htmlFor="price" sm={2}>Price:</Label>
                            <Col sm={10}>
                                <InputGroup>
                                    <Input defaultValue={this.state.price} placeholder="Price" min="0" type="number"
                                           name="price" field="price" step="0.01"
                                           onChange={(event) => this.handleNumberChange(event)}/>
                                    <InputGroupAddon addonType="append">{secondary}</InputGroupAddon>
                                </InputGroup>
                            </Col>
                        </FormGroup>
                        <FormGroup row>

                            <Label htmlFor="amount" sm={2}>Amount:</Label>
                            <Col sm={10}>
                                <InputGroup>
                                    <Input placeholder="Amount" type="number" min="0" name="amount" field="amount"
                                           step="0.01" onChange={(event) => this.handleNumberChange(event)}/>
                                    <InputGroupAddon addonType="append">{main}</InputGroupAddon>
                                </InputGroup>
                            </Col>
                        </FormGroup>
                        <FormGroup row>
                            <Label htmlFor="total" sm={2}>Total: </Label>
                            <Col sm={10}>
                                <InputGroup>
                                    <Input value={total} type="number" field="total" name="total" readOnly/>
                                    <InputGroupAddon addonType="append">{secondary}</InputGroupAddon>
                                </InputGroup>
                            </Col>
                        </FormGroup>
                        <Input hidden type="text" value={type} readOnly/>

                        <Alert color="info" isOpen={messageVisible} toggle={this.onDismissAlert}>
                            {message}
                        </Alert>

                        <Button color="primary" type="submit" block disabled={disabled}>{buttontext}</Button>


                    </FormStyled>
                )
            }
        </Form>
    }
}