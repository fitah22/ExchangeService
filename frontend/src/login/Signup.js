import * as React from 'react';
import {Form, Text, Checkbox} from 'react-form';
import {loginInstance} from "../axiosInstances";


const validate = value => ({
    error: !value ? "Input must contain 'Hello World'" : null
});

export class Signup extends React.Component {

    handleSubmit = (values, e, formapi) => {
        loginInstance.post("signup", values).then(response => {
            console.log(values);
            let token = btoa(values.email + ":" + values.password);
            console.log("basic token: " + token);
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