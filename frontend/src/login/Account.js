import * as React from 'react';
import {Table} from 'reactstrap';
import {UnclaimedAccounts} from "./UnclaimedAccounts";


export class Account extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            edit: false
        };
    }

    render() {
        const {data} = this.props;
        //const btnAttr = {className: "mr-2 mt-2"};
        if (data) {
            return (
                <div>
                    <h3>Accounts</h3>
                    <p>Nr of accounts: {data.length}</p>
                    <Table hover>
                        <thead>
                        <tr>
                            <th>Currency</th>
                            <th>Balance</th>
                            {/* <th/> */}
                        </tr>
                        </thead>
                        <tbody>
                        {data.map((acc, id) => {
                            return <tr key={id}>
                                <td>{acc.currency}</td>
                                <td>{acc.balance}</td>
                                {/*
                                <td>

                                    <Button {...btnAttr} color="primary">Deposit</Button>
                                    <Button {...btnAttr} color="primary">Withdraw</Button>

                                </td>
*/
                                }

                            </tr>
                        })}
                        <UnclaimedAccounts/>
                        </tbody>
                    </Table>
                </div>
            )
        }
        return <div></div>;
    }
}