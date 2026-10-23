package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;
import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clients")
public class ClientController {
    private final IClientService service;
    @GetMapping @Operation(summary = "Lister les clients") @ApiResponse(responseCode = "200", description = "Liste des ressources") public List<Client> findAll() { return service.findAll(); }
    @GetMapping("/{id}") @Operation(summary = "Consulter un client") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Client findById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer un client") @ApiResponse(responseCode = "201", description = "Ressource créée") @ApiResponse(responseCode = "400", description = "Données invalides") public Client create(@RequestBody Client value) { return service.create(value); }
    @PutMapping("/{id}") @Operation(summary = "Modifier un client") @ApiResponse(responseCode = "200", description = "Ressource modifiée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Client update(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id, @RequestBody Client value) { return service.update(id, value); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer un client") @ApiResponse(responseCode = "204", description = "Ressource supprimée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public void delete(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { service.deleteById(id); }
}

