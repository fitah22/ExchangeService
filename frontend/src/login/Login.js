import * as React from 'react';
import {Form, Text} from 'react-form';
import axios from "axios";
import {loginURL} from "../ServiceURLS";


const validate = value => ({
    error: !value ? "Input must contain 'Hello World'" : null
});

export class Login extends React.Component {

    constructor(props){
        super(props);
        this.setParams = this.props.setParams;
    }

    handleSubmit = (values, e, formapi) => {
        const config = {
            auth: {
                username: values.email,
                password: values.password
            }
        };
        axios.get(loginURL + "login", config).then(() => {
            this.setParams(values.email, values.password);
        }).catch(() => {
            formapi.setError("email", "Email already in use");
        });
    };

    render() {
        return (
            <Form onSubmit={this.handleSubmit}>
                {
                    (formApi) => (
                        <form onSubmit={formApi.submitForm}>
                            <label htmlFor="email">Email</label>
                            <Text field="email" placeholder="Email" validate={validate}/>
                            <label htmlFor="password">Password</label>
                            <Text field="password" type="password"/>
                            <button type="submit" className={"btn btn-md"}>Login</button>
                            <div>{JSON.stringify(formApi.errors)}</div>
                        </form>
                    )
                }
            </Form>
        )
    }
}