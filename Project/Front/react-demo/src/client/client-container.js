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


import * as API_CLIENT from "./api/client-api"
import ClientTable from "../client/components/client-table";
import ClientForm from "./components/client-form";
import ClientFormEdit from "./components/client-form-edit";


class ClientContainer extends React.Component{

    constructor(props){
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.toggleFormEdit = this.toggleFormEdit.bind(this);
        this.state = {
            tableData: [],
            errorStatus: 0,
            error: null,
            isLoaded: false,
            editClient: null
        }
    }

    componentDidMount() {

        this.setState({
            isLoaded: false
        });
        this.fetchClient();
    }



    fetchClient = () =>{
        return API_CLIENT.getClients((result, status, err) =>{
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



    handleDeleteClient = (id) => {
        console.log("Delete: " + id);
        API_CLIENT.deleteClient(id, (result, status, error) =>{
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

    handleDeviceAdd = (id) => {
        console.log("Add: " + id);
    }

    updateClient = (client) =>{
        this.setState({
            editClient: client
        })
    }

    handleEditClient = (id) => {
        console.log("Edit: " + id);

        API_CLIENT.getClientById(id, (result, status, err) =>{
            if(result !== null && status === 200){
                console.log(result);
                this.updateClient(result)
                this.setState({
                    editClient: result,
                    isLoadedClientEdit: true
                });
                console.log("Dupa setState: " + this.state.editClient);
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
        this.fetchClient();
    }

    reloadEdit = () => {
        this.setState({
            isLoaded: false
        });
        this.toggleFormEdit();
        this.fetchClient();
    }


    render(){
        return(
            <div>
                <CardHeader>
                    <strong> Client List </strong>
                </CardHeader>
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm} >Add Client </Button>
                        </Col>
                    </Row>
                    <br/>

                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <ClientTable
                                onDelete={this.handleDeleteClient}
                                tableData = {this.state.tableData}
                                onAddDevice = {this.handleDeviceAdd}
                                onEdit = {this.handleEditClient}

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
                    <ModalHeader toggle={this.toggleForm}> Add Client: </ModalHeader>
                    <ModalBody>
                        <ClientForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.selectedEdit} toggle={this.toggleFormEdit}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleFormEdit}> Edit Client: </ModalHeader>
                    {this.state.isLoadedClientEdit && <ClientFormEdit reloadHandler={this.reloadEdit}
                            data = {this.state.editClient}
                        />}
                </Modal>




            </div>
        )
    }

}

export default ClientContainer;