DMUA_SECRETS := $(HOME)/.dmua/secrets
DMUA_VARIABLES := $(HOME)/.dmua/variables

.PHONY: run
run: export ORG_GRADLE_PROJECT_credentials = $(shell cat "$(DMUA_SECRETS)/CREDENTIALS")
run: export ORG_GRADLE_PROJECT_devBucket = $(shell cat "$(DMUA_VARIABLES)/development/BUCKET_NAME")
run: export ORG_GRADLE_PROJECT_firebaseLocation = $(shell which firebase)
run: export ORG_GRADLE_PROJECT_googleProject = $(shell cat "$(DMUA_VARIABLES)/GOOGLE_PROJECT")
run: export ORG_GRADLE_PROJECT_sendGridApiKey = $(shell cat "$(DMUA_SECRETS)/SEND_GRID_API_KEY")
run:
	@./gradlew run

.PHONY: test
test: export ORG_GRADLE_PROJECT_firebaseLocation = $(shell which firebase)
test:
	@./gradlew test jacocoTestReport

.PHONY: prepare-native-build
prepare-native-build: export ORG_GRADLE_PROJECT_credentials = $(shell cat "$(DMUA_SECRETS)/CREDENTIALS")
prepare-native-build: export ORG_GRADLE_PROJECT_devBucket = $(shell cat "$(DMUA_VARIABLES)/development/BUCKET_NAME")
prepare-native-build: export ORG_GRADLE_PROJECT_firebaseLocation = $(shell which firebase)
prepare-native-build: export ORG_GRADLE_PROJECT_googleProject = $(shell cat "$(DMUA_VARIABLES)/GOOGLE_PROJECT")
prepare-native-build: export ORG_GRADLE_PROJECT_sendGridApiKey = $(shell cat "$(DMUA_SECRETS)/SEND_GRID_API_KEY")
prepare-native-build:
	@./gradlew -Pagent run

.PHONY: push-%
push-%: ARTIFACT_REGISTRY_LOCATION ?= $(shell cat "$(DMUA_VARIABLES)/ARTIFACT_REGISTRY_LOCATION")
push-%: ARTIFACT_REGISTRY = $(ARTIFACT_REGISTRY_LOCATION)-docker.pkg.dev
push-%: ARTIFACT_REPOSITORY ?= $(shell cat "$(DMUA_VARIABLES)/ARTIFACT_REPOSITORY")
push-%: GOOGLE_PROJECT ?= $(shell cat "$(DMUA_VARIABLES)/GOOGLE_PROJECT")
push-%: DOCKER_IMAGE = $(ARTIFACT_REGISTRY)/$(GOOGLE_PROJECT)/$(ARTIFACT_REPOSITORY)/website
push-%: VERSION ?= $(shell git describe --tags --always --first-parent)
push-%:
	@gcloud auth configure-docker $(ARTIFACT_REGISTRY) --quiet \
		&& docker buildx build \
			--push \
			--tag $(DOCKER_IMAGE):$(VERSION) \
			--tag $(DOCKER_IMAGE):$* \
			--cache-from type=gha,scope=main \
			--cache-to type=gha,mode=max,scope=main \
			.
