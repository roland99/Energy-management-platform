import React, {Component} from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import SockJsClient from 'react-stomp';
import {
    Button,
    Card, CardBody,
    CardHeader,
    Col,
    Modal,
    ModalBody,
    ModalHeader,
    Row
} from 'reactstrap';
import {
    LineChart,
    ResponsiveContainer,
    Legend, Tooltip,
    Line,
    XAxis,
    YAxis,
    CartesianGrid
} from 'recharts';
import * as API_CLIENT from "./api/client-api"
import * as API_DEVICE from "../device/api/device-api"
import Devices from "./devices";
import {HOST} from "../commons/hosts";

const SOCKET_URL = HOST.backend_api + "/ws-message";

const pdata = [
    {
        name: '00:00',
        student: 11,
        kWh: 5
    },
    {
        name: '04:00',
        student: 15,
        kWh: 1
    },
    {
        name: '08:00',
        student: 5,
        kWh: 4
    },
    {
        name: '12:00',
        student: 10,
        kWh: 5
    },
    {
        name: '16:00',
        student: 9,
        kWh: 3
    },
    {
        name: '20:00',
        student: 10,
        kWh: 8
    },
    {
        name: '00:00',
        student: 10,
        kWh: 4
    },
];

class UserHome extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userData: [],
            isLoaded: false,
            isLoadedSocket: false,
            showChart: false,
            deviceList: [],
            deviceDataList: [],
            socketMessage: ""
        }
    }

    onConnectedSocket = () => {
        console.log("Connected!!");
    }

    onMessageReceived = (msg) => {
        this.setState({
            socketMessage:msg.message,
            isLoadedSocket: true
        })
        console.log("A fost primit mesaj de la WebSocket: " + msg.message);
    }


    componentDidMount() {
        this.fetchUser();
    }

    fetchUser = () => {
        return API_CLIENT.getClientById(localStorage.getItem("user"),(result,status,error) =>{
            if(result !== null && status ===200){
                //console.log(result);
                this.setState({
                    clientObject: result,
                    deviceList: result.deviceList
                })
                console.log(this.state.deviceList);
                let n = this.state.deviceList.length;
                let i = 0;
                //get every item in the list from server
                while(i < n) {
                    API_DEVICE.getDeviceById(result.deviceList[i].id, (result, status, error) => {
                        if (result !== null && status === 200) {
                            console.log(result);
                            let newArr = this.state.deviceDataList;
                            newArr.push(result);
                            this.setState({
                                deviceDataList: newArr
                            });
                        }
                    })
                    i = i+1;
                }
                this.setState({
                    isLoaded: true
                })
                //console.log("da: " + this.state.deviceData);
            }else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }))
            }
        })
    }

    handleShowChart = () =>{
        this.setState({
            showChart: !this.state.showChart
        });
    }

    handleShowConsumption = () =>{
        console.log("Handle show consumption");
    }
    render() {
        return (
            <div>
                <SockJsClient
                    url={SOCKET_URL}
                    topics={['/user']}
                    onConnect={this.onConnectedSocket}
                    onDisconnect={console.log("Disconnected!")}
                    onMessage={msg => this.onMessageReceived(msg)}
                    debug={false}
                />
                <CardHeader>
                    User details
                </CardHeader>
                { this.state.isLoadedSocket && <div style={{color: "red"}}>{this.state.socketMessage}</div>}
                <CardBody>

                    <Card>
                        <Row>
                            <Col>
                                <ul>
                                    {this.state.isLoaded && <li>Name: {this.state.clientObject.name}</li>}
                                    {this.state.isLoaded && <li>Email: {this.state.clientObject.email}</li>}
                                    {this.state.isLoaded && <li>Address: {this.state.clientObject.address}</li>}
                                    {this.state.isLoaded && <li>Birth date: {this.state.clientObject.birthDate}</li>}
                                </ul>
                                <Button type={"submit"} className={"btn btn-sm btn-info"} onClick={this.handleShowChart}>Show Consumption</Button>
                            </Col>
                        </Row>



                        {this.state.showChart && <ResponsiveContainer width="100%" aspect={3}>
                            <LineChart data={pdata} margin={{ right: 300 }}>
                                <CartesianGrid />
                                <XAxis dataKey="name"
                                       interval={'preserveStartEnd'} />
                                <YAxis></YAxis>
                                <Legend />
                                <Tooltip />

                                <Line dataKey="kWh"
                                      stroke="red" activeDot={{ r: 8 }} />
                            </LineChart>
                        </ResponsiveContainer>}

                    </Card>
                    <h4>Devices:</h4>
                    {this.state.isLoaded && <Devices onShowConsumption={this.handleShowConsumption} data={this.state.deviceDataList}></Devices>}

                </CardBody>
            </div>
        );
    }
}

export default UserHome;