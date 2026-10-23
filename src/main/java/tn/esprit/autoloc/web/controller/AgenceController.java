package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;
import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences")
public class AgenceController {
    private final IAgenceService service;
    @GetMapping @Operation(summary = "Lister les agences") @ApiResponse(responseCode = "200", description = "Liste des agences") public List<Agence> findAll() { return service.findAll(); }
    @GetMapping("/{id}") @Operation(summary = "Consulter une agence") @ApiResponse(responseCode = "404", description = "Agence introuvable") public Agence findById(@Parameter(description = "Identifiant de l’agence") @PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer une agence") @ApiResponse(responseCode = "201", description = "Agence créée") @ApiResponse(responseCode = "400", description = "Données invalides") public Agence create(@RequestBody Agence value) { return service.create(value); }
    @PutMapping("/{id}") @Operation(summary = "Modifier une agence") @ApiResponse(responseCode = "200", description = "Agence modifiée") @ApiResponse(responseCode = "404", description = "Agence introuvable") public Agence update(@Parameter(description = "Identifiant de l’agence") @PathVariable Long id, @RequestBody Agence value) { return service.update(id, value); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer une agence") @ApiResponse(responseCode = "204", description = "Agence supprimée") @ApiResponse(responseCode = "404", description = "Agence introuvable") public void delete(@Parameter(description = "Identifiant de l’agence") @PathVariable Long id) { service.deleteById(id); }
}
