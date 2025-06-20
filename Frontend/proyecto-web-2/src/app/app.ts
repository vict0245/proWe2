import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoginComponent } from './login/login';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-root',
  imports: [RouterOutlet, LoginComponent,FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'proyecto-web-2';
}