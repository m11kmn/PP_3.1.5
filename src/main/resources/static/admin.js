(async function showAllUsers() {
    let response = await fetch('http://localhost:8080/admin/js');
    let users = await response.json();
    $('#this-email').text(users[0].username)
    $('#this-roles').text(users[0].rolesId.map(x => x.replace('ROLE_', '')).toString().replace(',', ' '))

    for (let user of users) {
        let newRow = '<tr>';
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
        for (let user of users) {
            if (user.id === parseInt(this.id)) {
                let keys = Object.keys(user);

                let editUser = {
                }
                let deletedUser = {
                }

                for (let key of keys) {
                    if (key !== 'password') {
                        $('#'+key+'1').val(user[key])
                        editUser[key] = user[key]

                        $('#'+key+'2').val(user[key])
                        deletedUser[key] = user[key]
                    }
                }

                $('#firstName1').change(function () {editUser.firstName = $('#firstName1').val()})
                $('#lastName1').change(function () {editUser.lastName = $('#lastName1').val()})
                $('#age1').change(function () {editUser.age = $('#age1').val()})
                $('#username1').change(function () {editUser.username = $('#username1').val()})
                $('#password1').change(function () {editUser.password = $('#password1').val()})
                $('#rolesId1').change(function () {editUser.rolesId = $('#rolesId1').val()})

                $('#edit-user').on('click', async function (){
                    const headers = {
                        'Content-Type': 'application/json'
                    }

                    let response = await fetch('http://localhost:8080/admin/js', {
                        method: 'PUT',
                        body: JSON.stringify(editUser),
                        headers: headers
                    });
                })

                $('#delete-user').on('click', async function (){
                    const headers = {
                        'Content-Type': 'application/json'
                    }

                    let response = await fetch('http://localhost:8080/admin/js', {
                        method: 'DELETE',
                        body: JSON.stringify(deletedUser),
                        headers: headers
                    });
                })

            }
        }
    })
})();

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
    let response = await fetch('http://localhost:8080/admin/js', {
        method: 'POST',
        body: JSON.stringify(newUser),
        headers: headers
    });
    console.log(newUser)
})