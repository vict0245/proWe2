export class Usuario {
    identificacion: string;
    nombre: string;
    fechaExpedicion: string; // Se usa string porque los inputs de fecha en HTML manejan strings
    categoria: string;
    vigencia: string;
    email: string;
    telefono: string;
    password: string;
    alquileres: any[];    
}
