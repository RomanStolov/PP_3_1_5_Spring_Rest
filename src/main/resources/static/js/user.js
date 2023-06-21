const urlGetUser = 'http://localhost:8080/api/user';

const tableBodyUser = document.getElementById('tableBodyUser');
const navbarBrand = document.getElementById('navbarBrand');

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
    navbarBrand.innerHTML = `<b> <span>${user.username}</span></b>
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