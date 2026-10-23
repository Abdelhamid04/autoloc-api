package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.IReservationService;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Réservations")
public class ReservationController {
    private final IReservationService service;
    @GetMapping @Operation(summary = "Lister les réservations") @ApiResponse(responseCode = "200", description = "Liste des ressources") public List<Reservation> findAll() { return service.findAll(); }
    @GetMapping("/{id}") @Operation(summary = "Consulter une réservation") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Reservation findById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Créer une réservation") @ApiResponse(responseCode = "201", description = "Ressource créée") @ApiResponse(responseCode = "400", description = "Données invalides") public Reservation create(@RequestBody Reservation value) { return service.create(value); }
    @PutMapping("/{id}") @Operation(summary = "Modifier une réservation") @ApiResponse(responseCode = "200", description = "Ressource modifiée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public Reservation update(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id, @RequestBody Reservation value) { return service.update(id, value); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Supprimer une réservation") @ApiResponse(responseCode = "204", description = "Ressource supprimée") @ApiResponse(responseCode = "404", description = "Ressource introuvable") public void delete(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) { service.deleteById(id); }
}

