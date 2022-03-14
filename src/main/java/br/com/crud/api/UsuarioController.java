package br.com.crud.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.crud.model.Usuario;
import br.com.crud.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private final PasswordEncoder encoder;
	
	public UsuarioController(PasswordEncoder encoder) {
		this.encoder = encoder;
	}
	
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }
    
    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return ResponseEntity.ok(usuarioRepository.save(usuario));
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
    
}
