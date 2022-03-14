package br.com.crud.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TB_USUARIO")
@SequenceGenerator(allocationSize = 1, name = "usuario", sequenceName = "sq_usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(generator = "usuario", strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private Long id;
	
	@Column(name = "nm_nome")
	private String nome;
	
	@Column(name = "ds_cpf")
	private String cpf;
	
	@Column(name = "ds_senha")
	private String senha;
	
	public Usuario() {
		super();
	}

	public Usuario(Long id, String nome, String cpf, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
