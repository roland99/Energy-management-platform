import React from 'react'
import logo from './commons/images/icon.png';

import {
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Nav,
    Navbar,
    NavbarBrand,
    NavLink,
    UncontrolledDropdown
} from 'reactstrap';
import {useHistory} from "react-router-dom";

const textStyle = {
    color: 'white',
    textDecoration: 'none'
};

const NavigationBar = props => (
    <div>
        <Navbar color="dark" light expand="md">
            <NavbarBrand href="/">
                <img src={logo} width={"50"}
                     height={"35"} />
            </NavbarBrand>
            <Nav className="mr-auto" navbar>

                <UncontrolledDropdown nav inNavbar>
                    <DropdownToggle style={textStyle} nav caret>
                       Menu
                    </DropdownToggle>
                    <DropdownMenu right >

                        <DropdownItem>
                            <NavLink href="/person">Persons</NavLink>
                        </DropdownItem>
                        <DropdownItem>
                            <NavLink href="/allClient">Clients</NavLink>
                        </DropdownItem>
                        <DropdownItem>
                            <NavLink href="/allDevice">Devices</NavLink>
                        </DropdownItem>
                        <DropdownItem>
                            <NavLink href="/allSensor">Sensors</NavLink>
                        </DropdownItem>

                    </DropdownMenu>

                </UncontrolledDropdown>

            </Nav>
            <Nav className={"navbar-right"}>
                <NavLink style={{color: "white"}} href="/login">Log in</NavLink>
            </Nav>
            {localStorage.getItem("isLogin") === "true" && <Nav className={"navbar-right"}>
                <NavLink style={{color: "white"}} onClick={
                    () => {
                        localStorage.setItem("isAdmin","false");
                        localStorage.setItem("isLogin","false");
                    }
                }
                href="/logout">Log out</NavLink>
            </Nav>}

        </Navbar>
    </div>
);

export default NavigationBar
