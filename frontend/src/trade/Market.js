import * as React from 'react';
import {Row, Col} from 'reactstrap';
import {Transaction} from "./Transaction";
import {Tradebook} from "./Tradebook";
import PropTypes from "prop-types";

export class Market extends React.Component {

    static getCurrenciesFromMarket(market) {
        const values = market.split("_");
        return {
            main: values[0],
            secondary: values[1]
        }
    }

    render() {
        const {currentMarket, currentMarketPrice} = this.props;
        const {main, secondary} = Market.getCurrenciesFromMarket(currentMarket);
        return <React.Fragment>
            <Row>
                <Col md={12}>
                    <h2>{main}/{secondary}</h2>
                    {currentMarketPrice &&
                    <p>Current market price: 1 {main} = {currentMarketPrice} {secondary}</p>}
                </Col>
            </Row>
            <Col md={12}>
                <Tradebook market={currentMarket}/>
            </Col>
            <Row>
                <Col md={12}>
                    <hr/>
                    <Row>
                        <Col md={6}>
                            <Transaction type={"Buy"} main={main} secondary={secondary}/>
                        </Col>
                        <Col md={6}>
                            <Transaction type={"Sell"} main={main} secondary={secondary}/>
                        </Col>
                    </Row>
                </Col>
            </Row>
        </React.Fragment>;
    }


}

Market.propTypes = {
    currentMarket: PropTypes.string.isRequired,
    currentMarketPrice: PropTypes.number,
};