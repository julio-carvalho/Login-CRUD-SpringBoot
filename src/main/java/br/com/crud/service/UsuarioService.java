package br.com.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crud.model.Usuario;
import br.com.crud.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario createUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public Usuario salvaUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public List<Usuario> buscarUsuarios() {
		return usuarioRepository.findAll();
	}
	
	public Optional<Usuario> findById(Long id) {
		return usuarioRepository.findById(id);
	}

	public void delete(Long id) {
		usuarioRepository.delete(this.getUsuario(id));
	}
	
	public Usuario getUsuario(Long id) {
		return usuarioRepository.getById(id);
	}
	
	public void update (Usuario Usuario) {
		Optional<Usuario> UsuarioUpdate = usuarioRepository.findById(Usuario.getId());
		this.atualizarDados(UsuarioUpdate, Usuario);
		usuarioRepository.save(UsuarioUpdate.get());
	}
	
	private void atualizarDados(Optional<Usuario> UsuarioUpdate, Usuario Usuario) {
		UsuarioUpdate.get().setNome(Usuario.getNome());
		UsuarioUpdate.get().setCpf(Usuario.getCpf());
		UsuarioUpdate.get().setSenha(Usuario.getSenha());
	}
}
