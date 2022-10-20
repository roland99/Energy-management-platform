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


import * as API_SENSOR from "./api/sensor-api"
import SensorTable from "../sensor/components/sensor-table";

import SensorForm from "./components/sensor-form";
import SensorFormEdit from "./components/sensor-form-edit";


class SensorContainer extends React.Component{

    constructor(props){
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.toggleFormEdit = this.toggleFormEdit.bind(this);
        this.state = {
            tableData: [],
            errorStatus: 0,
            error: null,
            isLoaded: false,
            editSensor: null
        }
    }

    componentDidMount() {

        this.setState({
            isLoaded: false
        });
        this.fetchSensor();
    }



    fetchSensor = () =>{
        return API_SENSOR.getSensor((result, status, err) =>{
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



    handleDeleteSensor = (id) => {
        console.log("Delete: " + id);
        API_SENSOR.deleteSensor(id, (result, status, error) =>{
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


    updateSensor = (client) =>{
        this.setState({
            editClient: client
        })
    }

    handleEditSensor = (id) => {
        console.log("Edit: " + id);

        API_SENSOR.getSensorById(id, (result, status, err) =>{
            if(result !== null && status === 200){
                console.log(result);
                this.updateSensor(result)
                this.setState({
                    editSensor: result,
                    isLoadedSensorEdit: true
                });
                console.log("Dupa setState: " + this.state.editSensor);
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

    reload = () => {
        this.setState({
            isLoaded: false
        });
        this.toggleForm();
        this.fetchSensor();
    }

    reloadEdit = () => {
        this.setState({
            isLoaded: false
        });
        this.toggleFormEdit();
        this.fetchSensor();
    }


    render(){
        return(
            <div>
                <CardHeader>
                    <strong> Sensor List </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm} >Add Sensor </Button>
                        </Col>
                    </Row>
                    <br/>

                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <SensorTable
                                onDelete={this.handleDeleteSensor}
                                tableData = {this.state.tableData}
                                onEdit = {this.handleEditSensor}

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
                    <ModalHeader toggle={this.toggleForm}> Add Sensor: </ModalHeader>
                    <ModalBody>
                        <SensorForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedEdit} toggle={this.toggleFormEdit}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleFormEdit}> Edit Sensor: </ModalHeader>
                    {this.state.isLoadedSensorEdit && <SensorFormEdit reloadHandler={this.reloadEdit}
                                                                      data = {this.state.editSensor}
                    />}
                </Modal>




            </div>
        )
    }

}

export default SensorContainer;