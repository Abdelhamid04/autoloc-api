# Atelier 4 : Couche Service, IoC et Injection de dépendances

## A. Modes d'injection comparés

| Critère | Injection par constructeur (recommandée) | Injection par attribut (`@Autowired` sur champ) |
| :--- | :--- | :--- |
| **Champ `final` possible ?** | Oui | Non |
| **Dépendance visible (signature) ?**| Oui (dans le constructeur) | Non (cachée à l'intérieur de la classe) |
| **Utilisable avec `new` hors conteneur ?**| Oui (on doit passer les dépendances au constructeur, évitant les `NullPointerException`) | Non (les dépendances seraient `null` si non injectées par Spring) |
| **Signalé par SonarQube ?** | Non, c'est la bonne pratique | Oui (Règle S6813 : *Field injection is not recommended*) |

## B. Services créés

| Service | Dépendances injectées | Mode | Justification |
| :--- | :--- | :--- | :--- |
| `ContratServiceImpl` | `IContratRepository` | Constructeur | Garantit que le repository est fourni à la création et reste constant (`final`). |
| `AgenceServiceImpl` | `IAgenceRepository` | Constructeur | Respect de la convention du projet et garantie d'intégrité de l'état. |
| `ClientServiceImpl` | `IClientRepository` | Constructeur | Même justification, permet l'utilisation du mot-clé `final`. |
| `EmployeServiceImpl` | `IEmployeRepository` | Constructeur | Standard Spring moderne via `@RequiredArgsConstructor` de Lombok. |
| `EquipementServiceImpl` | `IEquipementRepository` | Constructeur | Idéal pour le couplage faible et l'immutabilité du composant. |
| `MaintenanceServiceImpl`| `IMaintenanceRepository`| Constructeur | La dépendance est explicite et nécessaire au bon fonctionnement. |
| `PaiementServiceImpl` | `IPaiementRepository` | Constructeur | Besoin limité à la lecture, injection sécurisée via constructeur. |
| `ReservationServiceImpl`| `IReservationRepository`| Constructeur | Câblage fiable et testable facilement (ex: avec des Mocks). |
| `VehiculeServiceImpl` | `IVehiculeRepository` | Constructeur | Sécurise l'objet : impossible d'instancier le service sans son repository. |

## C. Messages d'erreur du conteneur

**Message 1 : Bean introuvable**
- **Cause** : Le bean `IContratService` est introuvable pour être injecté dans `ContratController`. Cela arrive si la classe `ContratServiceImpl` n'a pas l'annotation `@Service` ou si elle se trouve en dehors du package scanné par `@SpringBootApplication`.
- **Correction** : Ajouter l'annotation `@Service` sur la classe `ContratServiceImpl` (et vérifier qu'elle est bien dans un sous-package racine).

**Message 2 : Plusieurs beans pour une même dépendance**
- **Cause** : Le contrôleur demande une interface pour laquelle le conteneur a trouvé deux implémentations différentes (`emailNotificateur` et `smsNotificateur`). Spring ne sait pas laquelle choisir.
- **Correction** : Ajouter `@Primary` sur l'une des implémentations pour en faire le choix par défaut, ou préciser `@Qualifier("nomDuBean")` au niveau du constructeur de `NotificationController` pour lever l'ambiguïté.

**Message 3 : Dépendance circulaire**
- **Cause** : `ClientServiceImpl` et `ReservationServiceImpl` dépendent mutuellement l'un de l'autre par injection de constructeur. Le conteneur ne peut instancier aucun des deux car l'un attend l'autre.
- **Correction** : L'injection par constructeur bloque ce cycle car elle demande l'objet fini avant instanciation. Pour corriger, il faut revoir la conception métier (ex: extraire la logique commune dans un troisième service ou injecter le Repository direct).

## D. Anomalies SonarQube for IDE

| Anomalie SonarQube for IDE | Règle / explication | Correction apportée |
| :--- | :--- | :--- |
| **Injection par attribut (Field Injection)** | **java:S6813** : *Field injection is not recommended*. Rend la classe difficile à tester et l'objet peut être instancié dans un état incomplet (dépendance `null`). | Remplacement par l'injection par constructeur (utilisation de `@RequiredArgsConstructor` de Lombok avec attribut `final`). |
| **Exception générique levée** | **java:S112** : *Generic exceptions should never be thrown*. Lancer `RuntimeException` ou `Exception` masque la cause précise et complique la gestion des erreurs. | Création et utilisation d'une exception spécifique métier `ResourceNotFoundException`. |
| **Utilisation de @Data sur une entité** (rappel) | **java:S2166** : L'annotation génère `equals/hashCode` non performants pour JPA. | Remplacement par `@Getter`, `@Setter` et annotations de constructeurs explicites. |

## E. Questions de compréhension

1. **Où est le `new` de ContratServiceImpl et qui l'exécute ?**
   Il n'y a pas de mot-clé `new` écrit dans notre code pour ce composant. C'est le conteneur Spring (IoC) qui l'exécute dynamiquement au démarrage de l'application grâce au scan (via l'annotation `@Service`).

2. **Pourquoi un contrôleur dépendra-t-il de IContratService et non de ContratServiceImpl ?**
   Pour garantir le couplage faible. Ainsi, il est possible de changer l'implémentation sous-jacente ou de simuler le service (mock) pour les tests sans avoir à modifier le code du contrôleur.

3. **Pourquoi un service singleton doit-il rester sans état ? Donner un exemple de bug.**
   Étant partagé par tous les processus de l'application, un état modifiable créerait des "race conditions" (conflits d'accès concurrents). Par exemple : si un attribut `Contrat contratCourant` existait sur la classe, deux utilisateurs exécutant une action au même moment pourraient s'écraser mutuellement l'attribut, attribuant la mauvaise action au mauvais contrat.

4. **Pourquoi `update` charge-t-il l'entité existante avant d'appeler `save` au lieu de lui passer directement l'objet reçu ?**
   Passer un objet modifié reçu directement effacerait toutes les autres données absentes (champs mis à `null` par défaut). De plus, avec l'option `orphanRemoval`, écraser les collections effacerait involontairement toutes les entités enfants (comme les paiements d'un contrat).

5. **Quelle différence entre `@Component` et `@Bean` ? Entre `@Primary` et `@Qualifier` ? Pourquoi `IPaiementService` n'expose-t-il ni création, ni modification, ni suppression ?**
   - `@Component` annote une classe pour qu'elle soit détectée automatiquement au scan, tandis que `@Bean` s'applique sur une méthode d'une classe `@Configuration` pour déclarer explicitement un objet renvoyé au conteneur.
   - `@Primary` définit un composant prioritaire par défaut, tandis que `@Qualifier` est ciblé lors de l'injection pour forcer le choix d'un nom précis.
   - `IPaiementService` est limité car le Paiement dépend existentiellement du Contrat. C'est la création et la modification d'un Contrat qui se chargent de persister ou retirer ses paiements associés en cascade. Les paiements ne s'altèrent pas directement pour des raisons de logique métier et cohérence.
