document.addEventListener('DOMContentLoaded', function() {
    cargarClientes();

    /*document.getElementById('nuevo-cliente-form').addEventListener('submit', function(event) {
        event.preventDefault();
        agregarCliente();
    });*/
});

function cargarClientes() {
    fetch('http://localhost:8888/ws/clientes')
        .then(response => response.json())
        .then(clientes => mostrarClientes(clientes))
        .catch(error => console.error('Error al cargar clientes:', error));
}

function mostrarClientes(clientes) {
    const tablaClientes = document.getElementById('datos').getElementsByTagName('tbody')[0];
    tablaClientes.innerHTML = '';

    clientes.forEach(cliente => {
        const row = tablaClientes.insertRow();
        row.insertCell(0).textContent = cliente.id;
        row.insertCell(1).textContent = cliente.nombre;
        row.insertCell(2).textContent = cliente.apellidos;
        row.insertCell(3).textContent = cliente.email;

        // Celda con botón de editar
                const celdaEditar = row.insertCell(4);
                const botonEditar = document.createElement('a');
                botonEditar.textContent = 'Editar';
                botonEditar.href = '/clientes/update/' + cliente.id;  // Utilizamos Thymeleaf para construir la URL
                celdaEditar.appendChild(botonEditar);

        // Celda con botón de eliminar
        const celdaEliminar = row.insertCell(5);
        const botonEliminar = document.createElement('button');
        botonEliminar.textContent = 'Eliminar';
        botonEliminar.addEventListener('click', function() {
            // Obtener el ID del cliente
            const idCliente = cliente.id;

            // Confirmar la eliminación (puedes personalizar esto según tus necesidades)
            const confirmacion = confirm(`¿Estás seguro de eliminar al cliente con ID ${idCliente}?`);
            if (confirmacion) {
                // Realizar la petición DELETE a la URL correspondiente
                fetch(`http://localhost:8888/ws/clientes/${idCliente}`, {
                    method: 'DELETE',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    }
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Error al eliminar el cliente. Estado: ${response.status}`);
                    }
                    // Puedes realizar acciones adicionales después de la eliminación
                    console.log(`Cliente con ID ${idCliente} eliminado correctamente.`);
                    // Por ejemplo, recargar la lista de clientes
                    cargarClientes();
                })
                .catch(error => {
                    console.error(error);
                });
            }
        });
        celdaEliminar.appendChild(botonEliminar);
    });
}

function agregarCliente() {
    const nombre = document.getElementById('nombre').value;
    const apellidos = document.getElementById('apellidos').value;
    const email = document.getElementById('email').value;

    const nuevoCliente = {
        nombre: nombre,
        apellidos: apellidos,
        email: email
    };

    fetch('http://localhost:8888/ws/clientes/registro', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(nuevoCliente)
    })
    .then(response => response.json())
    .then(cliente => {
        console.log('Cliente registrado:', cliente);
        cargarClientes();
    })
    .catch(error => console.error('Error al agregar cliente:', error));
}
