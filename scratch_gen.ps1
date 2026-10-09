function Write-Service {
    param($Entity, $IdProp, $CheckLogic, $UpdateLogic)
    $lower = $Entity.ToLower()
    $var = $Entity.Substring(0, 1).ToLower() + $Entity.Substring(1)
    
    $content = @"
package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.$Entity;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.I$($Entity)Repository;

@Service
@RequiredArgsConstructor
public class $($Entity)ServiceImpl implements I$($Entity)Service {

    private final I$($Entity)Repository $($var)Repository;

    @Override
    public $Entity create($Entity $var) {
        if ($var.get$IdProp() != null) {
            throw new IllegalArgumentException("Un nouveau $lower ne doit pas avoir d'identifiant");
        }
        $CheckLogic
        return $($var)Repository.save($var);
    }

    @Override
    public $Entity findById(Long id) {
        return $($var)Repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("$Entity", id));
    }

    @Override
    public List<$Entity> findAll() {
        return $($var)Repository.findAll();
    }

    @Override
    public $Entity update(Long id, $Entity $var) {
        $Entity existant = findById(id);
        $CheckLogic
        $UpdateLogic
        return $($var)Repository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!$($var)Repository.existsById(id)) {
            throw new ResourceNotFoundException("$Entity", id);
        }
        $($var)Repository.deleteById(id);
    }
}
"@
    [System.IO.File]::WriteAllText("src/main/java/tn/esprit/autoloc/service/$($Entity)ServiceImpl.java", $content)
}

# Agence
Write-Service "Agence" "IdAgence" 'if ($var.getNom() == null || $var.getNom().trim().isEmpty()) { throw new IllegalArgumentException("Le nom de l''agence est obligatoire"); }' '
        existant.setNom($var.getNom());
        existant.setVille($var.getVille());
        existant.setAdresse($var.getAdresse());
        existant.setTelephone($var.getTelephone());
'

# Employe
Write-Service "Employe" "IdEmploye" 'if ($var.getNom() == null || $var.getNom().trim().isEmpty() || $var.getRole() == null) { throw new IllegalArgumentException("Le nom et le poste de l''employé sont obligatoires"); }' '
        existant.setNom($var.getNom());
        existant.setPrenom($var.getPrenom());
        existant.setRole($var.getRole());
        existant.setAgence($var.getAgence());
'

# Vehicule
Write-Service "Vehicule" "IdVehicule" 'if ($var.getTarifJournalier() != null && $var.getTarifJournalier().compareTo(java.math.BigDecimal.ZERO) < 0) { throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif"); }' '
        existant.setImmatriculation($var.getImmatriculation());
        existant.setMarque($var.getMarque());
        existant.setModele($var.getModele());
        existant.setCategorie($var.getCategorie());
        existant.setTarifJournalier($var.getTarifJournalier());
        existant.setStatut($var.getStatut());
        existant.setAgence($var.getAgence());
'

# Equipement
Write-Service "Equipement" "IdEquipement" 'if ($var.getLibelle() == null || $var.getLibelle().trim().isEmpty()) { throw new IllegalArgumentException("Le libellé de l''équipement est obligatoire"); }' '
        existant.setLibelle($var.getLibelle());
'

# Client
Write-Service "Client" "IdClient" 'if ($var.getNom() == null || $var.getNom().trim().isEmpty() || $var.getEmail() == null || $var.getEmail().trim().isEmpty()) { throw new IllegalArgumentException("Le nom et l''email du client sont obligatoires"); }' '
        existant.setNom($var.getNom());
        existant.setPrenom($var.getPrenom());
        existant.setEmail($var.getEmail());
        existant.setTelephone($var.getTelephone());
        existant.setNumPermis($var.getNumPermis());
        existant.setDateInscription($var.getDateInscription());
'

# Reservation
Write-Service "Reservation" "IdReservation" 'if ($var.getDateFin() != null && $var.getDateDebut() != null && $var.getDateFin().isBefore($var.getDateDebut())) { throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début"); }' '
        existant.setDateDebut($var.getDateDebut());
        existant.setDateFin($var.getDateFin());
        existant.setStatut($var.getStatut());
        existant.setClient($var.getClient());
        existant.setVehicule($var.getVehicule());
        existant.setContrat($var.getContrat());
'

# Maintenance
Write-Service "Maintenance" "IdMaintenance" 'if ($var.getDateFin() != null && $var.getDateDebut() != null && $var.getDateFin().isBefore($var.getDateDebut())) { throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début"); }' '
        existant.setDateDebut($var.getDateDebut());
        existant.setDateFin($var.getDateFin());
        existant.setDescription($var.getDescription());
        existant.setVehicule($var.getVehicule());
'

$paiementImpl = @"
package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IPaiementRepository;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    public Paiement findById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }
}
"@
[System.IO.File]::WriteAllText("src/main/java/tn/esprit/autoloc/service/PaiementServiceImpl.java", $paiementImpl)
