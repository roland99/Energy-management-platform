import React from 'react'
import {Route, Redirect} from 'react-router-dom'

export const UserRoute = ({component: Component, ...rest}) =>{
    return(
        <Route {...rest}
               render={ props => {
                   if (localStorage.getItem("isAdmin") ==="false" &&
                   localStorage.getItem("isLogin") === "true") {
                       return <Component {...props}/>;
                   }else{
                       return <Redirect to={
                           {
                               pathname: "/login",
                               state: {
                                   from: props.location
                               }
                           }
                       }/>
                   }
               }}
        />
    );
};