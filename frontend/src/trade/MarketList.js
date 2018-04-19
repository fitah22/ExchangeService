import * as React from 'react';
import {Table, Row, Col} from 'reactstrap';
import {Market} from "./Market";
import PropTypes from "prop-types";


export class MarketList extends React.Component {

    render() {
        return <Row>
            <Col md={12}>
                {this.renderMarkets()}
            </Col>
        </Row>
    }

    renderMarkets() {
        const {markets, currentMarket, prices, onClickRow} = this.props;
        if (markets.length > 0) {
            return (<React.Fragment>
                <h2>Markets:</h2>
                <p>Here you can choose the market you want to trade in.</p>
                <Table striped>
                    <thead>
                    <tr>
                        <td>Market</td>
                        <td>Current price</td>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        markets.map((market, i) => {
                            const style = market === currentMarket ? {"fontWeight": "bold"} : null;
                            const {main, secondary} = Market.getCurrenciesFromMarket(market);
                            const price = prices === undefined ? "" : prices[i];
                            return <tr key={i} onClick={() => onClickRow(market)} style={style}>
                                <td>{main}/{secondary}</td>
                                <td>{price}</td>
                            </tr>
                        })
                    }
                    </tbody>
                </Table>
            </React.Fragment>)
        }
    }
}

MarketList.propTypes = {
    markets: PropTypes.arrayOf(PropTypes.string).isRequired,
    currentMarket: PropTypes.string,
    prices: PropTypes.arrayOf(PropTypes.number),
    onClickRow: PropTypes.func,
};