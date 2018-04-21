import * as React from 'react';
import axios from "axios";
import {Link} from "react-router-dom";
import {historyURL} from "../ServiceURLS";
import {Table, Col, Row} from 'reactstrap';
import ReactTable from "react-table";
import 'react-table/react-table.css'

export class History extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            log: undefined
        };
    }

    componentDidMount() {
        axios.get(historyURL + "all").then(response => {
            console.log(response);
            this.setState({
                log: response.data
            })
        })
    }


    render() {
        return <React.Fragment>
            <h1>History</h1>
            {this.renderLog()}

        </React.Fragment>
    }

    renderLog() {
        const {log} = this.state;
        if (log) {
            const {apiMessages, payrecords} = log;
            return <React.Fragment>
                <Row>
                    <Col md={6}>
                        <h2>API events</h2>
                        {History.renderReactTable(APIEventColumns, apiMessages)}
                    </Col>
                    <Col md={6}>
                        <h2>Trade events</h2>
                        {History.renderReactTable(tradeEventColumns, payrecords)}
                    </Col>
                </Row>
            </React.Fragment>
        }

        return <p>Loading or down. Check <Link to={"/"}>home</Link> for microservice status.</p>

    }


    static renderReactTable(columns, data) {
        return <ReactTable filterable columns={columns} data={data} defaultPageSize={15} className="-striped -highlight"
                           defaultFilterMethod={(filter, row) => row[filter.id].startsWith(filter.value)}/>
    }
}

const APIEventColumns = [
    {
        Header: "Username",
        accessor: "username",
    },
    {
        Header: "Autenticated",
        accessor: "authenticated"
    },
    {
        Header: "Timestamp",
        accessor: "timestamp",
        Cell: props => <span className='number'>{new Date(props.value).toUTCString()}</span>
    },
    {
        Header: "Endpoint",
        accessor: "apiEndpoint"
    }
];

const tradeEventColumns = [
    {
        Header: "Username",
        accessor: "email"
    },
    {
        Header: "Currency",
        accessor: "currency"
    },
    {
        Header: "Timestamp",
        accessor: "timestamp",
        Cell: props => <span className='number'>{new Date(props.value).toUTCString()}</span>
    },
    {
        Header: "Total",
        accessor: "total"
    }, {
        Header: "Type",
        accessor: "transactionType"
    }
];