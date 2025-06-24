import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Vehiculos {
  idVehiculo: number;
  placa: string;
  marca: string;
  modelo: string;
  color: string;
  estado: string;
  valorAlquilerDia: number;
  tipo: string;
  img: File;

  constructor() {}
}
