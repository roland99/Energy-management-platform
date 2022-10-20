import React from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import {
    Button,
    Card,
    CardHeader,
    Col,
    Modal,
    ModalBody,
    ModalHeader,
    Row
} from 'reactstrap';


import * as API_DEVICE from "./api/device-api"
import DeviceTable from "../device/components/device-table";

import DeviceForm from "./components/device-form";
import DeviceFormEdit from "./components/device-form-edit";
import SensorSelection from "../sensor/components/sensor-selection";
import ClientSelection from "../sensor/components/client-selection";


class DeviceContainer extends React.Component{

    constructor(props){
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.toggleFormEdit = this.toggleFormEdit.bind(this);
        this.toggleSensorAdd = this.toggleSensorAdd.bind(this);
        this.toggleClientAdd = this.toggleClientAdd.bind(this);
        this.state = {
            tableData: [],
            errorStatus: 0,
            error: null,
            isLoaded: false,
            editDevice: null,
        }
    }

    componentDidMount() {

        this.setState({
            isLoaded: false
        });
        this.fetchDevice();
    }



    fetchDevice = () =>{
        return API_DEVICE.getDevices((result, status, err) =>{
            if(result !== null && status ===200){
                console.log(result);
                this.setState({
                    tableData: result,
                    isLoaded: true

                });
            }else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }))
            }
        });
    }

    toggleForm(){
        this.setState({selected: !this.state.selected});
    }

    toggleFormEdit(){
        this.setState({selectedEdit: !this.state.selectedEdit});
    }

    toggleSensorAdd(){
        this.setState({selectedSensorAdd: !this.state.selectedSensorAdd});
    }

    toggleClientAdd(){
        this.setState({selectedClientAdd: !this.state.selectedClientAdd});
    }



    handleDeleteDevice = (id) => {
        console.log("Delete: " + id);
        API_DEVICE.deleteDevice(id, (result, status, error) =>{
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully deleted client with id: " + id);
                this.componentDidMount();
            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });


    }


    handleEditDevice = (id) => {
        console.log("Edit: " + id);

        API_DEVICE.getDeviceById(id, (result, status, err) =>{
            if(result !== null && status === 200){
                console.log(result);
                this.setState({
                    editDevice: result,
                    isLoadedDeviceEdit: true
                });
                console.log("Dupa setState: " + this.state.editDevice);
            }else{
                this.setState(({
                    errorStatus: result,
                    error: err
                }))
            }
        });

        this.toggleFormEdit();
        //iau de aici id, il pun in state si apelez get in form sau aici pt restul
        //datelor si il trimit prin props in From

    }

    /***************Sensor*******************/
    //aleg device-ul la care atasez un senzor
    handleDeviceSelectSensor = (id) =>{
        this.setState({
            selectedDeviceId: id,
            isLoadedSensorAdd: true
        });
        console.log("Am selectat device: " + id);
        this.toggleSensorAdd();

    }
    //the continuatuin of above function after the sensor is selected too
    selectedSensor = (sensorId) =>{
        console.log("Am selectat sensor: " + sensorId);
        let selection = {
            deviceId : this.state.selectedDeviceId,
            sensorId : sensorId
        };

        API_DEVICE.addSensor(selection, (result, status, err) =>{
            if(result !== null && status === 200){
                console.log(result);

            }else{
                this.setState(({
                    errorStatus: result,
                    error: err
                }))
            }
        });

        this.toggleSensorAdd();
        this.componentDidMount();

    }

    /***************Client*******************/
    //aleg device-ul la care atasez un senzor
    handleDeviceSelectClient = (id) =>{
        this.setState({
            selectedDeviceId: id,
            isLoadedClientAdd: true
        });
        console.log("Am selectat device: " + id);
        this.toggleClientAdd();

    }
    //the continuatuin of above function after the sensor is selected too
    selectedClient = (clientId) =>{
        console.log("Am selectat sensor: " + clientId);
        let selection = {
            deviceId : this.state.selectedDeviceId,
            clientId : clientId
        };

        API_DEVICE.addClient(selection, (result, status, err) =>{
            if(result !== null && status === 200){
                console.log(result);

            }else{
                this.setState(({
                    errorStatus: result,
                    error: err
                }))
            }
        });

        this.toggleClientAdd();
        this.componentDidMount();

    }

    reload = () => {
        this.setState({
            isLoaded: false
        });
        this.toggleForm();
        this.fetchDevice();
    }

    reloadEdit = () => {
        this.setState({
            isLoaded: false
        });
        this.toggleFormEdit();
        this.fetchDevice();
    }


    render(){
        return(
            <div>
                <CardHeader>
                    <strong> Device List </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm} >Add Device </Button>
                        </Col>
                    </Row>
                    <br/>

                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <DeviceTable
                                onDelete={this.handleDeleteDevice}
                                tableData = {this.state.tableData}
                                onEdit = {this.handleEditDevice}
                                onSensorAdd = {this.handleDeviceSelectSensor}
                                onClientAdd = {this.handleDeviceSelectClient}

                            />}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />}
                        </Col>
                    </Row>


                </Card>


                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add Device: </ModalHeader>
                    <ModalBody>
                        <DeviceForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedEdit} toggle={this.toggleFormEdit}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleFormEdit}> Edit Client: </ModalHeader>
                    {this.state.isLoadedDeviceEdit && <DeviceFormEdit reloadHandler={this.reloadEdit}
                                                            data = {this.state.editDevice}
                    />}
                </Modal>

                <Modal isOpen={this.state.selectedSensorAdd} toggle={this.toggleSensorAdd}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleSensorAdd}> Select sensor: </ModalHeader>
                    {this.state.isLoadedSensorAdd && <SensorSelection onSelect={this.selectedSensor}

                    />}
                </Modal>


                <Modal isOpen={this.state.selectedClientAdd} toggle={this.toggleClientAdd}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleClientAdd}> Select client: </ModalHeader>
                    {this.state.isLoadedClientAdd && <ClientSelection onSelect={this.selectedClient}

                    />}
                </Modal>




            </div>
        )
    }

}

export default DeviceContainer;