package fr.ses10doigts.webApp2.model.payload;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import fr.ses10doigts.webApp2.model.TypeCeremonie;

public class CeremoniePayload {

    public String	 nom;
    public Integer	 prix;
    public TypeCeremonie type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date		 jour;
    public Integer	 ordre;

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public Integer getPrix() {
	return prix;
    }

    public void setPrix(Integer prix) {
	this.prix = prix;
    }

    public TypeCeremonie getType() {
	return type;
    }

    public void setType(TypeCeremonie type) {
	this.type = type;
    }

    public Date getJour() {
	return jour;
    }

    public void setJour(Date jour) {
	this.jour = jour;
    }

    public Integer getOrdre() {
	return ordre;
    }

    public void setOrdre(Integer ordre) {
	this.ordre = ordre;
    }

}
