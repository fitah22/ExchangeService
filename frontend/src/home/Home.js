import * as React from 'react';
import {Information} from "./Information";
import {ServiceUp} from "./ServiceUp";

export class Home extends React.Component {
    render() {
        return <React.Fragment>
            <Information/>
            <ServiceUp/>
        </React.Fragment>;
    }
}
