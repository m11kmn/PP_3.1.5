async function getAllUsers() {
    let response = await fetch('http://localhost:8080/admin/show');
    return await response.json();
}

let editUser = {}
let deleteUser = {}

function showAllUsers() {
    $('tbody tr').remove();
    getAllUsers().then(users => {
        $('#this-email').text(users[0].username)
        $('#this-roles').text(users[0].rolesId.map(x => x.replace('ROLE_', '')).toString().replace(',', ' '))
        for (let user of users) {
            let newRow = '<tr id="tr-'+user['id']+'">';
            let keys = Object.keys(user);
            for (let key of keys) {
                if (key !== 'password') {
                    if (key === 'rolesId') {
                        newRow += ('<td>' + user[key].map(x => x.replace('ROLE_', '')) + '</td>').replace(',', ' ');
                    } else {
                        newRow += '<td>' + user[key] + '</td>';
                    }
                }
            }
            newRow += '<td><button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#edit-modal-dialog" name="edit-button" id="'+user['id']+'">Edit</button></td>';
            newRow += '<td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#delete-modal-dialog" name="delete-button" id="'+user['id']+'">Delete</button></td>';
            $('tbody').append(newRow);
        }
        $('button[name="edit-button"], button[name="delete-button"]').on('click', function() {
            getAllUsers().then(users => {
                for (let user of users) {
                    if (user.id === parseInt(this.id)) {
                        let keys = Object.keys(user);
                        for (let key of keys) {
                            if (key !== 'password') {
                                $('#' + key + '1').val(user[key])
                                editUser[key] = user[key]

                                $('#' + key + '2').val(user[key])
                                deleteUser[key] = user[key]
                            }
                        }
                    }
                }
            })
        })
    })
}
showAllUsers()


////////////////////////        Создание и отправка нового чела         ///////////////////////////////
let newUserForm = $('#new-user-form')
let newUser = {}
newUserForm.change(function () {
    newUser.firstName = newUserForm.find('input[name="firstname"]').val();
    newUser.lastName = newUserForm.find('input[name="lastname"]').val();
    newUser.age = newUserForm.find('input[name="age"]').val();
    newUser.username = newUserForm.find('input[name="email"]').val();
    newUser.password = newUserForm.find('input[name="password"]').val();
    newUser.rolesId = newUserForm.find('select[name="rolesId"]').val();
})
$('#add-new-user').on('click', async function (){
    const headers = {
        'Content-Type': 'application/json'
    }
    let response = await fetch('http://localhost:8080/admin/create', {
        method: 'POST',
        body: JSON.stringify(newUser),
        headers: headers
    });
    $('#v-pills-home-tab').click();
    showAllUsers();
})


////////////////////////        Редактировнаие и отправка чела         ///////////////////////////////
let editUserForm = $('#edit-user-form')
editUserForm.change(function () {
    editUser.firstName = editUserForm.find('input[name="firstname"]').val();
    editUser.lastName = editUserForm.find('input[name="lastname"]').val();
    editUser.age = editUserForm.find('input[name="age"]').val();
    editUser.username = editUserForm.find('input[name="email"]').val();
    editUser.password = editUserForm.find('input[name="password"]').val();
    editUser.rolesId = editUserForm.find('select[name="rolesId"]').val();
})
$('#edit-user').on('click', async function (){
    const headers = {
        'Content-Type': 'application/json'
    }
    let response = await fetch('http://localhost:8080/admin/update', {
        method: 'PUT',
        body: JSON.stringify(editUser),
        headers: headers
    });
    $('#v-pills-home-tab').click();
    showAllUsers();
})

////////////////////////        Удаление чела         ///////////////////////////////
$('#delete-user').on('click', async function (){
    const headers = {
        'Content-Type': 'application/json'
    }
    let response = await fetch('http://localhost:8080/admin/delete', {
        method: 'DELETE',
        body: JSON.stringify(deleteUser),
        headers: headers
    });
    showAllUsers();
})
