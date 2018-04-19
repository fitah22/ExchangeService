import * as React from 'react';
import {Button, Row, Col} from 'reactstrap';
import {tradeURL} from "../ServiceURLS";
import axios from "axios/index";
import {Market} from "./Market";
import {MarketList} from "./MarketList";

export class TradeHome extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isUp: false,
            markets: ["BTC_USD"],
            prices: undefined,
            currentMarket: "BTC_USD",
        };

        this.setCurrentMarket = this.setCurrentMarket.bind(this);
    }

    componentDidMount() {
        //http://localhost:8081/
        this.checkIsUp();

        axios.get(tradeURL + "markets").then(response => {
            this.setState({
                markets: response.data
            });
            this.getCurrentPriceAllMarkets(response.data);
        });
    }

    checkIsUp() {
        axios.get(tradeURL + "actuator/health").then(response => {
            if (response.data.status === "UP") {
                this.setIsUp(true);
            }
        }).catch(err => console.log("Could not connect to service from TradeHome " + err));
    }

    getCurrentPriceAllMarkets(markets = undefined) {
        if (markets === undefined) {
            markets = this.state.markets;
        }
        let requests = [];
        let secondaries = [];
        for (let i = 0; i < markets.length; i++) {
            const {main, secondary} = Market.getCurrenciesFromMarket(markets[i]);
            const apiUrl = `https://min-api.cryptocompare.com/data/price?fsym=${main}&tsyms=${secondary}`;
            requests.push(axios.get(apiUrl));
            secondaries.push(secondary);
        }

        axios.all(requests).then(data => {
            let prices = [];
            for (let i = 0; i < data.length; i++) {
                const secondary = secondaries[i];
                prices.push(data[i].data[secondary]);
            }
            this.setState({
                prices
            });
            console.log("Prices for markets has been fetched.");
        }).catch(e => console.log(e));

    }

    setCurrentMarket(market) {
        this.setState({
            currentMarket: market
        })
    }

    setIsUp(isUp) {
        this.setState({
            isUp: isUp
        })
    }


    render() {
        const {isUp, markets, currentMarket, prices} = this.state;
        let currentMarketPrice = undefined;
        if (prices) {
            currentMarketPrice = prices[markets.indexOf(currentMarket)];
        }
        if (!isUp) {
            return <React.Fragment>
                <p>Could not connect to trade microservice. Try again later.
                    <br/>
                    You can see the layout anyway. Please be aware that BTC/USD is added as default value.
                    <br/>
                    <Button color="link" onClick={() => this.setIsUp(true)}>See layout anyway</Button>
                </p>


            </React.Fragment>
        }
        return <Row>
            <Col md={4}>
                <MarketList markets={markets} currentMarket={currentMarket} prices={prices}
                            onClickRow={this.setCurrentMarket}/>
            </Col>
            <Col md={8}>
                <Market currentMarket={currentMarket} currentMarketPrice={currentMarketPrice}/>
            </Col>
        </Row>
    }

}