import * as React from 'react';
import {Link} from 'react-router-dom';
import {Navbar, NavbarBrand, Nav, NavItem, NavLink, NavbarToggler, Collapse} from 'reactstrap';


export class NavigationMenu extends React.Component {
    constructor(props) {
        super(props);

        this.toggleNavbar = this.toggleNavbar.bind(this);
        this.state = {
            collapsed: true
        };
    }

    toggleNavbar() {
        this.setState({
            collapsed: !this.state.collapsed
        });
    }

    render() {
        return <div>
            <Navbar expand="md" color="dark" dark>
                <NavbarBrand tag={Link} to="/">Final project</NavbarBrand>
                <div className='clearfix'/>
                <NavbarToggler  onClick={this.toggleNavbar} className="mr-2" />
                <Collapse isOpen={!this.state.collapsed} navbar>
                    <Nav navbar>

                        <NavItem>
                            <NavLink tag={Link} to="/" activeClassName="active">
                                Home
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink tag={Link} to={'/user'} activeClassName="active">
                                User
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink tag={Link} to={'/trade'} activeClassName="active">
                                Trade
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink tag={Link} to={'/history'} activeClassName="active">
                                History
                            </NavLink>
                        </NavItem>
                        {this.renderLogin()}
                    </Nav>
                </Collapse>
            </Navbar>
        </div>
    }

    renderLogin() {
        const {isAuth, login, signup, restAuth} = this.props;
        if (isAuth) {
            return (<React.Fragment>
                <NavItem>
                    <NavLink tag={Link} to="#" onClick={restAuth}>
                        Logout
                    </NavLink>
                </NavItem>
            </React.Fragment>)
        }

        return (<React.Fragment>
            <NavItem>
                <NavLink tag={Link} to="#" onClick={login}>
                    Login
                </NavLink>
            </NavItem>
            <NavItem>
                <NavLink tag={Link} to="#" onClick={signup}>
                    Sign up
                </NavLink>
            </NavItem>
        </React.Fragment>)


    }

}
