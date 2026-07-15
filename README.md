# Like Hero To Zero

Java-EE-Webanwendung zur Visualisierung weltweiter CO2-Emissionsdaten.

Fallstudienprojekt im Kurs IPWA02-01 (Programmierung von industriellen Informationssystemen mit Java EE), IU Internationale Hochschule. Entwickelt in iterativen Arbeitsschritten nach agiler Vorgehensweise mit priorisiertem Product Backlog (MoSCoW-Methode).

## Projektübersicht

Ausgangspunkt ist folgendes Szenario: Eine PR-Agentur, die Naturverbände wie NABU und BUND unterstützt, benötigt eine Webanwendung für ein Nachhaltigkeitsprojekt namens "Like Hero To Zero". Die Anwendung soll öffentlich zugängliche Daten zu weltweiten CO2-Emissionen darstellen, ohne dass ein Login erforderlich ist. Zusätzlich soll eine Backend-Oberfläche mit Login existieren, über die registrierte Wissenschaftler:innen neue Daten hinzufügen oder bestehende Datenfehler korrigieren können.

### User Stories (Product Backlog, MoSCoW-Priorisierung)

| Priorität | Rolle | User Story | Status |
|---|---|---|---|
| MUST | Umweltpolitisch interessierte:r Bürger:in | Aktuellsten CO2-Ausstoß eines Landes einsehen können | Umgesetzt |
| MUST | Registrierte:r, Daten beitragende:r Wissenschaftler:in | Jüngste Daten aus der Klimaforschung im Datensatz hinterlegen können | Umgesetzt |
| COULD | Herausgeber:in des Datensatzes | Sicherstellen, dass Ergänzungen oder Änderungen an den Daten erst freigegeben werden müssen | Umgesetzt |

### Umsetzung der Aufgabenstellung

- Öffentliches Code-Repository auf GitHub eingerichtet
- Prototyp mit beiden MUST-Funktionen sowie der COULD-Funktion inklusive persistenter Datenhaltung entwickelt
- Technologiestack gemäß Kursvorgabe verwendet: JSF mit CDI-Beans, PrimeFaces als Komponentenbibliothek, JPA mit Hibernate als Persistenz-Provider, MySQL als relationale Datenbank

## Funktionsumfang

- Öffentliche Ansicht ohne Login: aktuellster, freigegebener CO2-Ausstoß pro Land, mit Länderdetailseite
- Login-geschützter Backend-Bereich für Wissenschaftler:innen (Passwort-Hashing mit BCrypt)
- Anlegen, Bearbeiten und Löschen von CO2-Einträgen im Backend
- Freigabe-Workflow: neu angelegte Einträge erhalten den Status "ausstehend" und sind erst nach Prüfung durch eine separate Herausgeber-Rolle öffentlich sichtbar
- Login-geschützter Freigabe-Bereich für Herausgeber:innen, mit Übersicht aller ausstehenden Einträge sowie Freigabe- oder Ablehnungsfunktion

## Technologiestack

| Bereich | Technologie |
|---|---|
| Frontend | JavaServer Faces (JSF), PrimeFaces |
| Komponentenmodell / DI | CDI (Contexts and Dependency Injection) |
| Persistenz | JPA mit Hibernate |
| Datenbank | MySQL |
| Passwort-Hashing | jBCrypt |
| Zugriffsschutz | Servlet-Filter (AuthFilter, PublisherAuthFilter) für /admin- und /publisher-Bereich |
| Applikationsserver | WildFly |
| Build-Tool | Maven |

## Lokale Entwicklungsumgebung

- MacBook Pro, Apple Silicon (M4 Pro), macOS
- IntelliJ IDEA Ultimate (JetBrains Student License), inklusive integriertem JDK und Maven
- MySQL Community Server, lokal installiert
- WildFly Application Server, lokal entpackt und konfiguriert
- Git (in macOS vorinstalliert), Repository auf GitHub

## Projektstruktur

```
like-hero-to-zero-lhtz/
├── src/main/java/de/lhtz/
│   ├── entity/       Country, Co2Entry (mit Status-Feld), Scientist, Publisher
│   ├── repository/   Datenzugriff über EntityManager (JPQL, Named Queries)
│   ├── service/       Co2Service, ScientistService, PublisherService, DataEntryService
│   ├── bean/          Co2ListBean, CountryDetailBean, AuthBean, AdminBean,
│   │                  PublisherAuthBean, ReviewBean
│   ├── converter/     CountryConverter (JSF-Converter für Country-Objekte)
│   └── filter/        AuthFilter (/admin), PublisherAuthFilter (/publisher)
├── src/main/resources/META-INF/
│   └── persistence.xml
└── src/main/webapp/
    ├── index.xhtml              öffentliche Startseite mit CO2-Übersicht
    ├── country.xhtml            Länderdetailseite
    ├── login.xhtml              Login für Wissenschaftler:innen
    ├── publisher-login.xhtml    Login für Herausgeber:innen
    ├── admin/dashboard.xhtml    geschütztes Backend für Wissenschaftler:innen
    └── publisher/review.xhtml   geschützter Freigabe-Bereich für Herausgeber:innen
```

## Architektur

Schichtenarchitektur:

```
Browser (JSF/XHTML)
      |
      v
CDI Backing Beans     Co2ListBean, CountryDetailBean, AuthBean, AdminBean,
                       PublisherAuthBean, ReviewBean
      |
      v
Services              Co2Service, ScientistService, PublisherService, DataEntryService
      |
      v
Repositories          Co2Repository, CountryRepository, ScientistRepository,
                       PublisherRepository
      |
      v
JPA / Hibernate
      |
      v
MySQL (lhtz_db)
```

## Datenmodell

Vier zentrale Entitäten:

- country: Land, Name, ISO-Code
- co2_entry: CO2-Wert (kt), Jahr, Fremdschlüssel auf country, Status (ausstehend, freigegeben, abgelehnt)
- scientist: Benutzerkonto mit BCrypt-gehashtem Passwort, für Dateneingabe
- publisher: Benutzerkonto mit BCrypt-gehashtem Passwort, für die Freigabe von Dateneinträgen

## Freigabe-Workflow

1. Wissenschaftler:in trägt neuen CO2-Eintrag ein; Eintrag erhält automatisch den Status "ausstehend"
2. Der Eintrag ist zu diesem Zeitpunkt noch nicht auf der öffentlichen Startseite sichtbar
3. Herausgeber:in meldet sich im separat geschützten Bereich an und sieht alle ausstehenden Einträge
4. Herausgeber:in gibt den Eintrag frei oder lehnt ihn ab
5. Erst nach Freigabe erscheint der Eintrag auf der öffentlichen Startseite

Bearbeitungen bestehender, bereits freigegebener Einträge behalten ihren Status und bleiben sofort sichtbar; nur neu angelegte Einträge durchlaufen den Freigabeprozess.

## Lokale Installation

Voraussetzungen: Java, Maven, MySQL, WildFly

```bash
CREATE DATABASE lhtz_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

mvn clean package

cp target/like-hero-to-zero.war $WILDFLY_HOME/standalone/deployments/
```

Die MySQL-Datasource muss in WildFly unter dem JNDI-Namen java:/lhtzDS eingerichtet sein (siehe persistence.xml).

Anwendung erreichbar unter: http://localhost:8080/like-hero-to-zero/

## Datenquelle

Die CO2-Daten stammen aus dem Datensatz "CO2 and Greenhouse Gas Emissions" (Our World in Data), bereitgestellt über die World Bank Data360 Plattform:
https://data360.worldbank.org/en/dataset/OWID_CB

Verwendeter Indikator: OWID_CB_CO2 (jährliche CO2-Emissionen pro Land). Die Werte wurden von Millionen Tonnen (Mt) in Kilotonnen (kt) umgerechnet und für den Zeitraum ab 1960 in die Datenbank importiert.

## Status

Alle MUST-Funktionen sowie die COULD-Funktion sind implementiert und getestet: öffentliche CO2-Ansicht, Login für Wissenschaftler:innen, Anlegen/Bearbeiten/Löschen von CO2-Einträgen im geschützten Backend-Bereich, sowie der vollständige Freigabe-Workflow mit separater Herausgeber-Rolle.
