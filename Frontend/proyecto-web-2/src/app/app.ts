import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { VehiculoComponente } from "./vehiculo-componente/vehiculo-componente";
import { LoginComponent } from './login/login';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LoginComponent,FormsModule,VehiculoComponente],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'proyecto-web-2';
}
