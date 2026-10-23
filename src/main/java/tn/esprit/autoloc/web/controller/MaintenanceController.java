package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.IMaintenanceService;
import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenances")
public class MaintenanceController {
    private final IMaintenanceService service;
    @GetMapping @Operation(summary = "Lister les maintenances") @ApiResponse(responseCode = "200", description = "Liste des ressources") public List<Maintenance> findAll() { return service.findAll(); }
    @GetMapping("/{id}") @Operation(summary = "Consulter une maintenance") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Maintenance findById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer une maintenance") @ApiResponse(responseCode = "201", description = "Ressource créée") @ApiResponse(responseCode = "400", description = "Données invalides") public Maintenance create(@RequestBody Maintenance value) { return service.create(value); }
    @PutMapping("/{id}") @Operation(summary = "Modifier une maintenance") @ApiResponse(responseCode = "200", description = "Ressource modifiée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Maintenance update(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id, @RequestBody Maintenance value) { return service.update(id, value); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer une maintenance") @ApiResponse(responseCode = "204", description = "Ressource supprimée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public void delete(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { service.deleteById(id); }
}

