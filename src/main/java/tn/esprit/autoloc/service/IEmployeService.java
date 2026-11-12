package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Employe;

public interface IEmployeService {
    Employe create(Employe entite);
    Employe findById(Long id);
    List<Employe> findAll();
    Employe update(Long id, Employe entite);
    Employe desaffecterEmploye(Long idEmploye);
    Employe affecterEmploye(Long idEmploye, Long idAgence);
    void deleteById(Long id);
}