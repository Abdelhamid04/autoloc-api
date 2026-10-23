package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.IEquipementService;
import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(name = "Équipements")
public class EquipementController {
    private final IEquipementService service;
    @GetMapping @Operation(summary = "Lister les équipements") @ApiResponse(responseCode = "200", description = "Liste des ressources") public List<Equipement> findAll() { return service.findAll(); }
    @GetMapping("/{id}") @Operation(summary = "Consulter un équipement") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Equipement findById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un équipement") @ApiResponse(responseCode = "201", description = "Ressource créée") @ApiResponse(responseCode = "400", description = "Données invalides") public Equipement create(@RequestBody Equipement value) { return service.create(value); }
    @PutMapping("/{id}") @Operation(summary = "Modifier un équipement") @ApiResponse(responseCode = "200", description = "Ressource modifiée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Equipement update(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id, @RequestBody Equipement value) { return service.update(id, value); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un équipement") @ApiResponse(responseCode = "204", description = "Ressource supprimée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public void delete(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { service.deleteById(id); }
}

