# Like Hero To Zero

A Java EE web application for visualizing worldwide CO2 emissions data.

Case study project for the course IPWA02-01 (Programming of Industrial Information Systems with Java EE), IU International University of Applied Sciences. Built iteratively following an agile approach, with a prioritized product backlog (MoSCoW method).

## What this project does

Like Hero To Zero shows the latest available CO2 emissions data for countries worldwide, no login required. It also includes a login-protected backend where registered scientists can submit new data, and a second, separately protected area where a publisher role reviews and approves submissions before they go live.

Key features:

- Public country list with the latest CO2 emissions figure per country, plus a detail page per country
- Scientist login area to add, edit, and delete CO2 data entries
- Publisher login area to approve or reject newly submitted entries before they become public
- Passwords are hashed with BCrypt, never stored in plain text


## Why this project is useful

Environmental data is often locked behind dashboards that are hard to navigate or require an account just to look at a single number. This project keeps the public side completely open, while still giving contributors (scientists) and reviewers (publishers) a proper, auditable workflow for adding and correcting data. It also serves as a reference implementation of a classic Java EE stack: JSF, CDI, JPA/Hibernate, and MySQL, wired together end to end, including authentication, role separation, and a review/approval workflow.

## Tech stack

| Layer | Technology |
|---|---|
| Frontend | JavaServer Faces (JSF), PrimeFaces |
| Component model / DI | CDI (Contexts and Dependency Injection) |
| Persistence | JPA with Hibernate |
| Database | MySQL |
| Password hashing | jBCrypt |
| Access control | Servlet filters (AuthFilter, PublisherAuthFilter) |
| Application server | WildFly |
| Build tool | Maven |

## Tech stack
Layer               | Technology
--------------------|-------------
Frontend            | JavaServer Faces (JSF), PrimeFaces
Component model / DI| CDI (Contexts and Dependency Injection)
Persistence         | JPA with Hibernate
Database            | MySQL
Password hashing    | jBCrypt
Access control      | Servlet filters (AuthFilter, PublisherAuthFilter)
Application server  | WildFly
Build tool          | Maven

# How to get started

##Prerequisites

- Java Development Kit (JDK 11 or later)
- Maven
- MySQL (Community Server)
- WildFly Application Server

This project was developed on macOS using IntelliJ IDEA, but it does not depend on any macOS-specific tooling — any standard Java development setup on Windows, macOS, or Linux will work.

## Installation & setup guide

Follow these steps to get the application running on your own machine.

### 1. Clone the repository

```bash
git clone https://github.com/linalauda/Like-Hero-To-Zero-IPWA02-01.git
cd Like-Hero-To-Zero-IPWA02-01/like-hero-to-zero-lhtz
```

### 2. Create the database

Log into your local MySQL server and create a dedicated database and user. Replace `<your_password>` with a password of your own choice.

```sql
CREATE DATABASE lhtz_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'lhtz'@'localhost' IDENTIFIED BY '<your_password>';
GRANT ALL ON lhtz_db.* TO 'lhtz'@'localhost';
```

### 3. Configure the datasource in WildFly

Open your WildFly configuration file at `standalone/configuration/standalone.xml` and add a MySQL datasource inside the `<datasources>` section, right before the closing `<drivers>` tag:

```xml
<datasource jndi-name="java:/lhtzDS" pool-name="lhtzDS" enabled="true" use-java-context="true">
    <connection-url>jdbc:mysql://localhost:3306/lhtz_db?serverTimezone=UTC&amp;useSSL=false&amp;allowPublicKeyRetrieval=true</connection-url>
    <driver>mysql-connector-j-8.3.0.jar</driver>
    <security user-name="lhtz" password="&lt;your_password&gt;"/>
</datasource>
```

Note: your database credentials live only in this local WildFly configuration file, which sits outside the project's source folder. They are never part of the source code or the Git repository.

You also need to place the MySQL Connector/J driver jar in `standalone/deployments/`. Maven downloads it automatically as a dependency; you can copy it from your local Maven repository:

```bash
cp ~/.m2/repository/com/mysql/mysql-connector-j/8.3.0/mysql-connector-j-8.3.0.jar $WILDFLY_HOME/standalone/deployments/
```

### 4. Build the project

```bash
mvn clean package
```

### 5. Deploy

```bash
cp target/like-hero-to-zero.war $WILDFLY_HOME/standalone/deployments/
```

Start WildFly if it is not already running:

```bash
$WILDFLY_HOME/bin/standalone.sh
```

### 6. Open the application

```
http://localhost:8080/like-hero-to-zero/
```

The public country overview loads immediately. To try the scientist or publisher areas, create your own accounts directly in the database (both `scientist` and `publisher` tables store a BCrypt password hash, not a plain password), or generate a hash locally with `jshell` and jBCrypt.

## Project structure

```
like-hero-to-zero-lhtz/
├── src/main/java/de/lhtz/
│   ├── entity/       Country, Co2Entry (with status field), Scientist, Publisher
│   ├── repository/   data access via EntityManager (JPQL, named queries)
│   ├── service/       Co2Service, ScientistService, PublisherService, DataEntryService
│   ├── bean/          Co2ListBean, CountryDetailBean, AuthBean, AdminBean,
│   │                  PublisherAuthBean, ReviewBean
│   ├── converter/     CountryConverter (JSF converter for Country objects)
│   └── filter/        AuthFilter (/admin), PublisherAuthFilter (/publisher)
├── src/main/resources/META-INF/
│   └── persistence.xml
└── src/main/webapp/
    ├── index.xhtml              public landing page with the CO2 overview
    ├── country.xhtml            country detail page
    ├── login.xhtml              scientist login
    ├── publisher-login.xhtml    publisher login
    ├── admin/dashboard.xhtml    protected backend for scientists
    └── publisher/review.xhtml   protected review area for publishers
```

## Architecture

Layered architecture:

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
Repositories           Co2Repository, CountryRepository, ScientistRepository,
                       PublisherRepository
      |
      v
JPA / Hibernate
      |
      v
MySQL (lhtz_db)
```

### Data model

Four core entities:

- `country`: name, ISO code
- `co2_entry`: CO2 value (kt), year, foreign key to country, status (pending, approved, rejected)
- `scientist`: account with BCrypt-hashed password, submits data
- `publisher`: account with BCrypt-hashed password, approves or rejects submitted data

### Approval workflow

1. A scientist submits a new CO2 entry; it is stored with status `pending`
2. The entry is not yet visible on the public page
3. A publisher logs into a separate, protected area and reviews all pending entries
4. The publisher approves or rejects the entry
5. Only approved entries appear on the public page

Edits to existing, already-approved entries stay immediately visible; only newly created entries go through the approval process.

## Data source

The CO2 data comes from the "CO2 and Greenhouse Gas Emissions" dataset (Our World in Data), served through the World Bank Data360 platform:
https://data360.worldbank.org/en/dataset/OWID_CB

Indicator used: `OWID_CB_CO2` (annual CO2 emissions per country). Values were converted from million tonnes (Mt) to kilotonnes (kt) and imported for the period from 1960 onward.

## Where to get help

This is a personal, single-author academic project and is not set up for external contributions. If you have a question about the code or run into an issue while trying it out, feel free to open a GitHub Issue in this repository — I check it from time to time and I'm happy to explain any part of the implementation.

## License & contact

This project was built as a student case study and is shared publicly as a portfolio piece. Feel free to explore the code.

##Maintainer

Lina Lauda GitHub: @linalauda

