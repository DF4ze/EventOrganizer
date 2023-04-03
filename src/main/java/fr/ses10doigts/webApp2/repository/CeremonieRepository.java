package fr.ses10doigts.webApp2.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.ses10doigts.webApp2.model.Ceremonie;
import fr.ses10doigts.webApp2.model.Display;

@Repository
public interface CeremonieRepository extends JpaRepository<Ceremonie, Long> {

    List<Ceremonie> findByOrderByIdAsc();

    @Query(value = "SELECT c FROM Ceremonie c WHERE c.display IN :displays ORDER BY c.id ASC")
    List<Ceremonie> findByDisplay(@Param("displays") Set<Display> set);

    @Query(value = "SELECT c FROM Ceremonie c WHERE c.display IN :displays AND c.actif = true ORDER BY c.id ASC")
    List<Ceremonie> findByDisplayAndActif(@Param("displays") Set<Display> set);

    Ceremonie findByNom(String nom);

}
