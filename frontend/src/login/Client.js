import * as React from 'react';
import {Account} from "./Account";
import {Button, Input, Form, FormGroup, Label, Col} from "reactstrap";
import axios from "axios";
import {loginURL} from "../ServiceURLS";

export class Client extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            newPassword: ""
        };

        this.onPasswordChange = this.onPasswordChange.bind(this);
        this.patchChangePassword = this.patchChangePassword.bind(this);
    }

    render() {
        const {data} = this.props;

        if (data) {
            return (
                <div>
                    <h3>Welcome</h3>
                    <p>Email: {data.email}</p>
                    <p>Address: {data.address}</p>
                    {this.renderPasswordFields()}
                    <Button>Address</Button>
                    <Account data={data.accounts}/>

                </div>
            )
        }
        return <div></div>;
    }

    renderPasswordFields() {
        return <Form>
            <FormGroup row>
                <Label for="oldpass" sm={2}>Old password</Label>
                <Col sm={10}>
                    <Input type="password" id="oldpass"/>
                </Col>
            </FormGroup>
            <FormGroup row>
                <Label for="newpass" sm={2}>New password</Label>
                <Col sm={10}>
                    <Input type="password" name="password" id="newpass" onChange={this.onPasswordChange}/>
                </Col>
            </FormGroup>
            <Button onClick={this.patchChangePassword}>Change password</Button>
        </Form>
    }

    onPasswordChange(event) {
        this.setState({
            newPassword: event.currentTarget.value
        });
    }

    patchChangePassword() {
        const {newPassword} = this.state;
        const {auth, onUpdatePassword} = this.props;
        debugger;
        axios.patch(loginURL + "user/password", {password: newPassword}, {auth}).then(response => {
            debugger;
            onUpdatePassword(newPassword);
        }).catch(e => console.log(e));
    }


}