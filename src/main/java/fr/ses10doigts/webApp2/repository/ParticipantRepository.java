package fr.ses10doigts.webApp2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.ses10doigts.webApp2.model.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Optional<Participant> findByNomAndPrenom(String nom, String prenom);

    @Override
    @Query(value = "SELECT p FROM Participant p ORDER BY p.prenom ASC")
    List<Participant> findAll();

    @Query(value = "SELECT p FROM Participant p WHERE p.actif = true ORDER BY p.prenom ASC")
    List<Participant> findAllByActif();

}
