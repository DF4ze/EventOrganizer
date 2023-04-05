package fr.ses10doigts.webApp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.ses10doigts.webApp2.model.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

}
