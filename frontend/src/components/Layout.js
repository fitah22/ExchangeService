import * as React from 'react';
import { NavigationMenu } from './NavigationMenu';



export class Layout extends React.Component {
    render() {
        return <React.Fragment>
                <NavigationMenu />

            <div className='container-fluid'>
                <div className='row'>
                    <div className='col-sm-8'>
                        { this.props.children }
                    </div>
                </div>
            </div>
        </React.Fragment>;
    }
}