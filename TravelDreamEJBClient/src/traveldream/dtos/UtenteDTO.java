package traveldream.dtos;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class UtenteDTO{


	@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message="invalid email")
	private String email;
	
	@NotEmpty
    private String nome;
	
	@NotEmpty
    private String cognome;
	
	@NotEmpty
    private String dataNascita;
	
	@NotEmpty
    private String indirizzo;
	
	@NotEmpty
    private String password;
	
     
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
    
    public String getDataNascita() {
        return dataNascita;
    } 
    
    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }
    
    public String getIndirizzo() {
        return indirizzo;
    } 
    
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    
  
    public String getPassword() {
        return password;
    }
     
    public void setPassword(String password) {
        this.password = password;
    }
    
    //DEBUG
    public void printaDati() {
		System.out.println(this.getNome());
		System.out.println(this.getCognome());
		System.out.println(this.getEmail());
		System.out.println(this.getIndirizzo());
		System.out.println(this.getPassword());
	}
     

}