import React from 'react'

const Devices = ({onShowConsumption, data}) =>{
    let totalConsumption = 0;
    data.map((d)=>{
        totalConsumption = totalConsumption + d.sensor.maxValue;
    })
    return(
        <>Total Consumption: {totalConsumption} kWh
            {
                data && data.map((d)=>{
                    //console.log(d);
                    return(
                        <div className="card " key={d.id} style={{background:d.color}}>
                            <div className="" style={{margin: 10}}>
                                <h5><b>{d.description}</b></h5>
                                <p>{d.address}</p>
                                <p>Sensor: {d.sensor.description}</p>
                                <p>Average consumption: {d.averageConsumption} kWh</p>
                                <p>Total consumption: {d.maxConsumption} kWh</p>
                                <button  className={"float-sm-right btn-info btn btn-sm m-md-2"} onClick={onShowConsumption}>Show Consumption</button>
                            </div>
                        </div>


                    )
                })
            }
        </>
    )
}

export default Devices;