import React from "react";

import ReactTable from "react-table";

let columns = [
    {
        Header: 'Description',
        accessor: 'description',
    },
    {
        Header: 'Max Value',
        accessor: 'maxValue',

    },

];


const filters = [
    {
        accessor: 'description',
    }
]

class SensorTable extends React.Component{

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
                    width: 250,
                    Cell: ({value}) => <div><button className="btn btn-sm btn-primary m-md-2" onClick={() => {this.props.onEdit(value )} }>Edit</button>
                        <button className={"btn btn-danger btn-sm m-md-2"} onClick={() => {this.props.onDelete(value )} }>Delete</button></div>
                }) }
                search={filters}
                pageSize={5}
            />
        )
    }
}

export default SensorTable;