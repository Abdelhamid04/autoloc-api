# Atelier 3 : Spring Data JPA

## 1. Choix des interfaces Repository

| Interface | Étend | Justification |
| :--- | :--- | :--- |
| `IContratRepository` | `JpaRepository<Contrat, Long>` | CRUD complet, `findAll` renvoie une `List`, `saveAndFlush` disponible. |
| `IAgenceRepository` | `JpaRepository<Agence, Long>` | Nécessité d'avoir un CRUD complet avec pagination/tri et manipulation facilitée via des `List`. |
| `IClientRepository` | `JpaRepository<Client, Long>` | Besoin du CRUD complet pour gérer les clients et de méthodes JPA spécifiques comme la suppression en lot. |
| `IEmployeRepository` | `JpaRepository<Employe, Long>` | Mêmes avantages : accès à `saveAll()`, `flush()` et `findAll()` retournant une liste d'employés. |
| `IEquipementRepository` | `JpaRepository<Equipement, Long>` | Interface la plus complète de Spring Data JPA, recommandée pour notre modèle complet. |
| `IMaintenanceRepository` | `JpaRepository<Maintenance, Long>` | Permet un accès complet et les méthodes de tri/pagination utiles pour l'historique des maintenances. |
| `IPaiementRepository` | `JpaRepository<Paiement, Long>` | Requis pour pouvoir lire et manipuler les paiements via des requêtes JPA, bien qu'on le modifie via le contrat parent. |
| `IReservationRepository` | `JpaRepository<Reservation, Long>` | Simplifie l'implémentation de la persistance avec des collections `List` et des opérations complètes. |
| `IVehiculeRepository` | `JpaRepository<Vehicule, Long>` | Outil standard et complet pour filtrer, paginer ou chercher des véhicules dans la base. |

## 2. Analyse de la qualité avec SonarQube for IDE

| Anomalie SonarQube for IDE | Règle / explication | Correction apportée |
| :--- | :--- | :--- |
| **Import "wildcard" utilisé** | **java:S2208** (Wildcard imports should not be used). Importer tout le package `jakarta.persistence.*` n'est pas recommandé par les outils d'analyse statique. | Remplacement par des imports spécifiques (`import jakarta.persistence.Entity;`, `import jakarta.persistence.Id;`, etc.) dans l'entité `Contrat`. |
| **Utilisation de `@Data` sur une entité** | **java:S2166** (Classes with `@Entity` should not use `@Data`). L'annotation `@Data` de Lombok génère un `hashCode()` et un `equals()` posant des problèmes avec les lazy-loading. | On évite `@Data` : on utilise `@Getter`, `@Setter`, `@NoArgsConstructor` et `@AllArgsConstructor` sur toutes les entités (comme pour `Employe`). |
| **Nom de table non conforme** | **java:S115** (Nommage). Le nom défini dans l'annotation est en majuscule au lieu d'être en minuscule comme la convention SQL le veut. | Modification de l'annotation `@Table(name = "Agence")` en `@Table(name = "agence")` dans la classe `Agence`. |

