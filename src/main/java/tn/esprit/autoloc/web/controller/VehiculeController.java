package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;
import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules")
public class VehiculeController {
    private final IVehiculeService service;
    @GetMapping @Operation(summary = "Lister les véhicules") @ApiResponse(responseCode = "200", description = "Liste des ressources") public List<Vehicule> findAll() { return service.findAll(); }
    @GetMapping("/{id}") @Operation(summary = "Consulter un véhicule") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Vehicule findById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un véhicule") @ApiResponse(responseCode = "201", description = "Ressource créée") @ApiResponse(responseCode = "400", description = "Données invalides") public Vehicule create(@RequestBody Vehicule value) { return service.create(value); }
    @PutMapping("/{id}") @Operation(summary = "Modifier un véhicule") @ApiResponse(responseCode = "200", description = "Ressource modifiée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Vehicule update(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id, @RequestBody Vehicule value) { return service.update(id, value); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un véhicule") @ApiResponse(responseCode = "204", description = "Ressource supprimée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public void delete(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { service.deleteById(id); }
}

