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
[![Terraform](https://img.shields.io/badge/terraform-1.14.4-darkred.svg?logo=terraform)](http://terraform.io)
[![Gradle](https://img.shields.io/badge/gradle-9.3.0-darkgreen.svg?logo=gradle)](http://gradle.org)
[![GrralVM](https://img.shields.io/badge/graalvm-25.0.2-blue.svg?logo=openjdk)](http://graalvm.org)
[![Kotlin](https://img.shields.io/badge/kotlin-2.3.0-darkblue.svg?logo=kotlin)](http://kotlinlang.org)
[![Ktor](https://img.shields.io/badge/ktor-3.4.0-red.svg)](http://ktor.io)

# What is this?

An application for serving and managing [the website of our alpaca breeding farm](https://dessinemoiunalpaga.com) 🦙

# Prerequisites

The following is required:
* the [Google Cloud CLI](https://cloud.google.com/sdk/docs/install)
* the [Github CLI](https://cli.github.com)
* a Google Cloud organization
* a Google user account that is `Organization Administrator`
* the [GraalVM 25 SDK](https://www.graalvm.org/downloads/)
* a recent [Docker installation](https://www.docker.com/products/docker-desktop/) that includes `buildx`
* a recent [Terraform installation](https://developer.hashicorp.com/terraform/install?product_intent=terraform)
* a recent [Task](https://taskfile.dev/docs/installation) installation
* a recent [npm](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm) installation

# Logging in

```shell
$ gcloud auth login
$ gh auth login
```

# Bootstrapping the Project

This project includes a Terraform configuration that will prompt you for several configuration items and make sure
everything is set up for deploying the application to Google Cloud Run from your local box and from GitHub.

```shell
$ task infrastructure:bootstrap:plan
```

Once the plan looks right, apply the changes to bootstrap the infrastructure:

```shell
$ task infrastructure:bootstrap:apply
```

Configuration files will be saved in the `~/.dmua` directory.

You are now all set to build using GitHub Actions, or locally:

```shell
$ task app:run
```

Running tests:

```shell
$ task app:test
```

# Preparing the Native Build

Compiling a native executable with GraalVM requires some configuration, which can be automatically generated:

```shell
$ task app:run -- -Pagent
```

You must then make sure that all relevant code paths are covered by your interactions with the app and all the
configuration files in `app/src/main/resouces/META-INF/native-image/be.alpago/website` will be updated accordingly.

# Contributing

All changes must take place on the `beta` branch. Every commit will trigger a semantic pre-release, tag the commit
accordingly, publish a GitHub pre-release and deploy it to the development environment.

Once the development environment is in a satisfactory state, create a pull request to the main branch and merge it. This
will trigger a semantic release, tag the commit accordingly, publish a GitHub release and deploy it to the production
environment.

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

### Hexagonal architecture

The goal is to decouple the domain and application use cases from the underlying technologies, which end up being
isolated under the adapters and interfaces packages.

Pretending to be domain-driven would be abusive, as there is no real business logic. We recognize that the
domain layer is anemic and live happily with it.

### Avoid obese and invasive frameworks and libraries

This project initially used the standard libraries made for interacting with the external services on
which it depends (Firestore and SendGrid) and the Koin Dependency Injection library.

Replacing those with a few lines of custom code cut the resulting binary's size in half (from ~120MB to ~60MB).

It also initially used the full `logback-classic` Slf4j implementation, which is definitely overkill for a twelve-factor
app that simply needs to output messages to the console. Swapping it out in favor of `slf4j-simple` allowed me to gain
a few extra megabytes.
