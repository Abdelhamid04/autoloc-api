package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEmployeRepository;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;

    @Override
    public Employe create(Employe employe) {
        if (employe.getIdEmploye() != null) {
            throw new IllegalArgumentException("Un nouveau employe ne doit pas avoir d'identifiant");
        }
        if (employe.getNom() == null || employe.getNom().trim().isEmpty() || employe.getRole() == null) { throw new IllegalArgumentException("Le nom et le poste de l'employé sont obligatoires"); }
        return employeRepository.save(employe);
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe update(Long id, Employe employe) {
        Employe existant = findById(id);
        if (employe.getNom() == null || employe.getNom().trim().isEmpty() || employe.getRole() == null) { throw new IllegalArgumentException("Le nom et le poste de l'employé sont obligatoires"); }
        
        existant.setNom(employe.getNom());
        existant.setPrenom(employe.getPrenom());
        existant.setRole(employe.getRole());

        return employeRepository.save(existant);
    }

    @Override
    public Employe desaffecterEmploye(Long idEmploye) {
        Employe employe = findById(idEmploye);
        employe.setAgence(null);
        return employeRepository.save(employe);
    }

    @Override
    public Employe affecterEmploye(Long idEmploye, Long idAgence) {
        Employe employe = findById(idEmploye);
        if (employe.getAgence() != null
                && !employe.getAgence().getIdAgence().equals(idAgence)) {
            throw new IllegalArgumentException("L'employé doit être désaffecté avant d'être affecté à une autre agence");
        }
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
        employe.setAgence(agence);
        return employeRepository.save(employe);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }
}