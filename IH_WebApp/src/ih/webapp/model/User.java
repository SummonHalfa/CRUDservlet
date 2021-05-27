package ih.webapp.model;

public class User {
	private int id;
	private String nome;
	private String cognome;
	private String email;
	private String residenza;
	
	
	public User(int id, String nome, String cognome, String email, String residenza) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.residenza = residenza;
	}
	
	
	public User(String nome, String cognome, String email, String residenza) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.residenza = residenza;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getResidenza() {
		return residenza;
	}
	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}
	
	
}
