import React from "react";


import ReactTable from "react-table";
import * as API_CLIENT from "../../client/api/client-api";

let columns = [
    {
        Header: 'Email',
        accessor: 'email',
    },
    {
        Header: 'Name',
        accessor: 'name',
    },
    {
        Header: 'Address',
        accessor: 'address',

    },
];



const filters = [
    {
        accessor: 'name',
    }
]

class ClientSelection extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            tableData: this.props.tableData

        };
        console.log(this.props.tableData);
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


    render(){
        return(
            <ReactTable
                data = {this.state.tableData}
                columns = {columns.concat({
                    Header: 'Actiuni',
                    accessor: 'id',
                    width: 250,
                    Cell: ({value}) => <div><button className="btn btn-sm btn-primary m-md-2" onClick={() => {this.props.onSelect(value )} }>Select</button>
                    </div>
                }) }
                search={filters}
                pageSize={5}
            />
        )
    }
}

export default ClientSelection;