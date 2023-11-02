DMUA_HOME=$(HOME)/.dmua
DMUA_SECRETS=$(DMUA_HOME)/secrets
DMUA_VARIABLES=$(DMUA_HOME)/variables

GOOGLE_PROJECT ?= $(shell cat "$(DMUA_VARIABLES)/GOOGLE_PROJECT")
VERSION ?= $(shell git describe --tags --always --first-parent)

ARTIFACT_REGISTRY_LOCATION ?= $(shell cat "$(DMUA_VARIABLES)/ARTIFACT_REGISTRY_LOCATION")
ARTIFACT_REGISTRY = $(ARTIFACT_REGISTRY_LOCATION)-docker.pkg.dev
ARTIFACT_REPOSITORY = common
CREDENTIALS ?= $(shell cat "$(DMUA_SECRETS)/CREDENTIALS")
DEV_BUCKET ?= $(shell cat "$(DMUA_VARIABLES)/DEV_BUCKET")
DOCKER_TAG = $(ARTIFACT_REGISTRY)/$(GOOGLE_PROJECT)/$(ARTIFACT_REPOSITORY)/website:$(VERSION)
DOMAIN_NAME ?= $(shell cat "$(DMUA_VARIABLES)/DOMAIN_NAME")
FIRESTORE_LOCATION ?= $(shell cat "$(DMUA_VARIABLES)/FIRESTORE_LOCATION")
GOOGLE_REGION ?= $(shell cat "$(DMUA_VARIABLES)/GOOGLE_REGION")
GOOGLE_ZONE ?= $(shell cat "$(DMUA_VARIABLES)/GOOGLE_ZONE")
PROD_BUCKET ?= $(shell cat "$(DMUA_VARIABLES)/PROD_BUCKET")
SEND_GRID_API_KEY ?= $(shell cat "$(DMUA_SECRETS)/SEND_GRID_API_KEY")

export GOOGLE_PROJECT
export GOOGLE_REGION
export GOOGLE_ZONE

TERRAFORM_INFRA_VARS = \
	-var 'artifact_registry_location=$(ARTIFACT_REGISTRY_LOCATION)' \
	-var 'artifact_repository=$(ARTIFACT_REPOSITORY)' \
	-var 'credentials=$(CREDENTIALS)' \
	-var 'firestore_location=$(FIRESTORE_LOCATION)' \
	-var 'send_grid_api_key=$(SEND_GRID_API_KEY)'

TERRAFORM_APP_VARS = \
	-var 'artifact_registry_location=$(ARTIFACT_REGISTRY_LOCATION)' \
	-var 'artifact_repository=$(ARTIFACT_REPOSITORY)' \
	-var 'docker_tag=$(DOCKER_TAG)' \
	-var 'location=$(GOOGLE_REGION)'

TERRAFORM_DEV_APP_VARS = \
	$(TERRAFORM_APP_VARS) \
	-var-file=variables/development.tfvars \
	-var 'bucket_name=$(DEV_BUCKET)'

TERRAFORM_PROD_APP_VARS = \
	$(TERRAFORM_APP_VARS) \
	-var-file=variables/production.tfvars \
	-var 'bucket_name=$(PROD_BUCKET)' \
	-var 'cors_origins=["https://$(DOMAIN_NAME)"]' \
	-var 'domain_name=$(DOMAIN_NAME)'

TERRAFORM_INFRA_GLOBAL_OPTIONS = -chdir=infrastructure/stacks/infra
TERRAFORM_APP_GLOBAL_OPTIONS = -chdir=infrastructure/stacks/app

TERRAFORM_BACKEND_OPTIONS = -backend-config=bucket=terraform-state-$(GOOGLE_PROJECT)
TERRAFORM_INFRA_BACKEND_OPTIONS = $(TERRAFORM_BACKEND_OPTIONS) -backend-config=prefix=common/infra
TERRAFORM_DEV_APP_BACKEND_OPTIONS = $(TERRAFORM_BACKEND_OPTIONS) -backend-config=prefix=development/app
TERRAFORM_PROD_APP_BACKEND_OPTIONS = $(TERRAFORM_BACKEND_OPTIONS) -backend-config=prefix=production/app

TERRAFORM_APPLY_OPTIONS = -input=false -auto-approve
TERRAFORM_INIT_OPTIONS = -input=false -reconfigure -upgrade
TERRAFORM_PLAN_OPTIONS = -input=false
TERRAFORM_UNLOCK_OPTIONS = -force ${LOCK_ID}

run:
	@cd app && ./gradlew run

push:
	@cd app \
		&& gcloud auth configure-docker $(ARTIFACT_REGISTRY) --quiet \
		&& docker buildx build --push --tag $(DOCKER_TAG) --cache-from type=gha --cache-to type=gha,mode=max .

bootstrap:
	@bash infrastructure/bootstrap/install.sh

init-infra:
	@terraform $(TERRAFORM_INFRA_GLOBAL_OPTIONS) init $(TERRAFORM_INIT_OPTIONS) $(TERRAFORM_INFRA_BACKEND_OPTIONS) $(TERRAFORM_INFRA_VARS)

plan-infra:
	@terraform $(TERRAFORM_INFRA_GLOBAL_OPTIONS) plan $(TERRAFORM_PLAN_OPTIONS) $(TERRAFORM_INFRA_VARS)

apply-infra:
	@terraform $(TERRAFORM_INFRA_GLOBAL_OPTIONS) apply $(TERRAFORM_APPLY_OPTIONS) $(TERRAFORM_INFRA_VARS)

init-dev:
	@terraform $(TERRAFORM_APP_GLOBAL_OPTIONS) init $(TERRAFORM_INIT_OPTIONS) $(TERRAFORM_DEV_APP_BACKEND_OPTIONS) $(TERRAFORM_DEV_APP_VARS)

init-prod:
	@terraform $(TERRAFORM_APP_GLOBAL_OPTIONS) init $(TERRAFORM_INIT_OPTIONS) $(TERRAFORM_PROD_APP_BACKEND_OPTIONS) $(TERRAFORM_PROD_APP_VARS)

plan-dev:
	@terraform $(TERRAFORM_APP_GLOBAL_OPTIONS) plan $(TERRAFORM_PLAN_OPTIONS) $(TERRAFORM_DEV_APP_VARS)

plan-prod:
	@terraform $(TERRAFORM_APP_GLOBAL_OPTIONS) plan $(TERRAFORM_PLAN_OPTIONS) $(TERRAFORM_PROD_APP_VARS)

apply-dev:
	@terraform $(TERRAFORM_APP_GLOBAL_OPTIONS) apply $(TERRAFORM_APPLY_OPTIONS) $(TERRAFORM_DEV_APP_VARS)

apply-prod:
	@terraform $(TERRAFORM_APP_GLOBAL_OPTIONS) apply $(TERRAFORM_APPLY_OPTIONS) $(TERRAFORM_PROD_APP_VARS)

unlock-infra:
	@terraform $(TERRAFORM_INFRA_GLOBAL_OPTIONS) force-unlock $(TERRAFORM_UNLOCK_OPTIONS)

unlock-app:
	@terraform $(TERRAFORM_APP_GLOBAL_OPTIONS) force-unlock $(TERRAFORM_UNLOCK_OPTIONS)

dev:
	$(MAKE) init-infra \
		&& $(MAKE) apply-infra \
		&& $(MAKE) push \
		&& $(MAKE) init-dev \
		&& $(MAKE) apply-dev

prod:
	$(MAKE) init-infra \
		&& $(MAKE) apply-infra \
		&& $(MAKE) push \
		&& $(MAKE) init-prod \
		&& $(MAKE) apply-prod

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
