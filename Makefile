export DMUA_HOME := $(HOME)/.dmua
export DMUA_SECRETS := $(DMUA_HOME)/secrets
export DMUA_VARIABLES := $(DMUA_HOME)/variables

export ARTIFACT_REGISTRY_LOCATION ?= $(shell cat "$(DMUA_VARIABLES)/ARTIFACT_REGISTRY_LOCATION")
export CREDENTIALS ?= $(shell cat "$(DMUA_SECRETS)/CREDENTIALS")
export DEV_BUCKET ?= $(shell cat "$(DMUA_VARIABLES)/DEV_BUCKET")
export GOOGLE_PROJECT ?= $(shell cat "$(DMUA_VARIABLES)/GOOGLE_PROJECT")

PROD_BUCKET ?= $(shell cat "$(DMUA_VARIABLES)/PROD_BUCKET")
export SEND_GRID_API_KEY ?= $(shell cat "$(DMUA_SECRETS)/SEND_GRID_API_KEY")
export VERSION ?= $(shell git describe --tags --always --first-parent)

export ARTIFACT_REGISTRY := $(ARTIFACT_REGISTRY_LOCATION)-docker.pkg.dev

export DOCKER_IMAGE := $(ARTIFACT_REGISTRY)/$(GOOGLE_PROJECT)/$(ARTIFACT_REPOSITORY)/website
export DOCKER_TAG := $(ARTIFACT_REGISTRY)/$(GOOGLE_PROJECT)/$(ARTIFACT_REPOSITORY)/website:$(VERSION)

bootstrap:
	@bash infrastructure/bootstrap/install.sh

%-app:
	$(MAKE) --directory app $*

%-infra:
	$(MAKE) --directory infrastructure $*

dev:
	$(MAKE) --directory=infrastructure init \
		&& $(MAKE) --directory=infrastructure apply \
		&& $(MAKE) --directory=app CHANNEL=dev push \
		&& $(MAKE) --directory=infrastructure init-dev \
		&& $(MAKE) --directory=infrastructure apply-dev

prod:
	$(MAKE) --directory=infrastructure init \
		&& $(MAKE) --directory=infrastructure apply \
		&& $(MAKE) --directory=app CHANNEL=prod push \
		&& $(MAKE) --directory=infrastructure init-prod \
		&& $(MAKE) --directory=infrastructure apply-prod

tear-down:
	@bash infrastructure/bootstrap/uninstall.sh

dev-assets-to-local:
	@gsutil -m rsync -r -d gs://$(DEV_BUCKET) assets/

prod-assets-to-local:
	@gsutil -m rsync -r -d gs://$(PROD_BUCKET) assets/

local-assets-to-dev:
	@gsutil -m rsync -x ".DS_Store" -r -d assets/ gs://$(DEV_BUCKET)

dev-assets-to-prod:
	@gsutil -m rsync -r -d gs://$(DEV_BUCKET) gs://$(PROD_BUCKET)
