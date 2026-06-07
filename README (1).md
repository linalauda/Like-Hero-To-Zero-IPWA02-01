# 🌍 Like Hero To Zero

> Eine Java-EE-Webanwendung zur Visualisierung weltweiter CO₂-Emissionsdaten — entwickelt im Rahmen der Fallstudie für den Kurs **IPWA02-01** an der IU Internationale Hochschule.

---

## 📌 Projektübersicht

**Like Hero To Zero** ist eine webbasierte Anwendung, die öffentlich zugängliche CO₂-Emissionsdaten weltweit darstellt. Ziel des Projekts ist es, umweltpolitisch interessierten Bürger:innen einen einfachen Zugang zu aktuellen Klimadaten zu ermöglichen — und gleichzeitig registrierten Wissenschaftler:innen die Möglichkeit zu geben, neue Daten beizutragen.

Das Projekt entstand im Kontext einer fiktiven PR-Agentur, die Naturverbände (NABU, BUND u. a.) unterstützt, und wurde nach der **MoSCoW-Methode** priorisiert.

---

## ✅ Umgesetzte User Stories (MUST)

| # | Rolle | Funktion |
|---|-------|----------|
| 1 | Bürger:in (öffentlich) | Aktuellsten CO₂-Ausstoß eines Landes ohne Login einsehen |
| 2 | Wissenschaftler:in (eingeloggt) | Neue CO₂-Daten über ein Backend-Formular eintragen |

---

## 🛠️ Technologiestack

| Schicht | Technologie |
|---------|-------------|
| Frontend | JavaServer Faces (JSF) + PrimeFaces |
| Backend / DI | CDI (Contexts and Dependency Injection) |
| Persistenz | JPA (Java Persistence API) + Hibernate |
| Datenbank | MySQL |
| Sicherheit | Servlet-Filter (`AuthFilter`) für `/admin`-Bereich |
| Applikationsserver | WildFly / Payara |
| Build | Maven (WAR-Packaging) |

---

## 📁 Projektstruktur

```
like-hero-to-zero/
├── src/
│   └── main/
│       ├── java/de/lhtz/
│       │   ├── entity/          # JPA-Entities: Country, Co2Entry, Scientist
│       │   ├── repository/      # Datenbankzugriff via EntityManager (JPQL)
│       │   ├── service/         # Co2Service, ScientistService, DataEntryService
│       │   ├── bean/            # CDI Backing Beans: Co2ListBean, CountryDetailBean,
│       │   │                    #                    AuthBean, AdminBean
│       │   └── filter/          # AuthFilter (Servlet-Security)
│       ├── resources/
│       │   └── META-INF/
│       │       └── persistence.xml   # JPA-Konfiguration (Hibernate + MySQL)
│       └── webapp/
│           ├── WEB-INF/
│           │   └── templates/        # Gemeinsames PrimeFaces-Layout-Template
│           ├── index.xhtml           # Öffentliche Startseite (CO₂-Liste)
│           ├── country.xhtml         # Länderdetailseite
│           ├── login.xhtml           # Login für Wissenschaftler:innen
│           └── admin/
│               └── dashboard.xhtml   # Backend-Eingabemaske (geschützt)
├── sql/
│   └── init.sql                      # Datenbankschema + Testdaten
└── pom.xml
```

---

## 🗄️ Datenbankstruktur

Die Anwendung verwendet drei Hauptentitäten:

- **`country`** — Länderdaten (Name, ISO-Code)
- **`co2_entry`** — CO₂-Einträge mit Jahr, Wert (kt) und Referenz auf ein Land
- **`scientist`** — Wissenschaftler:innen-Accounts mit BCrypt-verschlüsseltem Passwort

Datenbankname: `lhtz_db`

---

## 🚀 Lokale Installation & Setup

### Voraussetzungen

- Java 11+
- Maven 3.x
- MySQL 8.x
- WildFly 26+ oder Payara 6+

### 1. Datenbank einrichten

```sql
CREATE DATABASE lhtz_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Dann das Init-Skript ausführen:

```bash
mysql -u root -p lhtz_db < sql/init.sql
```

### 2. Datasource konfigurieren

In WildFly/Payara eine MySQL-Datasource mit dem JNDI-Namen `java:/lhtzDS` einrichten (passend zur `persistence.xml`).

### 3. Projekt bauen

```bash
mvn clean package
```

### 4. Deployen

Die erzeugte `.war`-Datei aus `target/` in das Deploymentverzeichnis des Applikationsservers kopieren:

```bash
cp target/like-hero-to-zero.war $WILDFLY_HOME/standalone/deployments/
```

### 5. Anwendung aufrufen

```
http://localhost:8080/like-hero-to-zero/
```

---

## 🔐 Zugangsdaten (Testumgebung)

| Rolle | E-Mail | Passwort |
|-------|--------|----------|
| Wissenschaftler:in | `scientist@lhtz.de` | `science123` |

> ⚠️ Diese Zugangsdaten sind ausschließlich für die lokale Testumgebung vorgesehen.

---

## 📊 Datenquelle

Die initialen CO₂-Daten basieren auf dem öffentlich verfügbaren Datensatz:

> Rearc (2022). *CO2 Emissions (kt) | World Bank Open Data.*  
> https://aws.amazon.com/marketplace/pp/prodview-qf3r4b6jpivte#usage

---

## 🏗️ Architekturüberblick

Die Anwendung folgt einer klassischen **MVC-Schichtarchitektur**:

```
Browser (JSF/XHTML)
      │
      ▼
Backing Beans (CDI)         ← Co2ListBean, CountryDetailBean, AuthBean, AdminBean
      │
      ▼
Services (CDI)              ← Co2Service, ScientistService, DataEntryService
      │
      ▼
Repositories                ← Co2Repository, CountryRepository, ScientistRepository
      │
      ▼
JPA / Hibernate
      │
      ▼
MySQL (lhtz_db)
```

---

## 📚 Kurskontext

| Feld | Wert |
|------|------|
| Kurs | IPWA02-01 – Programmierung von industriellen Informationssystemen mit Java EE |
| Hochschule | IU Internationale Hochschule |
| Prüfungsform | Fallstudie |
| Gewählte Aufgabe | Aufgabenstellung 1: Like Hero To Zero |

---

## 📄 Lizenz

Dieses Projekt wurde im Rahmen einer Studienleistung erstellt. Eine Weitergabe oder Veröffentlichung der Lösung auf Drittplattformen ist gemäß den Prüfungsrichtlinien der IU Internationalen Hochschule nicht gestattet.
