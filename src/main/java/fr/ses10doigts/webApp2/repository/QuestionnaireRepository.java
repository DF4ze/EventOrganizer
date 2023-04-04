package fr.ses10doigts.webApp2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.ses10doigts.webApp2.model.Questionnaire;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

    @Query(value = "SELECT q FROM Questionnaire q WHERE q.participant.actif = true ORDER BY q.participant.prenom ASC")
    List<Questionnaire> findByActivParticipant();

}
