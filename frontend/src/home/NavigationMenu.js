import * as React from 'react';
import { Link, NavLink } from 'react-router-dom';

export class NavigationMenu extends React.Component {
    render() {
        return <nav className="navbar navbar-inverse navbar-fixed-top">
                <div className='navbar-header'>
                    <Link className='navbar-brand' to={ '/' }>Final project</Link>
                </div>
                <div className='clearfix'/>
                <div className='navbar-collapse'>
                    <ul className='nav navbar-nav'>
                        <li>
                            <NavLink to={ '/' } exact activeClassName='active'>
                                Home
                            </NavLink>
                        </li>
                        {this.renderLinks()}
                    </ul>
                </div>
        </nav>;
    }

    renderLinks() {
        const {isAuth} = this.props;
        if(isAuth){
            return (<React.Fragment>
                <li>
                    <NavLink to={ '/user' } activeClassName='active'>
                        User
                    </NavLink>
                </li>
                <li>
                    <NavLink to={ '/trade' } activeClassName='active'>
                        Trade
                    </NavLink>
                </li>
                <li>
                    <NavLink to={ '/history' } activeClassName='active'>
                        History
                    </NavLink>
                </li>
            </React.Fragment>)
        }

        return (<React.Fragment>
            <li>
                <NavLink to={ '/user' } activeClassName='active'>
                    Log in or register
                </NavLink>
            </li>
        </React.Fragment>)


    }

}
