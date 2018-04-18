import * as React from 'react';
import axios from "axios";
import {historyURL} from "../ServiceURLS";

export class History extends React.Component {

    constructor(props){
        super(props);
        this.state = {

        };
    }

    componentDidMount() {
        axios.get(historyURL + "/")
    }



    render(){
        return <p>Hello - </p>
    }

}