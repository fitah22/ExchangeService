import * as React from 'react';
import {Form} from 'react-form';
import {Button, Form as FormStyled, FormGroup, Label, Input, InputGroup, InputGroupAddon, Col} from 'reactstrap';
import {TokenContext} from "../Contexts";
import axios from "axios";
import {tradeURL} from "../ServiceURLS";


export class Transaction extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            amount: "",
            price: "",
            currentMarketPrice: undefined
        };
    }

    handleSubmit = (values, e, formapi) => {

        axios.post(tradeURL, values).then((response) => {
            console.log("Trade OK");
        }).catch(() => {
            formapi.setError("error", "Something went wrong. Try again later.");
        });
    };

    handleNumberChange(event) {
        const {value, name} = event.currentTarget;
        this.setState({
            [name]: Number(value)
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
        const {type, currency, unit} = this.props;
        const {amount, price } = this.state;
        let disabled = context.auth === undefined;
        let total = amount*price;
        let buttontext = context.auth === undefined ? "Login to trade" : `${type} ${currency}`;

        return <Form onSubmit={this.handleSubmit}>
            {
                (formApi) => (
                    <FormStyled onSubmit={formApi.submitForm}>
                        <h3>{type} {currency}</h3>
                        <FormGroup row>
                            <Label htmlFor="price" sm={2}>Price:</Label>
                            <Col sm={10}>
                                <InputGroup>
                                    <Input defaultValue={this.state.price} placeholder="Price" min="0" type="number"
                                           name="price" field="price" step="0.01" onChange={(event) => this.handleNumberChange(event)}/>
                                    <InputGroupAddon addonType="append">{unit}</InputGroupAddon>
                                </InputGroup>
                            </Col>
                        </FormGroup>
                        <FormGroup row>

                            <Label htmlFor="amount" sm={2}>Amount:</Label>
                            <Col sm={10}>
                                <InputGroup>
                                    <Input placeholder="Amount" type="number" min="0" name="amount" field="amount" step="0.01" onChange={(event) => this.handleNumberChange(event)}/>
                                    <InputGroupAddon addonType="append">{currency}</InputGroupAddon>
                                </InputGroup>
                            </Col>
                        </FormGroup>
                        <FormGroup row>
                            <Label htmlFor="total" sm={2}>Total: </Label>
                            <Col sm={10}>
                                <InputGroup>
                                    <Input value={total} type="number" field="total" name="total" readOnly/>
                                    <InputGroupAddon addonType="append">{unit}</InputGroupAddon>
                                </InputGroup>
                            </Col>
                        </FormGroup>
                        <Input hidden type="text" value={type} readOnly/>
                        <div>{JSON.stringify(formApi.errors)}</div>
                        <Button color="primary" type="submit" block  disabled={disabled}>{buttontext}</Button>
                    </FormStyled>
                )
            }
        </Form>
    }
}