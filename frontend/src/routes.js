import * as React from "react";
import {Route} from "react-router-dom";
import {Layout} from "./components/Layout";
import {Home} from "./components/Home";
import {LoginService} from "./components/LoginService";
import {TradeEngine} from "./components/TradeEngine";
import {History} from "./components/History";


export const routes = <Layout>
    <Route exact path="/" component={Home}/>
    <Route exact path="/login" component={LoginService}/>
    <Route exact path="/trade" component={TradeEngine}/>
    <Route exact path="/history" component={History}/>

</Layout>;