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
[![Terraform](https://img.shields.io/badge/terraform-1.7.1-darkred.svg?logo=terraform)](http://terraform.io)
[![Gradle](https://img.shields.io/badge/gradle-8.6-darkgreen.svg?logo=gradle)](http://gradle.org)
[![GrralVM](https://img.shields.io/badge/graalvm-21.0.2-blue.svg?logo=openjdk)](http://graalvm.org)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.22-darkblue.svg?logo=kotlin)](http://kotlinlang.org)
[![Ktor](https://img.shields.io/badge/ktor-2.3.7-red.svg)](http://ktor.io)

# What is this?

An application for serving and managing [the website of our alpaca breeding farm](https://dessinemoiunalpaga.com) 🦙

# Prerequisites

## Bootstrap

The following is required to bootstrap the project:
* the [Google Cloud CLI](https://cloud.google.com/sdk/docs/install)
* a Google Cloud organization
* a Google user account that is `Organization Administrator`

## Gradle

Running the Gradle build requires:
* an installation of the [GraalVM 21 SDK](https://www.graalvm.org/downloads/)
* a [Google Firestore Emulator installation](https://firebase.google.com/docs/emulator-suite/install_and_configure)
    properly initialized at `~/.firestore` and configured to run on port `8181`

By default, the native compilation task is configured for Apple Silicon (`-march=armv8-a`). You can override the build arguments using the
`nativeCompileExtraBuildArgs` project variable:
```shell
$ ./gradlew nativeCompile -PnativeCompileExtraBuildArgs=-0b,-march=x86-64-v3
```

It is probably simpler to include your defaults in your `~/.gradle/gradle.properties` file:
```properties
nativeCompileExtraBuildArgs=-0b,-march=x86-64-v3
```

## Docker

Building and pushing the docker image requires a recent [Docker installation](https://www.docker.com/products/docker-desktop/)
that includes `buildx`.

The Docker build works exclusively on Linux/x86-64.

## Terraform

Provisioning the infrastructure requires a recent [Terraform installation](https://developer.hashicorp.com/terraform/install?product_intent=terraform).

# Bootstrapping the Project

This project includes an interactive script that will prompt you for several configuration items and make sure
everything is set up for deploying the application to Google Cloud Run from your local box and from GitHub.

```shell
$ make bootstrap
```

Configuration will be saved in the `~/.dmua` directory.

You are now all set to run the project locally:

```shell
$ make run
```

# Configuring GitHub Actions

Running the GitHub Actions workflows requires you to copy some secrets and variables to GitHub Actions.

Note that not all secrets and variables are required for successfully running GitHub Workflows; the complete list is
provided below.

## GitHub Actions Secrets

[Generate a Sonarcloud token](https://sonarcloud.io/account/security) and save it as the `SONARCLOUD_TOKEN` GitHub
Actions secret.

The other secret values can be found in the corresponding files located in `~/.dmua/secrets`:

* `CREDENTIALS`
* `SEND_GRID_API_KEY`

## GitHub Actions Variables

Variable values can be found in the corresponding files located in `~/.dmua/variables`:

* `ARTIFACT_REGISTRY_LOCATION`
* `DEV_BUCKET`
* `DOMAIN_NAME`
* `FIRESTORE_LOCATION`
* `GOOGLE_PROJECT`
* `GOOGLE_PROJECT_NAME`
* `GOOGLE_PROJECT_NUMBER`
* `GOOGLE_REGION`
* `GOOGLE_ZONE`
* `PROD_BUCKET`

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
