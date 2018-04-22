import * as React from 'react';
import {Row, Col} from 'reactstrap';
import {Transaction} from "./Transaction";
import {Tradebook} from "./Tradebook";
import PropTypes from "prop-types";
import {tradeURL} from "../ServiceURLS";
import axios from "axios/index";
import {MyTrades} from "./MyTrades";

export class Market extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            loadingFail: true,
            buy: [],
            sell: [],
        };
        this.updateMarketBook = this.updateMarketBook.bind(this);
    }

    componentDidMount() {
        this.updateMarketBook()
    }

    render() {
        const {currentMarket, currentMarketPrice} = this.props;
        const {loading, loadingFail, buy, sell} = this.state;
        const {main, secondary} = Market.getCurrenciesFromMarket(currentMarket);
        let tradebookComponent;
        if (loading) {
            tradebookComponent = <p>Loading market book...</p>
        } else if (loadingFail) {
            tradebookComponent = <p>Something went wrong with loading market book. Please try again.</p>
        } else {
            tradebookComponent = <Tradebook buy={buy} sell={sell}/>
        }

        return <React.Fragment>
            <Row>
                <Col md={12}>
                    <h2>{main}/{secondary}</h2>
                    {currentMarketPrice &&
                    <p>Current market price: 1 {main} = {currentMarketPrice} {secondary}</p>}
                </Col>
            </Row>
            <Row>
                <Col md={12}>
                    {tradebookComponent}
                </Col>
            </Row>
            <Row>
                <Col md={12}>
                    <hr/>
                    <Row>
                        <Col md={6}>
                            <Transaction type={"Buy"} main={main} secondary={secondary}
                                         onUpdate={this.updateMarketBook}/>
                        </Col>
                        <Col md={6}>
                            <Transaction type={"Sell"} main={main} secondary={secondary}
                                         onUpdate={this.updateMarketBook}/>
                        </Col>
                    </Row>
                </Col>
            </Row>
            <Row className={"mt-4"}>
                <Col md={12}>
                    <MyTrades buy={buy} sell={sell} onUpdate={this.updateMarketBook}/>
                </Col>
            </Row>
        </React.Fragment>;
    }

    updateMarketBook() {
        const {currentMarket} = this.props;
        this.setState({
            loading: true
        });

        axios.all([
            axios.get(tradeURL + currentMarket.toUpperCase() + "/SELL"),
            axios.get(tradeURL + currentMarket.toUpperCase() + "/BUY")
        ]).then(([sell, buy]) => {

            this.setState({
                sell: sell.data,
                buy: buy.data,
                loading: false,
                loadingFail: false
            });
        }).catch(() => {
            this.setState({
                loading: false,
                loadingFail: true,
            });
        });

    }

    static getCurrenciesFromMarket(market) {
        const values = market.split("_");
        return {
            main: values[0],
            secondary: values[1]
        }
    }


}

Market.propTypes = {
    currentMarket: PropTypes.string.isRequired,
    currentMarketPrice: PropTypes.number,
};