import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-nav-usuario',
  imports: [],
  templateUrl: './nav-usuario.html',
  styleUrl: './nav-usuario.css'
})
export class NavUsuarioComponent {
  @Output() reloandAfterInit = new EventEmitter<void>();

  onReloandAfterinit(){
    this.reloandAfterInit.emit();
  }
}
