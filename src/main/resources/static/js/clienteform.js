document.addEventListener('DOMContentLoaded', function() {
    // Obtener el ID del cliente de la URL
    const urlPath = window.location.pathname;
    const idCliente = urlPath.split('/').pop(); // Obtener el último segmento de la URL

    if(idCliente == "add"){
        document.getElementById("botonActualizar").style.display = "none";
        // Registrar Cliente
        const botonRegistro = document.getElementById('botonRegistro');
        botonRegistro.addEventListener('click', function() {
            // Obtener los valores del formulario
            const nombre = document.getElementById('nombre').value;
            const apellidos = document.getElementById('apellidos').value;
            const nif = document.getElementById('nif').value;
            const email = document.getElementById('email').value;
            const claveSeguridad = document.getElementById('claveSeguridad').value;

            // Construir el objeto con los datos del nuevo cliente
            const nuevoCliente = {
                "nombre": nombre,
                "apellidos": apellidos,
                "nif": nif,
                "email": email,
                "claveSeguridad": claveSeguridad
            };

            console.log(nuevoCliente);

            // Realizar la solicitud para registrar el nuevo cliente
            fetch('http://localhost:8888/ws/clientes/registro', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(nuevoCliente)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Error al registrar el cliente. Estado: ${response.status}`);
                }
                return response.json();
            })
            .then(clienteRegistrado => {
                console.log('Cliente registrado:', clienteRegistrado);
                // Puedes hacer algo después de registrar el cliente, como redirigir a la lista de clientes
                window.location.href = '/clientes';
            })
            .catch(error => {
                console.error(error);
            });
        });
    } else {
        document.getElementById("botonRegistro").style.display = "none";
        // Asegúrate de convertir idCliente a un número si es necesario
        const idClienteNumero = parseInt(idCliente, 10); // o Number(idCliente)

        // Realizar una solicitud para obtener los detalles del cliente con el ID proporcionado
        fetch(`http://localhost:8888/ws/clientes/${idClienteNumero}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Error al obtener los detalles del cliente. Estado: ${response.status}`);
                }
                return response.json();
            })
            .then(cliente => {
                // Llenar el formulario con los detalles del cliente
                document.getElementById('nombre').value = cliente.nombre;
                document.getElementById('apellidos').value = cliente.apellidos;
                document.getElementById('nif').value = cliente.nif;
                document.getElementById('email').value = cliente.email;
                document.getElementById('claveSeguridad').value = cliente.claveSeguridad;
            })
            .catch(error => {
                console.error(error);
            });
            //ACTUALIZAR CLIENTE
            const clienteForm = document.getElementById('clienteForm');
            const botonActualizar = document.getElementById('botonActualizar');
            // Obtener el ID del cliente de la URL
            const urlParams = new URLSearchParams(window.location.search);

            // Agregar evento input a los campos del formulario
            clienteForm.addEventListener('input', function() {
             // Habilitar el botón "Actualizar" cuando se realicen cambios en el formulario
             botonActualizar.removeAttribute('disabled');
            });

             // Agregar evento submit al formulario para manejar la actualización
            clienteForm.addEventListener('submit', function(event) {
                event.preventDefault(); // Evitar el envío del formulario por defecto

                // Obtener los valores actualizados del formulario
                const nombre = document.getElementById('nombre').value;
                const apellidos = document.getElementById('apellidos').value;
                const nif = document.getElementById('nif').value;
                const email = document.getElementById('email').value;
                const claveSeguridad = document.getElementById('claveSeguridad').value;

                // Construir el objeto con los datos actualizados
                const datosActualizados = {
                "nombre": nombre,
                "apellidos": apellidos,
                "nif": nif,
                "email": email,
                "claveSeguridad": claveSeguridad
                };

                console.log(datosActualizados)

                // Realizar la solicitud para actualizar el cliente con el ID proporcionado
                fetch(`http://localhost:8888/ws/clientes/${idClienteNumero}`, {
                    method: 'PUT',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(datosActualizados)
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Error al actualizar el cliente. Estado: ${response.status}`);
                    }
                    return response.json();
                })
                .then(clienteActualizado => {
                    console.log('Cliente actualizado:', clienteActualizado);

                    botonActualizar.setAttribute('disabled', 'disabled'); // Desactivar el botón después de la actualización
                })
                .catch(error => {
                    console.error(error);
                });
            });
    }

});
