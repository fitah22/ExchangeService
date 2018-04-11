import * as React from 'react';
import {Form, Text, Checkbox} from 'react-form';
import axios from "axios";
import {loginURL } from "../ServiceURLS";


const validate = value => ({
    error: !value ? "Input must contain 'Hello World'" : null
});

export class Signup extends React.Component {

    constructor(props){
        super(props);
        this.setParams = this.props.setParams;
    }


    handleSubmit = (values, e, formapi) => {
        axios.post(loginURL+"signup", values).then(response => {
            this.setParams(values.email, values.password);
        }).catch(error => {
            formapi.setError("email","Email already in use");
        });
    };

    render() {
        return (
            <Form onSubmit={this.handleSubmit}>
                {(form) => (
                    <form onSubmit={form.submitForm}>
                        <label htmlFor="email">Email</label>
                        <Text field="email" placeholder="Email" validate={validate} required/>
                        {form.errors && form.errors.email}
                        <label htmlFor="password">Password</label>
                        <Text field="password" type="password" required/>
                        {form.errors && form.errors.password}
                        <label htmlFor="address">Address</label>
                        <Text field="address" placeholder="Address" required/>
                        {form.errors && form.errors.address}
                        <Checkbox field="agreesToTerms" required/>
                        <label htmlFor="agreesToTerms">Terms and conditions</label>
                        <button type="submit">Submit</button>
                    </form>
                )
                }
            </Form>
        )
    }

}