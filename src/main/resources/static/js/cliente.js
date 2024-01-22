async function eliminarCliente(idCliente) {
console.log("Eliminando el cliente " + idCliente)
const peticion = await fetch('http://localhost:8888/ws/clientes/'+idCliente,
{
    method: 'DELETE',
    headers: {
        'Accept': 'application/json',
        'Content-Type':'application/json'
    }
});
console.log("Eliminado correctamente")

location.reload()
}




