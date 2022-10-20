import React from "react";

import ReactTable from "react-table";

let columns = [
    {
        Header: 'Description',
        accessor: 'description',
    },
    {
        Header: 'Address',
        accessor: 'address',
    },
    {
        Header: 'Max Consumptions',
        accessor: 'maxConsumption',

    },
    {
        Header: 'Average consumption',
        accessor: 'averageConsumption',

    },
    {
        Header: 'Sensor',
        accessor: 'sensor.description',

    },
];


const filters = [
    {
        accessor: 'description',
    }
]

class DeviceTable extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            tableData: this.props.tableData

        };
        console.log(this.props.tableData);

    }


    render(){
        return(
            <ReactTable
                data = {this.state.tableData}
                columns = {columns.concat({
                    Header: 'Actiuni',
                    accessor: 'id',
                    width: 350,
                    Cell: ({value}) => <div><button className="btn btn-sm btn-primary m-md-2" onClick={() => {this.props.onEdit(value )} }>Edit</button>
                        <button className={"btn btn-danger btn-sm m-md-2"} onClick={() => {this.props.onDelete(value )} }>Delete</button>
                        <button className={"btn btn-primary btn-sm m-md-2"} onClick={() => {this.props.onSensorAdd(value )} }>Add Sensor</button>
                        <button className={"btn btn-primary btn-sm m-md-2"} onClick={() => {this.props.onClientAdd(value )} }>Add Client</button>
                    </div>
                }) }
                search={filters}
                pageSize={5}
            />
        )
    }
}

export default DeviceTable;