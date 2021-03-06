import * as React from 'react';
import {Form, Text, Checkbox} from 'react-form';
import axios from "axios";
import {SyncLoader} from "react-spinners";
import {loginURL} from "../ServiceURLS";
import {Button, Form as FormStyled, FormGroup, Label, Modal, ModalHeader, ModalBody} from 'reactstrap';

//https://stackoverflow.com/a/18964976
const regexFilter = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;

const validateEmail = value => ({
    error: !regexFilter.test(value) ? "Email format is not valid." : null
});

const validateNotEmpty = (value = "") => ({
    error: value.trim().length === 0 ? "Illegal value. Only whitespace is not allowed." : null
});


export class Signup extends React.Component {

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
            loading: true
        });
        axios.post(loginURL + "signup", values).then(response => {
            this.setParams(values.email, values.password);
            this.setClientData(response.data);
        }).catch(error => {
            formapi.setError("error", "Email already in use, or the service may be done.");
        }).finally(() => {
            this.setState({
                loading: false
            });
        });
    };

    render() {
        const textStyle = {className: "form-control"};
        const {open, toggle} = this.props;
        return (
            <Modal isOpen={open} toggle={toggle}>
                <ModalHeader toggle={toggle}>
                    Sign up
                </ModalHeader>
                <ModalBody>
                    <Form onSubmit={this.handleSubmit}>
                        {(form) => (
                            <FormStyled onSubmit={form.submitForm}>
                                <FormGroup>
                                    <Label htmlFor="email">Email</Label>
                                    <Text field="email" placeholder="Email" validate={validateEmail}
                                          required {...textStyle}/>
                                </FormGroup>
                                <FormGroup>
                                    <Label htmlFor="password">Password</Label>
                                    <Text field="password" type="password" placeholder="Password"
                                          required {...textStyle} validate={validateNotEmpty}/>
                                    {form.errors && form.errors.password}
                                </FormGroup>
                                <FormGroup>
                                    <Label htmlFor="address">Address</Label>
                                    <Text field="address" placeholder="Address" required {...textStyle}
                                          validate={validateNotEmpty}/>
                                    {form.errors && form.errors.address}
                                </FormGroup>
                                <FormGroup>
                                    <Checkbox field="agreesToTerms" required/>
                                    <label htmlFor="agreesToTerms">Terms and conditions</label>
                                </FormGroup>
                                {this.state.loading &&
                                <Button color="primary" disabled={true}><SyncLoader size={10}/></Button>}
                                {!this.state.loading && <Button color="primary" type="submit">Sign up</Button>}
                                <Button color="secondary" className={"ml-1"} onClick={toggle}>Cancel</Button>

                                <p>{form.errors && form.errors.email}</p>
                            </FormStyled>
                        )
                        }
                    </Form>
                </ModalBody>
            </Modal>
        )
    }

}