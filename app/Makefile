.PHONY: test
test: export ORG_GRADLE_PROJECT_firebaseLocation = $(shell which firebase)
test:
	@./gradlew test jacocoTestReport

.PHONY: push
push-%: ARTIFACT_REGISTRY_LOCATION ?= $(shell cat "$(DMUA_VARIABLES)/ARTIFACT_REGISTRY_LOCATION")
push-%: ARTIFACT_REGISTRY = $(ARTIFACT_REGISTRY_LOCATION)-docker.pkg.dev
push-%: ARTIFACT_REPOSITORY ?= $(shell cat "$(DMUA_VARIABLES)/ARTIFACT_REPOSITORY")
push-%: DOCKER_IMAGE = $(ARTIFACT_REGISTRY)/$(GOOGLE_PROJECT)/$(ARTIFACT_REPOSITORY)/website
push-%: GOOGLE_PROJECT ?= $(shell cat "$(DMUA_VARIABLES)/GOOGLE_PROJECT")
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
