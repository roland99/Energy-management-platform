import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";




const endpoint = {
    allClient: '/allClient',
    client: '/client/',
    createClient: '/createClient',
    updateClient: '/updateClient',
    deleteClient: '/deleteClient',
    login: '/login'
};

function getClients(callback){
    let request = new Request(HOST.backend_api + endpoint.allClient, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request,callback);
}

function getClientById(id, callback){
    let request = new Request(HOST.backend_api + endpoint.client + id, {
        method: 'GET'
    });
    console.log(request.url);
    RestApiClient.performRequest(request,callback);
}

function addClient(client, callback){
    let request = new Request(HOST.backend_api + endpoint.createClient, {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(client)
    });
    console.log(request.url);
    RestApiClient.performRequest(request,callback);
}

function deleteClient(client, callback){
    let request = new Request(HOST.backend_api + endpoint.deleteClient, {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(client)
    });
    console.log(request.url);
    RestApiClient.performRequest(request,callback);
}

function updateClient(client, callback){
    let request = new Request(HOST.backend_api + endpoint.updateClient, {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(client)
    });
    console.log(request.url);
    RestApiClient.performRequest(request,callback);
}

function login(data, callback){
    let request = new Request(HOST.backend_api + endpoint.login, {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    });
    console.log(request.url)
    RestApiClient.performRequest(request,callback);
}


export{
    getClients,
    addClient,
    deleteClient,
    getClientById,
    updateClient,
    login
}