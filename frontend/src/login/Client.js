import * as React from 'react';
import {Account} from "./Account";
import {Button, Alert} from 'reactstrap';

export class Client extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            edit: false
        };
    }

    render() {
        const {data} = this.props;

        if (data) {
            return (
                <div>
                    <h3>Welcome</h3>
                    <p>Email: {data.email}</p>
                    <p>Address: {data.address}</p>
                    <Account data={data.accounts}/>
                </div>
            )
        }
        return <div></div>;
    }


}