package fr.ses10doigts.webApp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.ses10doigts.webApp2.model.Participation;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {

}
