import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { Output, EventEmitter } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-nav-admin',
  imports: [RouterOutlet],
  templateUrl: './nav-admin.html',
  styleUrl: './nav-admin.css'
})
export class NavAdminComponent implements OnInit {
  @Output() abrirModalAlquiladosRequest = new EventEmitter<void>();
  @Output() abrirModalDisponiblesRequest = new EventEmitter<void>();
  @Output() AbrirDivVehiculoRequest = new EventEmitter<void>();
  @Output() abrirModalPendientesRequest = new EventEmitter<void>();

  constructor(private router:Router) { }
  ngOnInit(): void {
  }

  onAbrirModalAlquiladosClick(){
    this.abrirModalAlquiladosRequest.emit();
  }

  onAbrirModalDisponiblesClick(){
    this.abrirModalDisponiblesRequest.emit();
  }

  onAbrirDivVehiculo(){
    this.AbrirDivVehiculoRequest.emit();
  }
  onAbrirModalPendientesClick(){
    this.abrirModalPendientesRequest.emit();
  }
}
