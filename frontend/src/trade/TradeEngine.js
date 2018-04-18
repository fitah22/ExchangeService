import * as React from 'react';
import {Button, Row, Col} from 'reactstrap';
import {Transaction} from "./Transaction";
import {tradeURL} from "../ServiceURLS";
import axios from "axios/index";
import {Marketbook} from "./Marketbook";

export class TradeEngine extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            markets: ["BTC_USD"],
            currentMarket: "BTC_USD",
            currentMarketPrice: undefined
        };
    }

    componentDidMount() {
        axios.get(tradeURL + "markets").then(response => {
            this.setState({
                markets: response.data
            });
        });


        this.getCurrentPrice();
    }

    getCurrentPrice() {
        const {main, secondary} = this.getCurrenciesFromMarket();
        const apiUrl = `https://min-api.cryptocompare.com/data/price?fsym=${main}&tsyms=${secondary}`;
        axios.get(apiUrl).then(response => {
            if (response.status === 200) {
                this.setState({
                    currentMarketPrice: response.data[secondary]
                });
                console.log(`Current ${main}-${secondary} price: ${response.data[secondary]}`)
            }
        });
    }

    getCurrenciesFromMarket(market = undefined) {
        if (market === undefined) {
            market = this.state.currentMarket;
        }
        const values = market.split("_");
        return {
            main: values[0],
            secondary: values[1]
        }
    }


    render() {
        const {currentMarket} = this.state;
        const {main, secondary} = this.getCurrenciesFromMarket();
        return <React.Fragment>
            <Row>
                <Col md={12}>
                    <h2>{main}/{secondary}</h2>
                    {this.state.currentMarketPrice &&
                    <p>Current market price: 1 {main} = {this.state.currentMarketPrice} {secondary}</p>}
                </Col>
            </Row>
            <Row>
                <Col md={12}>
                    {this.renderMarkets()}
                </Col>
            </Row>
            <Col md={12}>
                <Row>
                    <Marketbook market={currentMarket}/>
                </Row>
            </Col>
            <Row>
                <Col md={12}>
                    <hr/>
                    <Row>
                        <Col md={6}>
                            <Transaction type={"Buy"} currency={main} unit={secondary}/>
                        </Col>
                        <Col md={6}>
                            <Transaction type={"Sell"} currency={main} unit={secondary}/>
                        </Col>
                    </Row>
                </Col>
            </Row>
        </React.Fragment>;
    }

    renderMarkets() {
        const markets = this.state.markets.filter(value => value !== this.state.currentMarket);
        if (markets.length > 0) {
            return (<React.Fragment>
                <p>Markets:</p>
                {
                    markets.map(value => {
                        const {main, secondary} = this.getCurrenciesFromMarket(value);
                        return <Button>{main}/{secondary}</Button>
                    })
                }
            </React.Fragment>)
        }
    }
}