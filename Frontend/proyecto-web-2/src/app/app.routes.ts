import { LoginComponent } from './login/login';
import { Routes } from '@angular/router';
import { BannerUsuarioComponent } from './banner-usuario/banner-usuario';
import { BannerAdministrador } from './banner-administrador/banner-administrador';
import { AlquileresComponent } from './alquileres/alquileres';

export const routes: Routes = [
    {path: 'bannerUsuario',component: BannerUsuarioComponent },
    {path: 'bannerAdministrador',component: BannerAdministrador },
    {path:'login',component:LoginComponent},
    {path: 'alquiler',component:AlquileresComponent}
];
