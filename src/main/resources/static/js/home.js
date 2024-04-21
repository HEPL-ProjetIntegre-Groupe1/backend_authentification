function addRequest() {
    fetch('/mongoDB/request', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            userId: '1',
            identificationToken: '1234567890',
            websiteTarget: 'retraite'
            // other data to include in the body
        }),
    })
        .then(response => response.json())
        .then(data => {
            // Convert the data to a string if it's an object
            let output = typeof data === 'object' ? JSON.stringify(data, null, 2) : data;
            // Replace the content of the 'output' element with the data
            document.getElementById('output').textContent = output;
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function getAllRequest() {
    fetch('/mongoDB/request', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data => {
            // Convert the data to a string if it's an object
            let output = typeof data === 'object' ? JSON.stringify(data, null, 2) : data;
            // Replace the content of the 'output' element with the data
            document.getElementById('output').textContent = output;
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function getRequest(id) {
    if(id === undefined) {id = 1;}
    fetch('/mongoDB/request/get?id=' + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data => {
            // Convert the data to a string if it's an object
            let output = typeof data === 'object' ? JSON.stringify(data, null, 2) : data;
            // Replace the content of the 'output' element with the data
            document.getElementById('output').textContent = output;
        })
        .catch((error) => {
            console.error('Error:', error);
            document.getElementById('output').textContent = "no data found";
        });
}

function deleteRequest(id) {
    if(id === undefined) {id = 1;}
    fetch('/mongoDB/request?id=' + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
            if(response.status === 200) {
                output = "Deleted";
            } else {
                output = "No data found";
            }
            // Replace the content of the 'output' element with the data
            document.getElementById('output').textContent = output;
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function getAllData() {
    fetch('/mysql/data', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data => {
            // Convert the data to a string if it's an object
            let output = typeof data === 'object' ? JSON.stringify(data, null, 2) : data;
            // Replace the content of the 'output' element with the data
            document.getElementById('output').textContent = output;
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function getData(id) {
    if(id === undefined) {id = 1;}
    fetch('/mysql/data?id='+id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data => {
            // Convert the data to a string if it's an object
            let output = typeof data === 'object' ? JSON.stringify(data, null, 2) : data;
            // Replace the content of the 'output' element with the data
            document.getElementById('output').textContent = output;
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function addData() {
    fetch('/mysql/data', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            utilisateurid: {'id': '1'},
            notSoSecretData: 'If you look in the resources/js folder, you will find this string in home.js'
            // other data to include in the body
        }),
    })
        .then(response => response.json())
        .then(responseEntity => {
            let output = typeof responseEntity === 'object' ? JSON.stringify(responseEntity, null, 2) : responseEntity;
            // Replace the content of the 'output' element with the data
            document.getElementById('output').textContent = output;
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function deleteData(id) {
    if(id === undefined) {id = 1;}
    fetch('/mysql/data?id=' + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
            if(response.status === 200) {
                output = "Deleted";
            } else {
                output = "No data found";
            }
            // Replace the content of the 'output' element with the data
            document.getElementById('output').textContent = output;
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function hello() {
    console.log("Hello");
}