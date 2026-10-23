package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;
import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(name = "Employés")
public class EmployeController {
    private final IEmployeService service;
    @GetMapping @Operation(summary = "Lister les employés") @ApiResponse(responseCode = "200", description = "Liste des ressources") public List<Employe> findAll() { return service.findAll(); }
    @GetMapping("/{id}") @Operation(summary = "Consulter un employé") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Employe findById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un employé") @ApiResponse(responseCode = "201", description = "Ressource créée") @ApiResponse(responseCode = "400", description = "Données invalides") public Employe create(@RequestBody Employe value) { return service.create(value); }
    @PutMapping("/{id}") @Operation(summary = "Modifier un employé") @ApiResponse(responseCode = "200", description = "Ressource modifiée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Employe update(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id, @RequestBody Employe value) { return service.update(id, value); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un employé") @ApiResponse(responseCode = "204", description = "Ressource supprimée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public void delete(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { service.deleteById(id); }
}

