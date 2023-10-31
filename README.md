![Dessine-Moi un Alpaga](logo.png)

![](https://github.com/gilles-gosuin/dessine-moi-un-alpaga/actions/workflows/release.yaml/badge.svg)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?logo=gnu)](https://www.gnu.org/licenses/gpl-3.0)
[![Terraform](https://img.shields.io/badge/terraform-1.6.2-darkred.svg?logo=terraform)](http://gradle.org)
[![Gradle](https://img.shields.io/badge/gradle-8.4-darkgreen.svg?logo=gradle)](http://gradle.org)
[![GrralVM](https://img.shields.io/badge/graalvm-17.0.9-blue.svg?logo=openjdk)](http://graalvm.org)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.10-darkblue.svg?logo=kotlin)](http://kotlinlang.org)
[![Ktor](https://img.shields.io/badge/ktor-2.3.5-red.svg)](http://ktor.io)

# What is this?

An application for serving and managing [the website of our alpaca breeding farm 🦙](https://dessinemoiunalpaga.com).

# Prerequisites

The following is required to bootstrap the project:
* a Google Cloud organization
* a Google user account tht is `Organization Administrator` and has the `Owner` role on the project.

# Bootstrapping the Project

This project includes an interactive script that will prompt you for several configuration items and make sure
everything is set up for deploying the application to Google Cloud Run from your local box and from GitHub.

```shell
$ make bootstrap
```

Configuration will be saved in the `~/.dmua` directory.

The application uses Google
[Application Default Credentials](https://cloud.google.com/docs/authentication/application-default-credentials) and will
automatically detect the credentials for the user you are logged in with:

```shell
$ gcloud auth application-default login
```

Your are now all set to run the project locally:

```shell
$ make run
```

# Configuring GitHub Actions

Running the GitHub Actions workflows requires you to copy some secrets and variables to GitHub Actions.

Note that not all secrets and variables are required for successfully running GitHub Workflows; the complete list is
provided below.

## GitHub Actions Secrets

Values can be found in the corresponding files located in `~/.dmua/secrets`:

* `CREDENTIALS`
* `SEND_GRID_API_KEY`

## GitHub Actions Variables

Values can be found in the corresponding files located in `~/.dmua/variables`:

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
