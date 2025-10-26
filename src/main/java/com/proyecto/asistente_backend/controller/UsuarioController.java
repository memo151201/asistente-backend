package com.proyecto.asistente_backend.controller;


import com.proyecto.asistente_backend.model.Usuario;
import com.proyecto.asistente_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // GET: Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    // GET: Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Obtener usuario por email
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> obtenerPorEmail(@PathVariable String email) {
        return usuarioService.obtenerPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Crear usuario
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT: Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Desactivar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarUsuario(@PathVariable Long id) {
        try {
            usuarioService.desactivarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET: Obtener usuarios activos
    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> obtenerActivos() {
        return ResponseEntity.ok(usuarioService.obtenerActivos());
    }

    // GET: Obtener usuarios por rol
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Usuario>> obtenerPorRol(@PathVariable String rol) {
        Usuario.RolUsuario rolUsuario = Usuario.RolUsuario.valueOf(rol.toUpperCase());
        return ResponseEntity.ok(usuarioService.obtenerPorRol(rolUsuario));
    }
}