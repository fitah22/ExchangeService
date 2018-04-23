import * as React from 'react';
import axios from "axios";
import {Link} from "react-router-dom";
import {historyURL} from "../ServiceURLS";
import {Col, Row} from 'reactstrap';
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
            this.setState({
                log: response.data
            })
        })
    }


    render() {
        const {email} = this.props;
        let title =<h1>History</h1>;
        if(email) {
            title = <h3>History</h3>
        }

        return <React.Fragment>
            {title}
            {this.renderLog()}

        </React.Fragment>
    }

    renderLog() {
        const {log} = this.state;
        if (log) {
            let {apiMessages, payrecords} = log;
            let APITitle = <h2>API events</h2>;
            let tradeTitle = <h2>Trade events</h2>;
            const {email} = this.props;
            if(email) {
                APITitle= <h4>User API overview</h4>;
                tradeTitle = <h4>Buy/sell history</h4>;
                apiMessages = apiMessages.filter(value => value.username === email);
                payrecords = payrecords.filter(value => value.username === email);
            }

            return <React.Fragment>
                <Row>
                    <Col md={6}>
                        {APITitle}
                        {History.renderReactTable(APIEventColumns, apiMessages)}
                    </Col>
                    <Col md={6}>
                        {tradeTitle}
                        {History.renderReactTable(tradeEventColumns, payrecords)}
                    </Col>
                </Row>
            </React.Fragment>
        }

        return <p>Loading or down. Check <Link to={"/"}>home</Link> for microservice status.</p>

    }


    static renderReactTable(columns, data) {
        return <ReactTable filterable columns={columns} data={data} defaultPageSize={10} className="-striped -highlight"
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
        accessor: "authenticated",
        Cell: props => <span>{props.value ? "true" : "false"}</span>,
        filterMethod: (filter, row) => {
            if (filter.value === "all") {
                return true;
            }
            if (filter.value === "false") {
                return !row[filter.id];
            }
            return row[filter.id];
        },
        Filter: ({ filter, onChange }) =>
            <select
                onChange={event => onChange(event.target.value)}
                style={{ width: "100%" }}
                value={filter ? filter.value : "all"}
            >
                <option value="all">All</option>
                <option value="true">True</option>
                <option value="false">False</option>
            </select>
    },
    {
        Header: "Timestamp",
        accessor: "timestamp",
        Cell: props => <span className='number'>{new Date(props.value).toLocaleString('no-NB', { timeZone: 'UTC' })}</span>
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
        Cell: props => <span className='number'>{new Date(props.value).toLocaleString('no-NB', { timeZone: 'UTC' })}</span>
    },
    {
        Header: "Total",
        accessor: "total"
    }, {
        Header: "Type",
        accessor: "transactionType"
    }
];