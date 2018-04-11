import * as React from 'react';
import {TokenContext} from "../Contexts";
import {Link} from "react-router-dom";
import {Transaction} from "./Transaction";

export class TradeEngine extends React.Component {

    render() {
        return <TokenContext.Consumer>
            {value => this.renderBasedOnValue(value)}
        </TokenContext.Consumer>
    }

    renderBasedOnValue(context) {
        if (context.auth === undefined) {
            return <React.Fragment>
                <h3>You need to log in first</h3>
                <Link to={"/user"}>Login now</Link>
            </React.Fragment>
        }

        return <React.Fragment>
            <div className={"col-md-6"}>
                <Transaction type={"Buy"} currency={"BTC"} unit={"USDT"}/>
            </div>
            <div className={"col-md-6"}>
                <Transaction type={"Sell"} currency={"BTC"} unit={"USDT"}/>
            </div>
        </React.Fragment>;
    }
}