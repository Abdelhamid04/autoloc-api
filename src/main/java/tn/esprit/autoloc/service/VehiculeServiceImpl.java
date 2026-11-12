package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;

    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau vehicule ne doit pas avoir d'identifiant");
        }
        if (vehicule.getTarifJournalier() != null && vehicule.getTarifJournalier().compareTo(java.math.BigDecimal.ZERO) < 0) { throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif"); }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existant = findById(id);
        if (vehicule.getTarifJournalier() != null && vehicule.getTarifJournalier().compareTo(java.math.BigDecimal.ZERO) < 0) { throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif"); }
        
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        existant.setAgence(vehicule.getAgence());

        return vehiculeRepository.save(existant);
    }

    @Override
    public Vehicule affecterVehicule(Long idVehicule, Long idAgence) {
        Vehicule vehicule = findById(idVehicule);
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
        vehicule.setAgence(agence);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = findById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        if (vehicule.getEquipements().stream()
                .noneMatch(affecte -> affecte.getIdEquipement().equals(idEquipement))) {
            vehicule.getEquipements().add(equipement);
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = findById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        vehicule.getEquipements().removeIf(affecte ->
                affecte.getIdEquipement().equals(equipement.getIdEquipement()));
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }
}