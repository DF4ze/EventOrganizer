package fr.ses10doigts.webApp2.model;

import java.util.Date;

import fr.ses10doigts.webApp2.security.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long   id;
    @ManyToOne
    private Participant	participant;
    @ManyToOne
    private User   writer;
    @Column(length = 50000)
    private String text;
    private Date   date;

    public Participant getParticipant() {
	return participant;
    }

    public void setParticipant(Participant participant) {
	this.participant = participant;
    }

    public User getWriter() {
	return writer;
    }

    public void setWriter(User writer) {
	this.writer = writer;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

}
