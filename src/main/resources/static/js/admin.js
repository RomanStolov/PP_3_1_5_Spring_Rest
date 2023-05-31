const urlGetUser = 'http://localhost:8080/user';
const urlGetUsers = 'http://localhost:8080/admin/users';
const urlGetRoles = 'http://localhost:8080/admin/roles';
const urlGetUserById = 'http://localhost:8080/admin/users/';
const urlPostUsers = 'http://localhost:8080/admin/users';
const urlDeleteUsers = 'http://localhost:8080/admin/users/';
const urlPutUsers = 'http://localhost:8080/admin/users/';

const tableBodyUser = document.getElementById('tableBodyUser');
const tableBodyUsers = document.getElementById('tableBodyUsers');
const navbarBrand = document.getElementById('navbarBrand');
const formEdit = document.getElementById('formEdit');
const formDelete = document.getElementById('formDelete');
const formNew = document.getElementById('formNew');

formEdit.addEventListener('submit', async (event) => {
    event.preventDefault();
    let roles = Array
        .from(document.getElementById('editRoleEditUser').options)
        .filter(option => option.selected)
        .map(option => ({id: option.value, name: `ROLE_${option.text}`}));
    let id = document.getElementById('noEditIdEditUser').value;
    let username = document.getElementById('editUsernameEditUser').value;
    let surname = document.getElementById('editSurnameEditUser').value;
    let age = document.getElementById('editAgeEditUser').value;
    let email = document.getElementById('editEmailEditUser').value;
    let password = document.getElementById('noEditPasswordEditUser').value;
    await fetch(
        urlPutUsers + id,
        {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(
                {
                    id: id,
                    username: username,
                    surname: surname,
                    age: age,
                    email: email,
                    password: password,
                    roles: roles
                })
        })
        .then(() => {
            formEdit.reset();
            getUsers();
            $('#editClose').click();
            $('#nav-home-tab').click();
        })
        .catch((error) => {
            alert(error);
        })
})

formNew.addEventListener('submit', async (event) => {
    event.preventDefault();
    let roles = Array
        .from(document.getElementById('newUserRole').options)
        .filter(option => option.selected)
        .map(option => `ROLE_${option.text}`);
    let username = document.getElementById('newUserUsername').value;
    let surname = document.getElementById('newUserSurname').value;
    let age = document.getElementById('newUserAge').value;
    let email = document.getElementById('newUserEmail').value;
    let password = document.getElementById('newUserPassword').value;
    await fetch(
        urlPostUsers + `?roles=${roles}`,
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(
                {
                    username: username,
                    surname: surname,
                    age: age,
                    email: email,
                    password: password
                })
        })
        .then(() => {
            formNew.reset();
            getUsers();
            $('#nav-home-tab').click();
        })
        .catch((error) => {
            alert(error);
        })
})

formDelete.addEventListener('submit', async (event) => {
    event.preventDefault();
    let id = document.getElementById('noEditIdDeleteUser').value;
    await fetch(
        urlDeleteUsers + id,
        {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            }
        })
        .then(() => {
            formDelete.reset();
            getUsers();
            $('#deleteClose').click();
            $('#nav-home-tab').click();
        })
        .catch((error) => {
            alert(error);
        })
})

async function getCurrentUser() {
    const response = await fetch(urlGetUser);
    if (response.ok) {
        const user = await response.json();
        insertCodeTableBodyUser(user);
        insertCodeNavbarBrand(user);
    } else {
        alert('Ошибка при получении текущего пользователя: ' + response.status);
    }
}

getCurrentUser()

async function setRolesToFormNew() {
    const response = await fetch(urlGetRoles);
    if (response.ok) {
        const roles = await response.json();
        let insertCode = '';
        for (const role of roles) {
            let value = role.id;
            let text = role.name;
            text = text.toString().substring(5, text.length);
            insertCode += `<option value="${value}">${text}</option>`;
        }
        document.getElementById('newUserRole').innerHTML = insertCode;
    } else {
        alert('Ошибка при получении списка ролей: ' + response.status);
    }
}

setRolesToFormNew()

async function getUsers() {
    const response = await fetch(urlGetUsers);
    if (response.ok) {
        const users = await response.json();
        insertCodeTableBodyUsers(users);
    } else {
        alert('Ошибка при получении списка всех пользователей: ' + response.status);
    }
}

getUsers()

function insertCodeTableBodyUsers(users) {
    let insertCode = '';
    let rolesString = '';
    for (const user of users) {
        rolesString = rolesToString(user.roles);
        insertCode += `<tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.surname}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${rolesString}</td>
                        <!--********** Кнопка "Edit"-->
                        <td>
                            <button type="button" class="btn btn-info" id="${'#editModal' + user.id}"
                            onclick="startEdit(${user.id})">
                                Edit
                            </button>
                        </td>
                        <!--********** Кнопка "Delete"-->
                        <td>
                            <button type="button" class="btn btn-danger" id="${'#deleteModal' + user.id}"
                            onclick="startDelete(${user.id})">
                                Delete
                            </button>
                        </td>
                    </tr>`;
    }
    tableBodyUsers.innerHTML = insertCode;
}

async function getUserById(id) {
    const response = await fetch(urlGetUserById + id);
    if (response.ok) {
        return await response.json();
    } else {
        alert('Ошибка при получении пользователя по "id": ' + response.status);
    }
}

async function startEdit(id) {
    const user = await getUserById(id);
    document.getElementById('noEditIdEditUser').value = user.id;
    document.getElementById('editUsernameEditUser').value = user.username;
    document.getElementById('editSurnameEditUser').value = user.surname;
    document.getElementById('editAgeEditUser').value = user.age;
    document.getElementById('editEmailEditUser').value = user.email;
    document.getElementById('noEditPasswordEditUser').value = user.password;
    const response = await fetch(urlGetRoles);
    if (response.ok) {
        const roles = await response.json();
        let insertCode = '';
        for (const role1 of roles) {
            let value = role1.id;
            let text = role1.name;
            text = text.toString().substring(5, text.length);
            let selected = '';
            for (const role2 of user.roles) {
                if (role1.name === role2.name) {
                    selected = 'selected';
                    break;
                }
            }
            insertCode += `<option ${selected} value="${value}">${text}</option>`;
        }
        document.getElementById('editRoleEditUser').innerHTML = insertCode;
        $('#editModal').modal('show');
    } else {
        alert('Ошибка при получении списка ролей: ' + response.status);
    }
}

async function startDelete(id) {
    const user = await getUserById(id);
    document.getElementById('noEditIdDeleteUser').value = user.id;
    document.getElementById('noEditUsernameDeleteUser').value = user.username;
    document.getElementById('noEditSurnameDeleteUser').value = user.surname;
    document.getElementById('noEditAgeDeleteUser').value = user.age;
    document.getElementById('noEditEmailDeleteUser').value = user.email;
    document.getElementById('noEditPasswordDeleteUser').value = user.password;
    const response = await fetch(urlGetRoles);
    if (response.ok) {
        const roles = await response.json();
        let insertCode = '';
        for (const role1 of roles) {
            let value = role1.id;
            let text = role1.name;
            text = text.toString().substring(5, text.length);
            let selected = '';
            for (const role2 of user.roles) {
                if (role1.name === role2.name) {
                    selected = 'selected';
                    break;
                }
            }
            insertCode += `<option ${selected} value="${value}">${text}</option>`;
        }
        document.getElementById('noEditRoleDeleteUser').innerHTML = insertCode;
        $('#deleteModal').modal('show');
    } else {
        alert('Ошибка при получении списка ролей: ' + response.status);
    }
}

function insertCodeTableBodyUser(user) {
    let rolesString = rolesToString(user.roles);
    tableBodyUser.innerHTML = `<tr>
                                <td>${user.id}</td>
                                <td>${user.username}</td>
                                <td>${user.surname}</td>
                                <td>${user.age}</td>
                                <td>${user.email}</td>
                                <td>${rolesString}</td>
                            </tr>`;
}

function insertCodeNavbarBrand(user) {
    let rolesString = rolesToString(user.roles);
    navbarBrand.innerHTML = `<b><span>${user.username}</span></b>
                            <span>with roles:</span>
                            <span>${rolesString}</span>`;
}

function rolesToString(roles) {
    let rolesString = '';
    for (const element of roles) {
        rolesString += (element.name.toString().replace('ROLE_', '') + ', ');
    }
    rolesString = rolesString.substring(0, rolesString.length - 2);
    return rolesString;

}