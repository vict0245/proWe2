import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AlquileresComponent } from './alquileres/alquileres';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, AlquileresComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'proyecto-web-2';
}
