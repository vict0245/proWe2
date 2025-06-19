import { LoginComponent } from './login/login';
import { Routes } from '@angular/router';
import { BannerUsuarioComponent } from './banner-usuario/banner-usuario';
import { BannerAdministrador } from './banner-administrador/banner-administrador';

export const routes: Routes = [
    {path: 'bannerUsuario',component: BannerUsuarioComponent },
    {path: 'bannerAdministrador',component: BannerAdministrador },
    {path:'login',component:LoginComponent}
];
