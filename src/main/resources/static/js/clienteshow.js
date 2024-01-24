document.addEventListener('DOMContentLoaded', function() {
    // Obtener el ID del cliente de la URL
    const urlParams = new URLSearchParams(window.location.search);
    const idCliente = urlParams.get('id');

    // Si hay un ID de cliente, cargar la información del cliente
    if (idCliente) {
        cargarCliente(idCliente);
    }
});

function cargarCliente(idCliente) {
    // Realizar una solicitud para obtener la información del cliente con el ID proporcionado
    fetch('http://localhost:8888/ws/clientes/' + idCliente)
        .then(response => response.json())
        .then(cliente => mostrarCliente(cliente))
        .catch(error => console.error('Error al cargar cliente:', error));
}

function mostrarCliente(cliente) {
    // Llenar los campos del formulario con la información del cliente
    document.getElementById('id').value = cliente.id;
    document.getElementById('nombre').value = cliente.nombre;
    document.getElementById('apellidos').value = cliente.apellidos;
    document.getElementById('email').value = cliente.email;
    // Otros campos según sea necesario
}
