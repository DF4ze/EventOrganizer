package fr.ses10doigts.webApp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.ses10doigts.webApp2.model.Repas;

@Repository
public interface RepasRepository extends JpaRepository<Repas, Long> {

}
