import * as React from 'react';
import { Form, Text, Checkbox } from 'react-form';



const validate = value => ({
    error: !value || !/Hello World/.test(value) ? "Input must contain 'Hello World'" : null,
    warning: !value || !/^Hello World$/.test(value) ? "Input should equal just 'Hello World'" : null,
    success: value && /Hello World/.test(value) ? "Thanks for entering 'Hello World'!" : null
})

export class Login extends React.Component  {

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