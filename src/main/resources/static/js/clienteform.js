let boton = document.getElementById("botonRegistro");

boton.addEventListener("click", evento=>{
registrarCliente();
});

let registrarCliente = async() =>{

let campos = {};
campos.nombre = document.getElementById("nombre").value;
campos.apellidos = document.getElementById("apellidos").value;
campos.nif = document.getElementById("nif").value;
campos.email = document.getElementById("email").value;
campos.claveSeguridad = document.getElementById("claveSeguridad").value;

console.log(JSON.stringify(campos))
const peticion = await fetch('http://localhost:8888/ws/clientes/registro',
{
    method: 'POST',
    headers: {
        'Accept': 'application/json',
        'Content-Type':'application/json'
    },
    body: JSON.stringify(campos)
});
}