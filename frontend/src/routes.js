import * as React from "react";
import {Route} from "react-router-dom";
import {Layout} from "./home/Layout";
import {Home} from "./home/Home";
import {LoginService} from "./login/LoginService";
import {TradeEngine} from "./trade/TradeEngine";
import {History} from "./history/History";


export const routes = <Layout>
    <Route exact path="/" component={Home}/>
    <Route exact path="/user" component={LoginService}/>
    <Route exact path="/trade" component={TradeEngine}/>
    <Route exact path="/history" component={History}/>

</Layout>;