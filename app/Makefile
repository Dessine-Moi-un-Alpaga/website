DMUA_SECRETS := $(HOME)/.dmua/secrets
DMUA_VARIABLES := $(HOME)/.dmua/variables

FIREBASE_LOCATION = $(shell which firebase)

.PHONY: run
run: export ORG_GRADLE_PROJECT_credentials = $(value CREDENTIALS)
run: export ORG_GRADLE_PROJECT_devBucket = $(shell cat "$(DMUA_VARIABLES)/development/BUCKET_NAME")
run: export ORG_GRADLE_PROJECT_firebaseLocation = $(FIREBASE_LOCATION)
run: export ORG_GRADLE_PROJECT_googleProject = $(GOOGLE_PROJECT)
run: export ORG_GRADLE_PROJECT_sendGridApiKey = $(SEND_GRID_API_KEY)
run: export ORG_GRADLE_PROJECT_smtpServerAddress = $(SMTP_SERVER_ADDRESS)
run: export ORG_GRADLE_PROJECT_smtpServerPassword = $(SMTP_SERVER_PASSWORD)
run: export ORG_GRADLE_PROJECT_smtpServerPort = $(SMTP_SERVER_PORT)
run: export ORG_GRADLE_PROJECT_smtpServerUsername = $(SMTP_SERVER_USERNAME)
run:
	@./gradlew run

.PHONY: test
test: export ORG_GRADLE_PROJECT_firebaseLocation = $(shell which firebase)
test:
	@./gradlew test jacocoTestReport

.PHONY: prepare-native-build
prepare-native-build: export ORG_GRADLE_PROJECT_credentials = $(value CREDENTIALS)
prepare-native-build: export ORG_GRADLE_PROJECT_devBucket = $(shell cat "$(DMUA_VARIABLES)/development/BUCKET_NAME")
prepare-native-build: export ORG_GRADLE_PROJECT_firebaseLocation = $(FIREBASE_LOCATION)
prepare-native-build: export ORG_GRADLE_PROJECT_googleProject = $(GOOGLE_PROJECT)
prepare-native-build: export ORG_GRADLE_PROJECT_sendGridApiKey = $(SEND_GRID_API_KEY)
prepare-native-build: export ORG_GRADLE_PROJECT_smtpServerAddress = $(SMTP_SERVER_ADDRESS)
prepare-native-build: export ORG_GRADLE_PROJECT_smtpServerPassword = $(SMTP_SERVER_PASSWORD)
prepare-native-build: export ORG_GRADLE_PROJECT_smtpServerPort = $(SMTP_SERVER_PORT)
prepare-native-build: export ORG_GRADLE_PROJECT_smtpServerUsername = $(SMTP_SERVER_USERNAME)
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
