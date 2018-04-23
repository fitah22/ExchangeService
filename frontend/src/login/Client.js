import * as React from 'react';
import {Account} from "./Account";
import {Button, Input, Form, FormGroup, Label, Col, UncontrolledAlert} from "reactstrap";
import axios from "axios";
import {loginURL} from "../ServiceURLS";

export class Client extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            newPassword: "",
            oldPassword: "",
            passwordMessage: undefined,
            changeAddress: false,
            newAddress: "",
            addressMessage: undefined,
        };

        this.onPasswordChange = this.onPasswordChange.bind(this);
        this.patchChangePassword = this.patchChangePassword.bind(this);
        this.onAddressChange = this.onAddressChange.bind(this);
        this.patchChangeAddress = this.patchChangeAddress.bind(this);
    }

    render() {
        const {data} = this.props;

        if (data) {
            return (
                <div>
                    <h3>Welcome</h3>
                    <p>Email: {data.email}</p>
                    {this.renderAddressField()}
                    <h4>Change password</h4>
                    {this.renderPasswordFields()}
                    <Account data={data.accounts}/>
                </div>
            )
        }
        return <div>
            <h1>User</h1>
            <p>Service may be done.</p>
        </div>;
    }

    renderPasswordFields() {
        const {passwordMessage} = this.state;
        return <Form onSubmit={this.patchChangePassword}>
            <FormGroup row>
                <Label for="oldpass" sm={2}>Old password</Label>
                <Col sm={10}>
                    <Input type="password" id="oldPassword" onChange={this.onPasswordChange} required/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Label for="newpass" sm={2}>New password</Label>
                <Col sm={10}>
                    <Input type="password" name="password" id="newPassword" onChange={this.onPasswordChange} required/>
                </Col>
            </FormGroup>
            {passwordMessage && <UncontrolledAlert color="info">{passwordMessage}</UncontrolledAlert>}
            <Button color={"info"}>Change password</Button>
        </Form>
    }

    onPasswordChange(event) {
        const {id, value} = event.currentTarget;
        this.setState({
            [id]: value
        });
    }

    patchChangePassword(event) {
        event.preventDefault();
        const {oldPassword, newPassword} = this.state;
        const {auth, onUpdatePassword} = this.props;

        if(oldPassword !== auth.password) {
            this.setState({
                passwordMessage: "Old password is incorrect. Try again.",
            });
            return;
        }
        axios.patch(loginURL + "user/password", {value: newPassword}, {auth}).then(response => {
            onUpdatePassword(newPassword);
            this.setState({
                passwordMessage: "Password changed",
            });
        }).catch(e => console.log(e));
    }


    renderAddressField() {
        const {data} = this.props;
        const {changeAddress, newAddress, addressMessage} = this.state;
        if (changeAddress) {
            return <React.Fragment>
                <Form onSubmit={this.patchChangeAddress}>
                    <FormGroup row>
                        <Label for="address" sm={2}>New address</Label>
                        <Col sm={10}>
                            <Input value={newAddress} type="address" name="address"
                                   id="address" onChange={this.onAddressChange} required/>
                        </Col>
                    </FormGroup>
                    <Button className={"mr-2"} color={"info"}>Save</Button>
                    <Button onClick={() => this.setAddressChange(false)}>Cancel</Button>
                </Form>
            </React.Fragment>
        }
        return <React.Fragment>
            <p>Address: {data.address}</p>
            {addressMessage && <UncontrolledAlert color="info">{addressMessage}</UncontrolledAlert>}
            <Button color={"info"} onClick={() => this.setAddressChange(true)}>Change address</Button>
        </React.Fragment>
    }

    setAddressChange(value) {
        this.setState({
            changeAddress: value
        });
    }

    onAddressChange(event) {
        this.setState({
            newAddress: event.currentTarget.value
        });
    }

    patchChangeAddress(event) {
        event.preventDefault();
        const {newAddress} = this.state;
        const {auth, onUpdateAddress} = this.props;
        axios.patch(loginURL + "user/address", {value: newAddress}, {auth}).then(response => {
            onUpdateAddress(newAddress);
            this.setState({
                addressMessage: "Address changed",
                changeAddress: false,
            });
        }).catch(e => console.log(e));
    }
}