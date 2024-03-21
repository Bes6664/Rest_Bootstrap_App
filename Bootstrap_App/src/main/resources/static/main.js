function getAllUsers() {
    fetch("http://localhost:8080/api/users")
        .then(res => res.json())
        .then(users => {
            let temp = '';
            console.log(users);
            users.forEach(function (user) {
                temp += `
                <tr>
                <td id="id${user.id}">${user.id}</td>
                <td id="first_name${user.id}">${user.firstName}</td>
                <td id="last_name${user.id}">${user.lastName}</td>
                <td id="username0${user.id}">${user.username}</td>
                <td id="roles${user.id}">${user.authorities.map(r => r.role.replace("ROLE_", ""))}</td>
                <td>
                <button class="btn btn-info btn-md" type="button"
                data-toggle="modal" data-target="#editUser"
                onclick="openModal(${user.id})">Edit</button></td>
                <td>
                <button class="btn btn-danger btn-md" type="button"
                data-toggle="modal" data-target="#deleteUser"
                onclick="openModal(${user.id})">Delete</button></td>
              </tr>`;
            });
            console.log()
            document.getElementById("tbodyAllUserTable").innerHTML = temp;
        });
}
getAllUsers()

function openModal(id) {
    fetch("http://localhost:8080/api/user/" + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(u => {
            console.log(u);
            document.getElementById('idEditUser').value = u.id;
            document.getElementById('usernameEditUser').value = u.username;
            document.getElementById('passwordEditUser').value = u.password;
            document.getElementById('firstNameEditUser').value = u.firstName;
            document.getElementById('lastNameEditUser').value = u.lastName;
            document.getElementById('rolesEditUser').value = u.authorities;

            document.getElementById('idDeleteUser').value = u.id;
            document.getElementById('usernameDeleteUser').value = u.username;
            document.getElementById('firstNameDeleteUser').value = u.firstName;
            document.getElementById('lastNameDeleteUser').value = u.lastName;
            document.getElementById('rolesDeleteUser').value = u.roles;
        })
    });
}

document.getElementById("editUserForm")
    .addEventListener("submit", editUser);

async function editUser() {
    event.preventDefault()
    await fetch("http://localhost:8080/api/user/" + document.getElementById("idEditUser").value + "/edit", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            id: document.getElementById('idEditUser').value,
            username: document.getElementById('usernameEditUser').value,
            password: document.getElementById('passwordEditUser').value,
            firstName: document.getElementById('firstNameEditUser').value,
            lastName: document.getElementById('lastNameEditUser').value,
            roles: getRoles(document.getElementById('rolesEditUser'))
        })
    }).then(response => console.log(response));
    $("#editUser .close").click();
    refreshTable();
}

async function deleteUser() {
    event.preventDefault()
    await fetch("http://localhost:8080/api/user/" + document.getElementById("idDeleteUser").value + "/delete", {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
    })

    $("#deleteUser .close").click();
    refreshTable();
}

function addNewUser() {
    event.preventDefault()
    let username = document.getElementById('newUserUsername').value;
    let password = document.getElementById('newUserPassword').value;
    let firstName = document.getElementById('newUserFirstName').value;
    let lastName = document.getElementById('newUserLastName').value;
    let roles = getRoles(document.getElementById('newUserRoles'));
    fetch("http://localhost:8080/api/create", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            firstName: firstName,
            lastName: lastName,
            username: username,
            password: password,
            roles: roles
        })
    })
        .then(() => {
            document.getElementById("allUsersTable").click();
            getAllUsers();
            document.getElementById("formNewUser").reset();
        })
}

function getRoles(selector) {
    let collection = selector.selectedOptions
    let roles = []
    for (let i = 0; i < collection.length; i++) {
        if (collection[i].value === '1') {
            roles.push({
                id: 1,
                name: 'ROLE_USER'
            })
        } else if (collection[i].value === '2') {
            roles.push({
                id: 2,
                name: 'ROLE_ADMIN'
            })
        }
    }
    console.log('--------------------------')
    console.log(roles)
    return roles
}

function refreshTable() {
    let table = document.getElementById('tbodyAllUserTable')
    while (table.rows.length > 1) {
        table.deleteRow(1)
    }
    getAllUsers()
}