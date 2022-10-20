import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    allDevice: '/allDevice',
    device: '/device/',
    createDevice: '/createDevice',
    updateDevice: '/updateDevice',
    deleteDevice: '/deleteDevice',
    addSensor: '/addSensorToDevice',
    addClient: '/addClientToDevice'
};

function getDevices(callback) {
    let request = new Request(HOST.backend_api + endpoint.allDevice, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getDeviceById(params, callback){
    let request = new Request(HOST.backend_api + endpoint.device + params, {
        method: 'GET'
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function createDevice(device, callback){
    let request = new Request(HOST.backend_api + endpoint.createDevice , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(device)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function updateDevice(device, callback){
    let request = new Request(HOST.backend_api + endpoint.createDevice , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(device)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function deleteDevice(device, callback){
    let request = new Request(HOST.backend_api + endpoint.deleteDevice, {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(device)
    });
    console.log(request.url);
    RestApiClient.performRequest(request,callback);
}

function addSensor(ids, callback){
    let request = new Request(HOST.backend_api + endpoint.addSensor, {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(ids)
    });
    console.log(request.url);
    RestApiClient.performRequest(request,callback);
}

function addClient(ids, callback){
    let request = new Request(HOST.backend_api + endpoint.addClient, {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(ids)
    });
    console.log(request.url);
    RestApiClient.performRequest(request,callback);
}

export {
    getDevices,
    getDeviceById,
    createDevice,
    updateDevice,
    deleteDevice,
    addSensor,
    addClient
};
