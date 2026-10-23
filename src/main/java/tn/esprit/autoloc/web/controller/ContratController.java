package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;
import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrats")
public class ContratController {
    private final IContratService service;
    @GetMapping @Operation(summary = "Lister les contrats") @ApiResponse(responseCode = "200", description = "Liste des ressources") public List<Contrat> findAll() { return service.findAll(); }
    @GetMapping("/{id}") @Operation(summary = "Consulter un contrat") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Contrat findById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un contrat") @ApiResponse(responseCode = "201", description = "Ressource créée") @ApiResponse(responseCode = "400", description = "Données invalides") public Contrat create(@RequestBody Contrat value) { return service.create(value); }
    @PutMapping("/{id}") @Operation(summary = "Modifier un contrat") @ApiResponse(responseCode = "200", description = "Ressource modifiée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Contrat update(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id, @RequestBody Contrat value) { return service.update(id, value); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un contrat") @ApiResponse(responseCode = "204", description = "Ressource supprimée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public void delete(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { service.deleteById(id); }
}

