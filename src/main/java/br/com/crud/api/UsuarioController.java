package br.com.crud.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.crud.model.Usuario;
import br.com.crud.repository.UsuarioRepository;
import br.com.crud.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	private final PasswordEncoder encoder;
	
	public UsuarioController(PasswordEncoder encoder) {
		this.encoder = encoder;
	}
	
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarTodos() {
    	List<Usuario> usuarios = new ArrayList();
    	usuarios = usuarioService.buscarUsuarios();
        return ResponseEntity.ok().body(usuarios);
    }
    
    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        usuarioService.createUsuario(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.ok(usuario);
    }
    
    @GetMapping("/login")
    public ResponseEntity<Boolean> validaLogin(@RequestParam String cpf, @RequestParam String senha) {

        Optional<Usuario> optUsuario = usuarioRepository.findByCpf(cpf);
        if (optUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        Usuario usuario = optUsuario.get();
        boolean valid = encoder.matches(senha, usuario.getSenha());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }
    
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> deleteUsuarioById(@PathVariable Long id) {
		usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> updateUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
		Usuario usuarioUpdate = usuario;
		usuarioUpdate.setId(id);
		usuarioService.update(usuarioUpdate);
		
		return ResponseEntity.noContent().build();
	}
}
