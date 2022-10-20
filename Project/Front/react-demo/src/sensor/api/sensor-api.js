import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    allSensor: '/allSensor',
    sensor: '/sensor/',
    createSensor: '/createSensor',
    updateSensor: '/updateSensor',
    deleteSensor: '/deleteSensor',
    allSensorFree: '/allSensorFree'
};

function getSensor(callback) {
    let request = new Request(HOST.backend_api + endpoint.allSensor, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getSensorFree(callback) {
    let request = new Request(HOST.backend_api + endpoint.allSensorFree, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getSensorById(params, callback){
    let request = new Request(HOST.backend_api + endpoint.sensor + params, {
        method: 'GET'
    });

    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

function createSensor(sensor, callback){
    let request = new Request(HOST.backend_api + endpoint.createSensor , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(sensor)
    });

    console.log("URL: " + request.url);
    console.log("Date: " + JSON.stringify(sensor));

    RestApiClient.performRequest(request, callback);
}

function updateSensor(sensor, callback){
    let request = new Request(HOST.backend_api + endpoint.createSensor , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(sensor)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function deleteSensor(sensor, callback){
    let request = new Request(HOST.backend_api + endpoint.deleteSensor, {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(sensor)
    });
    console.log(request.url);
    RestApiClient.performRequest(request,callback);
}

export {
    getSensor,
    getSensorById,
    createSensor,
    updateSensor,
    deleteSensor,
    getSensorFree
};
