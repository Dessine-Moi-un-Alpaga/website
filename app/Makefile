ifdef CI
GRADLE_BUILD_SCAN_SWITCH := --scan
endif

export ORG_GRADLE_PROJECT_credentials = $(value CREDENTIALS)
export ORG_GRADLE_PROJECT_devBucket = $(shell cat "$(DMUA_VARIABLES)/development/BUCKET_NAME")
export ORG_GRADLE_PROJECT_firebaseLocation = $(shell which firebase)
export ORG_GRADLE_PROJECT_googleProject = $(GOOGLE_PROJECT)
export ORG_GRADLE_PROJECT_smtpServerAddress = $(SMTP_SERVER_ADDRESS)
export ORG_GRADLE_PROJECT_smtpServerPassword = $(SMTP_SERVER_PASSWORD)
export ORG_GRADLE_PROJECT_smtpServerPort = $(SMTP_SERVER_PORT)
export ORG_GRADLE_PROJECT_smtpServerUsername = $(SMTP_SERVER_USERNAME)

.PHONY: build
build:
	@./gradlew $(GRADLE_BUILD_SCAN_SWITCH) test jacocoTestReport shadowJar

.PHONY: prepare-native-build
prepare-native-build:
	@./gradlew -Pagent run

.PHONY: push-%
push-%:
	@gcloud auth configure-docker $(ARTIFACT_REGISTRY) --quiet \
		&& docker buildx build \
			--push \
			--tag $(DOCKER_IMAGE):$(VERSION) \
			--tag $(DOCKER_IMAGE):$* \
			--cache-from type=gha,scope=main \
			--cache-to type=gha,mode=max,scope=main \
			.

.PHONY: run
run:
	@./gradlew run

.PHONY: test
test:
	@./gradlew $(GRADLE_BUILD_SCAN_SWITCH) test
