import React, {Component, useState} from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import * as API_CLIENT from "./api/client-api"
import {Button, CardHeader, Card, Row, Col, Form, Input, Label} from "reactstrap";
import {Alert} from "react-bootstrap";
import { useHistory} from 'react-router-dom';
function ClientLogin(props) {


    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');


    const credentialChange = (event) =>{
        if(event.target.name === 'email'){
            setEmail(event.target.value);
            console.log("aici " + email);
        }else{
            setPassword(event.target.value);
        }
        console.log("Email: " + email + " Pass: " + password );
    }
    let history = useHistory();
    const handleSubmit = () =>{
        let user = {
            email: email,
            password: password
        }

        console.log(user);
        API_CLIENT.login(user,(result,status,error) =>{
            if(result !== null && status ===200){
                console.log(result);
                if(result.loginSuccess){
                    if(result.isAdmin){
                        localStorage.setItem("isAdmin","true");
                        localStorage.setItem("isLogin", "true");
                        localStorage.setItem("user", result.idUser);
                        history.push("/allClient");
                        history.go(0);
                    }else{
                        localStorage.setItem("isAdmin","false");
                        localStorage.setItem("isLogin","true");
                        localStorage.setItem("user", result.idUser);
                        history.push("/user");
                        history.go(0);
                    }
                }else{
                    setError("Invalid username or password");
                }

            }else {
                console.log(error);
            }
        })

    }

    return (
        <div>
            <CardHeader>
                <strong> Log in </strong>
            </CardHeader>
            <Card>
            <br/>

            <Row>
                <Col sm={{size: '3', offset:4}}>
                    {error && <Alert variant="danger">{error}</Alert>}
                    <Form >
                        <Label>Email:</Label>
                        <Input name="email" id="email" placeholder="Email.." type="text" value={email} onChange={credentialChange}/>
                        <Label>Password:</Label>
                        <Input name="password" id="password" placeholder="Password.." type="password" value={password} onChange={credentialChange}/>
                    </Form>
                    <Button className={"btn-info"} type={"submit"} onClick={handleSubmit}>Login</Button>
                </Col>
            </Row>
            </Card>
        </div>
    );
}

export default ClientLogin;