import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { VehiculoComponente } from "./vehiculo-componente/vehiculo-componente";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, VehiculoComponente],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'proyecto-web-2';
}
