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
            let claimedRewardText = data.claimedReward ? "Yes" : "No";
            return (
                <div>
                    <h3>Welcome</h3>
                    <p>Email: {data.email}</p>
                    <p>Address: {data.address}</p>
                    <p>Claimed reward: {claimedRewardText}</p>
                    {!data.clamiedReward && this.claimRewardBanner()}
                    <Account data={data.accounts}/>
                </div>
            )
        }
        return <div></div>;
    }


    claimRewardBanner = () => {
        return <React.Fragment>
            <Alert color="primary">
                All users gets a 100$ and 100 BTC bonus as thank you for using your service.
            </Alert>
            <Button color="primary" className={"md-2"}>Claim your reward</Button>
        </React.Fragment>
    }

}