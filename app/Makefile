FIREBASE_LOCATION := $(shell which firebase)

GRADLE_PROJECT_VARIABLES := \
	-Pcredentials=$(CREDENTIALS) \
	-PdevBucket=$(DEV_BUCKET) \
	-PfirebaseLocation=$(FIREBASE_LOCATION) \
	-PgoogleProject=$(GOOGLE_PROJECT) \
	-PsendGridApiKey=$(SEND_GRID_API_KEY) \
	-Pversion=$(VERSION)

prepare-native-build:
	@./gradlew $(GRADLE_PROJECT_VARIABLES) -Pagent run

push:
	@gcloud auth configure-docker $(ARTIFACT_REGISTRY) --quiet \
		&& docker buildx build --push --tag $(DOCKER_TAG) --tag $(DOCKER_IMAGE):$(CHANNEL) --cache-from type=gha,scope=main --cache-to type=gha,mode=max,scope=main .

%:
	@./gradlew $(GRADLE_PROJECT_VARIABLES) $*
