import * as React from 'react';
import {Form, Text} from 'react-form';
import {Button, Form as FormStyled, FormGroup, Label, Input, InputGroup, InputGroupAddon, Col} from 'reactstrap';
import axios from "axios";
import {tradeURL} from "../ServiceURLS";


export class Transaction extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            amount: "",
            price: "",
            total: "",
            currentMarketPrice: undefined
        };
    }

    componentDidMount(){
        const {currency, unit} = this.props;
        const apiUrl = `https://min-api.cryptocompare.com/data/price?fsym=${currency}&tsyms=${unit}`;
        axios.get(apiUrl).then(response => {
            if(response.status === 200){
                this.setState({
                    currentMarketPrice: response.data[unit]
                });
                console.log(`Current ${currency}-${unit} price: ${response.data[unit]}`)
            }
        });
    }

    handleSubmit = (values, e, formapi) => {

        axios.post(tradeURL, values).then((response) => {

            console.log("Trade OK");
        }).catch(() => {
            formapi.setError("error", "Something went wrong. Try again later.");
        });
    };

    render() {
        const {type, currency, unit} = this.props;
        const textStyle = {className: "form-control"};
        return (
            <Form onSubmit={this.handleSubmit}>
                {
                    (formApi) => (
                        <FormStyled onSubmit={formApi.submitForm}>
                            <h3>{type} {currency}</h3>
                            <FormGroup row>
                                <Label htmlFor="price" sm={2}>Price:</Label>
                                <Col sm={10}>
                                    <InputGroup>
                                        <Input defaultValue={this.state.price} placeholder="Price" min="0" type="number" field="price" step="0.01"/>
                                        <InputGroupAddon addonType="append">{unit}</InputGroupAddon>
                                    </InputGroup>
                                </Col>
                            </FormGroup>
                            <FormGroup row>

                                <Label htmlFor="amount" sm={2}>Amount:</Label>
                                <Col sm={10}>
                                <InputGroup>
                                    <Input placeholder="Amount" type="number" min="0" step="0.01"/>
                                    <InputGroupAddon addonType="append">{currency}</InputGroupAddon>
                                </InputGroup>
                                </Col>
                            </FormGroup>
                            <FormGroup row>
                                <Label htmlFor="total" sm={2}>Total: </Label>
                                <Col sm={10}>
                                <InputGroup>
                                    <Input defaultValue={this.state.total} type="number" field="total" step="1"/>
                                    <InputGroupAddon addonType="append">{unit}</InputGroupAddon>
                                </InputGroup>
                                </Col>
                            </FormGroup>
                            <Input hidden type="text" value={type} readOnly/>
                            <Button color="primary" type="submit" block>{type} {currency}</Button>
                            <div>{JSON.stringify(formApi.errors)}</div>
                        </FormStyled>
                    )
                }
            </Form>
        )
    }
}