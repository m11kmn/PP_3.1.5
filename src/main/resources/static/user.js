(async function showAllUsers() {
    let response = await fetch('http://localhost:8080/user/js');
    let user = await response.json();
    $('#this-email').text(user.username)
    $('#this-roles').text(user.rolesId.map(x => x.replace('ROLE_', '')).toString().replace(',', ' '))

    if (!user.rolesId.includes('ROLE_ADMIN')) {$('#admin-button').hide()}

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
    $('tbody').append(newRow);
})();