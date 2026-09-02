[![Dessine-Moi un Alpaga](logo.png)](https://dessinemoiunalpaga.com)

![GitHub workflow](https://github.com/Dessine-Moi-un-Alpaga/website/actions/workflows/release.yaml/badge.svg)

[![Google Cloud Run](https://img.shields.io/badge/Hosted%20on-Google%20Cloud%20Run-blue?logo=google)](https://console.cloud.google.com/run)
![GitHub development deployment](https://img.shields.io/github/deployments/Dessine-Moi-un-Alpaga/website/development?label=development)
![GitHub production deployment](https://img.shields.io/github/deployments/Dessine-Moi-un-Alpaga/website/production?label=production)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Dessine-Moi-un-Alpaga_website&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Dessine-Moi-un-Alpaga_website)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Dessine-Moi-un-Alpaga_website&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Dessine-Moi-un-Alpaga_website)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Dessine-Moi-un-Alpaga_website&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=Dessine-Moi-un-Alpaga_website)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Dessine-Moi-un-Alpaga_website&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=Dessine-Moi-un-Alpaga_website)

[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Dessine-Moi-un-Alpaga_website&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=Dessine-Moi-un-Alpaga_website)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Dessine-Moi-un-Alpaga_website&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Dessine-Moi-un-Alpaga_website)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Dessine-Moi-un-Alpaga_website&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=Dessine-Moi-un-Alpaga_website)

[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Dessine-Moi-un-Alpaga_website&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=Dessine-Moi-un-Alpaga_website)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Dessine-Moi-un-Alpaga_website&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Dessine-Moi-un-Alpaga_website)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Dessine-Moi-un-Alpaga_website&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Dessine-Moi-un-Alpaga_website)

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?logo=gnu)](https://www.gnu.org/licenses/gpl-3.0)
[![Terraform](https://img.shields.io/badge/terraform-1.16.1-darkred.svg?logo=terraform)](http://terraform.io)
[![Gradle](https://img.shields.io/badge/gradle-9.7.1-darkgreen.svg?logo=gradle)](http://gradle.org)
[![GrralVM](https://img.shields.io/badge/graalvm-25.3.4-blue.svg?logo=openjdk)](http://graalvm.org)
[![Kotlin](https://img.shields.io/badge/kotlin-2.4.10-darkblue.svg?logo=kotlin)](http://kotlinlang.org)
[![Ktor](https://img.shields.io/badge/ktor-3.5.2-red.svg)](http://ktor.io)

# What is this?

An application for serving and managing [the website of our alpaca breeding farm](https://dessinemoiunalpaga.com) 🦙

# Isn't this overkill? Are you crazy?

No. My mother had me tested.

## Functional Requirements

The main functional requirement behind its design is that it must include an API to CRUD most of the contents of the
website at runtime:
* animals
* news articles
* photos
* factsheets
* etc.

## Non-Functional Requirements

The first non-functional driver behind its design is that it should minimize application startup time and resource usage,
so that it can shine when deployed on [Google Cloud Run](https://cloud.google.com/run), thus reducing its carbon footprint (and, as a side
benefit, its price) as much as possible, [in a measurable way](https://console.cloud.google.com/carbon).

The second non-functional driver is that I should be having fun working on it 😊

Coming from a JVM background, I opted for [Kotlin](https://kotlinlang.org), compiled into a native binary by
[GraalVM](https://graalvm.org).

[Ktor](https://ktor.io) was the next logical choice, as most of its features can be compiled into native binaries quite easily.

## Design Considerations

### [Hexagonal Architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))

The goal is to decouple the domain and application use cases from the underlying technologies, which end up being
isolated under the `adapters` and `interfaces` root packages.

Pretending to be [domain-driven](https://www.domainlanguage.com/ddd/blue-book/) would be abusive, as there is no real
business logic. We recognize that the domain layer is anemic and live happily with it.

### Avoid obese and invasive frameworks and libraries

This project initially used the standard libraries made for interacting with the external services on
which it depends ([Google Cloud Firestore](https://cloud.google.com/products/firestore)) or used to depend (SendGrid),
as well as the Koin Dependency Injection library.

Replacing those with a few lines of custom code cut the resulting binary's size in half. After
[Ktor's native DI](https://ktor.io/docs/server-dependency-injection.html) implementation got introduced, settling for that
instead of rolling our own proved to be a cost-effective compromise.

The project also initially made use of the full `logback-classic` Slf4j implementation, which is definitely overkill for
a [twelve-factor app](https://12factor.net) that simply needs to output messages to the console. Swapping it out in
favor of `slf4j-simple` allowed me to gain a few extra megabytes. The added "cost" of using
[kotlin-logging](https://github.com/oshai/kotlin-logging)
in addition to that is arguably outweighed by the expressiveness of the resulting logging code.

# Bootstrapping the Project

The bootstrap Terraform configuration will prompt you for several configuration items and make sure everything is set up
for deploying the application to Google Cloud Run from your local box or from GitHub.

> [!IMPORTANT]
> The following software is required for bootstrapping the project:
> * the [Google Cloud CLI](https://cloud.google.com/sdk/docs/install)
> * the [Github CLI](https://cli.github.com)
> * a Google Cloud organization
> * a Google user account that is `Organization Administrator`
> * a recent [Task](https://taskfile.dev/docs/installation) installation
> * a recent [Terraform installation](https://developer.hashicorp.com/terraform/install?product_intent=terraform)

Start by authenticating with Google Cloud (using the aforementioned Organization Administrator account) and GitHub:

```shell
$ gcloud auth login
$ gh auth login
```

You can now plan the configuration:

```shell
$ task infrastructure:bootstrap:plan
```

Once the plan looks right, apply the changes to bootstrap the infrastructure:

```shell
$ task infrastructure:bootstrap:apply
```

Configuration files will be saved in the `~/.dmua` directory.

You are now all set to build using GitHub Actions, or locally.

# Local Development

> [!IMPORTANT]
> In addition to some of the [software listed above](#bootstrapping-the-project), the following is required to develop
the project:

* the [GraalVM 25 SDK](https://www.graalvm.org/downloads/)
* [Node.js](https://nodejs.org/en/download)

Running tests:

```shell
$ task app:test
```

Starting the server:

```shell
$ task app:run
```

# Preparing the Native Build

Compiling a native executable with GraalVM requires some configuration, which can be automatically generated:

```shell
$ task app:run -- -Pagent
```

You must then make sure that all relevant code paths are covered by your interactions with the app and all the
configuration files in `app/src/main/resouces/META-INF/native-image/com.dessinemoiunalpaga/website` will be updated
accordingly.

# Contributing

All changes must take place on the `beta` branch. Every commit will trigger a semantic pre-release, tag the commit
accordingly, publish a GitHub pre-release and deploy it to the development environment.

Once the development environment is in a satisfactory state, create a pull request to the main branch and merge it. This
will trigger a semantic release, tag the commit accordingly, publish a GitHub release and deploy it to the production
environment.

Every build deploys [the project's API documentation](https://dessine-moi-un-alpaga.github.io/website) to GitHub Pages.

# Modifying the Common Infrastructure from outside the CI Environment

The `infrastructure/infra` Terraform configuration covers the infrastructure that is common to both the development and
production environments.

Rather than implementing a plan-then-apply Terraform CI workflow that would prompt the developer for their approval of
the changes to the infrastructure, it can feel more practical, given the limited context of this project, to (carefully)
plan infrastructure changes from a local developer machine.

> [!WARNING]
> Such changes should be introduced in a backward-compatible fashion, so that changes intended to be tested on the
> development environment first do not break the production environment.

After making sure you are logged in as Organization Administrator of your Google Cloud Organization, run:

```shell
$ task infrastructure:infra:plan
```

Once the plan looks right, committing the changes will let the CI apply them, but applying them from a local developer
machine is definitely an option as well:

```shell
$ task infrastructure:infra:apply
```

# Managing the Dynamic Assets

Dynamic assets, that is, assets which are not referenced in the code directly, but rather by data that is managed at
runtime, are stored in Google Cloud Storage.

The following tasks assume that local assets are located under the `assets/` folder in the project directory.

```shell
$ task download-assets
$ task upload-assets
```

# Version Update Chores

Most version updates are managed by Dependabot through GitHub Actions.

The versions of the tools that are are used by the CI build get configured in the GitHub Actions workflow
(`.github/workflows/release.yaml`), whenever possible, namely:

* Node.js
* [semantic-release](https://github.com/semantic-release/semantic-release)

Version updates that need to be applied to several files at once are listed below.

## Bumping the Gradle Version

Run the following command from the `app/` subdirectory and update the `README.md` accordingly:

```shell
./gradlew :wrapper --gradle-version=<GRADLE_VERSION> && ./gradlew :wrapper
```

## Bumping the Terraform Version

Update the following files:

* `.github/workflows/release.yaml`
* `README.md`

## Bumping the Kolin or Ktor Versions

These updates will be carried out by Dependabot but you should also make sure to update the corresponding version badges
in the `README.md` file.

## Bumping the GraalVM version

Update the following files:

* `app/Dockerfile`
* `README.md`

Major version bumps require aligning the JDK version in the GitHub Actions workflow as well:

* `.github/workflows/release.yaml`

## About Dependabot Security Updates

Unfortunately, Dependabot on GitHub can only create pull requests for dependency security updates against the default
branch. This makes the automatic security updates feature very impractical, given
[the development process described above](#contributing).

The recommended alternative is to run the following command locally from the `firebase-emulator` directory, as often as
it is deemed appropriate:

```shell
$ npm audit fix
```

# Known Issues

When working on the `beta` branch, the Github Actions workflow skips the (lengthy) Sonar analysis step when no source
file changed. It does this by comparing the commit that triggered the build with the previous commit on that branch. In
order to avoid cloning the whole branch history, only the last two commits are fetched. This in turn means that pushing
more than one commit at a time might will result in the analysis **not** being run, even though it should have, if one
of those commits included source changes. This is arguably an acceptable trade-off between workflow speed and analysis
exhaustiveness.
