package fr.ses10doigts.webApp2.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

/**
 * @author df4ze
 *
 */
@Entity
@Table(uniqueConstraints = {
	@UniqueConstraint(name = "UniqueParticipantCeremonie", columnNames = { "participant_id", "ceremonie_id" }) })
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long	id;

    private Integer	quantite    = 1;
    private Integer	prix;
    @ManyToOne
    @NotNull
    private Participant	participant;
    @OneToOne
    @Nullable
    private Souhait	souhait;
    @OneToOne
    @NotNull
    private Ceremonie	ceremonie;

    private boolean	prixModifie = false;
    private boolean	fait	    = false;

    private boolean	actif = true;


    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Integer getQuantite() {
	return quantite;
    }

    public void setQuantite(Integer quantite) {
	this.quantite = quantite;
    }

    public Integer getPrix() {
	return prix;
    }

    public void setPrix(Integer prix) {
	this.prix = prix;
    }

    public boolean isActif() {
	return actif;
    }

    public void setActif(boolean actif) {
	this.actif = actif;
    }
    public Participant getParticipant() {
	return participant;
    }

    public void setParticipant(Participant participant) {
	this.participant = participant;
    }

    public Souhait getSouhait() {
	return souhait;
    }

    public void setSouhait(Souhait souhait) {
	this.souhait = souhait;
    }

    public Ceremonie getCeremonie() {
	return ceremonie;
    }

    public void setCeremonie(Ceremonie ceremonie) {
	this.ceremonie = ceremonie;
    }

    public boolean isPrixModifie() {
	return prixModifie;
    }

    public void setPrixModifie(boolean prixModifie) {
	this.prixModifie = prixModifie;
    }

    public boolean isFait() {
	return fait;
    }

    public void setFait(boolean fait) {
	this.fait = fait;
    }

    @Override
    public String toString() {
	return "Participation [id=" + id + ", quantite=" + quantite + ", prix=" + prix + ", participant=" + participant
		+ ", ceremonie=" + ceremonie + "]";
    }

}
