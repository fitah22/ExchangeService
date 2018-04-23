import * as React from 'react';
import {TokenContext} from "../Contexts";
import axios from "axios";
import {loginURL} from "../ServiceURLS";

export class UnclaimedAccounts extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currencies: []
        }
    }

    componentDidMount() {
        axios.get(loginURL + "currencies").then(response => {
            this.setState({
                currencies: response.data === null ? [] : response.data
            })
        });
    }

    render() {
        return <TokenContext.Consumer>
            {value => this.renderBasedOnValue(value)}
        </TokenContext.Consumer>

    }

    renderBasedOnValue(context) {
        const {client, auth, setClientData} = context;
        const {currencies} = this.state;
        const client_account = client.accounts.map(value => value.currency);
        const unclaimed = currencies.filter(value => client_account.indexOf(value) < 0);

        return <React.Fragment>
            <tr>
                <th colSpan={3}>Currencies where you do not have account:</th>
            </tr>

            {unclaimed.map(currency => {
                return <tr key={currency}>
                    <td>{currency}</td>
                    <td><a href="" onClick={() => this.createNewAccountAndClaim(currency, auth, setClientData)}>Create account now</a>, and get 50 {currency} as bonus.</td>
                </tr>

            })
            }
        </React.Fragment>
    }


    createNewAccountAndClaim(curr, auth, setClientData) {

        //Use login since it returns this user
        axios.post(loginURL + "user/newAccountAndClaim", {value: curr}, {auth}).then((response) => {
            setClientData(response.data);
            console.log(`Account {curr} made and amount claimed. Client data updated`);
        });
    }

}