package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.Usuarios;
import com.example.demo.repositorio.UsuariosRepositorio;

@RestController
@RequestMapping("/paginaRegistro")

public class UsuariosControlador {

	@Autowired
	private UsuariosRepositorio usuariorepositorio;
	
	@GetMapping("/verUsuarios")
	public List <Usuarios> verTodosUsuarios(){
		return usuariorepositorio.findAll();
	}
	
	@PostMapping("/registrarUsuario")
	public ResponseEntity<Usuarios> registroUsuario(@RequestBody Usuarios usuario){
		try {
			Usuarios nuevoUsuario = this.usuariorepositorio.save(usuario);
			return ResponseEntity.ok(nuevoUsuario);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
	