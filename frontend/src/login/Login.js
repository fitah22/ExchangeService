import * as React from 'react';
import { Form, Text, Checkbox } from 'react-form';
import {loginInstance, setAuthToken} from "../axiosInstances";



const validate = value => ({
    error: !value ? "Input must contain 'Hello World'" : null
});

export class Login extends React.Component  {

    handleSubmit = (values, e, formapi) => {
        loginInstance.post("login", values).then(response => {
            let token = btoa(values.email + ":" + values.password);
            setAuthToken(token);
        }).catch(error => {
            formapi.setError("email","Email already in use");
        });
    };

    render(){
        return (
            <Form render={
                (formApi) => (
                    <form onSubmit={formApi.submitForm}>
                        <label htmlFor="email">Email</label>
                        <Text field="email" placeholder="Email" validate={validate}/>
                        <label htmlFor="password">Password</label>
                        <Text field="password"  type="password" />
                        <button type="submit">Login</button>
                        <div>{JSON.stringify(formApi.errors)}</div>
                    </form>
                )
            }/>
        )
    }
}