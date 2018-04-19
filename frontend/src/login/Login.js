import * as React from 'react';
import {Form, Text} from 'react-form';
import {Button, Form as FormStyled, FormGroup, Label, Modal, ModalHeader, ModalBody} from 'reactstrap';
import {SyncLoader} from "react-spinners";
import axios from "axios";
import {loginURL} from "../ServiceURLS";


const validate = value => ({
    error: !value ? "Input must contain 'Hello World'" : null
});

export class Login extends React.Component {

    constructor(props) {
        super(props);
        this.setParams = this.props.setParams;
        this.setClientData = this.props.setClientData;
        this.state = {
          loading: false
        };
    }

    handleSubmit = (values, e, formapi) => {
        this.setState({
            loading:true
        });
        const config = {
            auth: {
                username: values.email,
                password: values.password
            }
        };
        axios.post(loginURL + "login", values, config).then((response) => {
            this.setParams(values.email, values.password);
            this.setClientData(response.data);
            console.log("Params and client data set");
        }).catch(() => {
            formapi.setError("email", "Email already in use");
        }).finally(() => {
            this.setState({
                loading:false
            });
        });
    };

    render() {
        const textStyle = {className: "form-control"};
        const {open, toggle} = this.props;
        debugger;
        return (
            <Modal isOpen={open} toggle={toggle}>
                <ModalHeader toggle={toggle}>
                    Login
                </ModalHeader>
                <ModalBody>
                    <Form onSubmit={this.handleSubmit}>
                        {
                            (formApi) => (
                                <FormStyled onSubmit={formApi.submitForm}>
                                    <FormGroup>
                                        <Label htmlFor="email">Email</Label>
                                        <Text field="email" placeholder="Email" validate={validate}
                                              required {...textStyle}/>
                                    </FormGroup>
                                    <FormGroup>
                                        <label htmlFor="password">Password</label>
                                        <Text field="password" type="password" placeholder="Password"
                                              required {...textStyle}/>
                                    </FormGroup>
                                    {this.state.loading && <Button color="primary" disabled={true}><SyncLoader size={10}/></Button>}
                                    {!this.state.loading && <Button color="primary" type="submit">Login</Button>}
                                    <Button color="secondary" className={"ml-1"} onClick={toggle}>Cancel</Button>
                                    <div>{JSON.stringify(formApi.errors)}</div>
                                </FormStyled>
                            )
                        }
                    </Form>
                </ModalBody>
            </Modal>
        )
    }
}