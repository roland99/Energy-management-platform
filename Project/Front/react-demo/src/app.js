import React from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import NavigationBar from './navigation-bar'
import Home from './home/home';
import PersonContainer from './person/person-container'

import ErrorPage from './commons/errorhandling/error-page';
import styles from './commons/styles/project-style.css';
import ClientContainer from "./client/client-container";
import DeviceContainer from "./device/device-container";
import SensorContainer from "./sensor/sensor-container";
import ClientLogin from "./client/client-login";
import {ProtectedRoute} from "./protected-route";
import UserHome from "./client/user-home";
import {UserRoute} from "./user-route";

class App extends React.Component {


    render() {

        return (
            <div >
            <Router>
                <div>
                    <NavigationBar />
                    <Switch>
                        <Route
                            exact
                            path='/'
                            render={() => <Home/>}
                        />

                        <Route
                            exact
                            path='/person'
                            render={() => <PersonContainer/>}
                        />
                        <ProtectedRoute
                            exact
                            path='/allClient'
                            component={ClientContainer}
                        />
                        <ProtectedRoute
                            exact
                            path='/allDevice'
                            component={DeviceContainer}

                        />
                        <ProtectedRoute
                            exact
                            path='/allSensor'
                            component={SensorContainer}

                        />
                        <Route
                            exact
                            path='/login'
                            render={() => <ClientLogin/>}

                        />
                        <Route
                            exact
                            path='/logout'
                            render={() => <ClientLogin/>}

                        />

                        <UserRoute
                            exact
                            path='/user'
                            component={UserHome}

                        />

                        {/*Error*/}
                        <Route
                            exact
                            path='/error'
                            render={() => <ErrorPage/>}
                        />

                        <Route render={() =><ErrorPage/>} />
                    </Switch>
                </div>
            </Router>
            </div>
        )
    };
}

export default App
