import * as React from 'react';
import {Form, Text} from 'react-form';
import { Button, Form as FormStyled, FormGroup, Label } from 'reactstrap';
import axios from "axios";
import {tradeURL} from "../ServiceURLS";


export class Transaction extends React.Component {

    constructor(props){
        super(props);
    }

    handleSubmit = (values, e, formapi) => {

        axios.post(tradeURL + "login", values, config).then((response) => {

            console.log("Params and client data set");
        }).catch(() => {
            formapi.setError("email", "Email already in use");
        });
    };

    render() {
        const {type, currency} = this.props;
        const textStyle = {className: "form-control"};
        return (
            <Form onSubmit={this.handleSubmit}>
                {
                    (formApi) => (
                        <FormStyled onSubmit={formApi.submitForm}>
                            <h3>{type} {currency}</h3>
                            <FormGroup>
                                <Label htmlFor="price">Price</Label>
                                <Text field="price" placeholder="Price" validate={validate} required {...textStyle}/>
                            </FormGroup>
                            <FormGroup>
                                <label htmlFor="amount">Amount</label>
                                <Text field="amount" type="amount" placeholder="amount" required {...textStyle}/>
                            </FormGroup>
                            <FormGroup>
                                <label htmlFor="password">Total</label>
                                <Text field="password" type="password" placeholder="Password" {...textStyle}/>
                            </FormGroup>
                            <Button color="primary" type="submit">Login</Button>
                            <div>{JSON.stringify(formApi.errors)}</div>
                        </FormStyled>
                    )
                }
            </Form>
        )
    }
}