package fr.ses10doigts.webApp2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.ses10doigts.webApp2.model.Ceremonie;
import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.Souhait;

@Repository
public interface SouhaitRepository extends JpaRepository<Souhait, Long> {

    List<Souhait> findByParticipant(Participant participant);

    List<Souhait> findByCeremonie(Ceremonie ceremonie);
}
