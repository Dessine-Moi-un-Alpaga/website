ifdef CI
export TF_IN_AUTOMATION = true
else
DMUA_SECRETS := $(HOME)/.dmua/secrets
DMUA_VARIABLES := $(HOME)/.dmua/variables
export ARTIFACT_REGISTRY_LOCATION ?= $(shell cat "$(DMUA_VARIABLES)/ARTIFACT_REGISTRY_LOCATION")
export ARTIFACT_REGISTRY = $(ARTIFACT_REGISTRY_LOCATION)-docker.pkg.dev
export ARTIFACT_REPOSITORY ?= $(shell cat "$(DMUA_VARIABLES)/ARTIFACT_REPOSITORY")
export GOOGLE_PROJECT ?= $(shell cat "$(DMUA_VARIABLES)/GOOGLE_PROJECT")
export DOCKER_IMAGE = $(ARTIFACT_REGISTRY)/$(GOOGLE_PROJECT)/$(ARTIFACT_REPOSITORY)/website
export CREDENTIALS ?= $(shell cat "$(DMUA_SECRETS)/CREDENTIALS")
export FIRESTORE_LOCATION ?= $(shell cat "$(DMUA_VARIABLES)/FIRESTORE_LOCATION")
export SEND_GRID_API_KEY ?= $(shell cat "$(DMUA_SECRETS)/SEND_GRID_API_KEY")
export VERSION ?= $(shell git describe --tags --always --first-parent)
endif

.PHONY: app/%
app/%:
	@$(MAKE) --directory=app $*

.PHONY: infrastructure/%
infrastructure/%:
	@$(MAKE) --directory=infrastructure $*

.PHONY: release/%
release/%:
	@$(MAKE) infrastructure/infra/init \
		&& $(MAKE) infrastructure/infra/plan \
		&& $(MAKE) infrastructure/infra/apply \
		&& $(MAKE) app/push-$* \
		&& $(MAKE) infrastructure/app/init-$* \
		&& $(MAKE) infrastructure/app/plan-$* \
		&& $(MAKE) infrastructure/app/apply

.PHONY: download-%-assets
download-%-assets: BUCKET_NAME = $(shell cat "$(DMUA_VARIABLES)/$*/BUCKET_NAME")
download-%-assets:
	@gsutil -m rsync -r -d gs://$(BUCKET_NAME) assets/

.PHONY: upload-%-assets
upload-%-assets: BUCKET_NAME = $(shell cat "$(DMUA_VARIABLES)/$*/BUCKET_NAME")
upload-%-assets:
	@gsutil -m rsync -x ".DS_Store" -r -d assets/ gs://$(BUCKET_NAME)
