package br.unibh.loja.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="tb_cliente", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"login"}),
		@UniqueConstraint(columnNames = {"cpf"})
})

public class Cliente {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@NotEmpty
	@Size(min=3, max=100)
	@Pattern(regexp="[A-zÀ-ú.´ ]*", message="Caracteres permitidos: letras, espaços, ponto e aspas simples")
	@Column(length=100, nullable=false)
	private String nome;
	
	@NotBlank
	@NotEmpty
	@Size(min=8, max=15)
	@Pattern(regexp="[A-z0-9]*", message="Caracteres permitidos: Caracteres Alfanúmericos")
	@Column(length=15, nullable=false)
	private String login;
	
	@NotBlank
	@NotEmpty
	@Size(min=4, max=100)
	@Column(length=100, nullable=false)
	private String senha;
	
	@NotEmpty
	@Size(max=100)
	@Pattern(regexp="[A-zÀ-ú ]*", message="Caracteres permitidos: letras e espaços")
	@NotBlank
	@Column(length=100, nullable=false)
	private String perfil;
	
	@NotNull
	@Column(columnDefinition="CHAR(11)", nullable=false)
	@Pattern(regexp="\\d{11}\\d{0,1}", message="Fornecer 11 dígitos sem caracteres de separação")
	private String cpf;
	
	@Column(columnDefinition="CHAR(14)", nullable=false)
	@Pattern(regexp="\\(\\d{2}\\)\\d{0,1}\\d{4}-\\d{4}", message="Fornecer um telefone no formato (99)09999-9999")
	private String telefone;
	
	@Column(length=100, nullable=false)
	@Email(message = "Email deve ser válido")
	private String email;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_nascimento", nullable=false)
	private Date dataNascimento;
	
	@NotNull
	@Column(name="data_cadastro", nullable=false)
	private Date dataCadastro;
	
	@Version
	private Long version;
	
	
	
	public Cliente(Long id, String nome, String login, String senha, String perfil, String cpf, String telefone,
			String email, Date dataNascimento, Date dataCadastro) {
		super();
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.perfil = perfil;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.dataCadastro = dataCadastro;
	}
	
	public Cliente() {
		super();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((perfil == null) ? 0 : perfil.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (perfil == null) {
			if (other.perfil != null)
				return false;
		} else if (!perfil.equals(other.perfil))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", login=" + login + ", senha=" + senha + ", perfil=" + perfil
				+ ", cpf=" + cpf + ", telefone=" + telefone + ", email=" + email + ", dataNascimento=" + dataNascimento
				+ ", dataCadastro=" + dataCadastro + "]";
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
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version){
		this.version = version;
	}
}
