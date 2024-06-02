DMUA_SECRETS := $(HOME)/.dmua/secrets
DMUA_VARIABLES := $(HOME)/.dmua/variables

.PHONY: init-%
init-%: GOOGLE_PROJECT ?= $(shell cat "$(DMUA_VARIABLES)/GOOGLE_PROJECT")
init-%: export TF_CLI_ARGS_init = -input=false -reconfigure -upgrade -backend-config=bucket=terraform-state-$(GOOGLE_PROJECT) -backend-config=prefix=$*/app
init-%:
	@terraform init

.PHONY: plan-%
plan-%: VERSION ?= $(shell git describe --tags --always --first-parent)
plan-%: export GOOGLE_PROJECT ?= $(shell cat "$(DMUA_VARIABLES)/GOOGLE_PROJECT")
plan-%: ARTIFACT_REGISTRY_LOCATION ?= $(shell cat "$(DMUA_VARIABLES)/ARTIFACT_REGISTRY_LOCATION")
plan-%: ARTIFACT_REGISTRY = $(ARTIFACT_REGISTRY_LOCATION)-docker.pkg.dev
plan-%: ARTIFACT_REPOSITORY ?= $(shell cat "$(DMUA_VARIABLES)/ARTIFACT_REPOSITORY")
plan-%: DOCKER_IMAGE = $(ARTIFACT_REGISTRY)/$(GOOGLE_PROJECT)/$(ARTIFACT_REPOSITORY)/website
plan-%: export TF_CLI_ARGS_plan = -input=false -out=tfplan
plan-%: export TF_VAR_artifact_registry_location = $(ARTIFACT_REGISTRY_LOCATION)
plan-%: export TF_VAR_artifact_repository = $(ARTIFACT_REPOSITORY)
plan-%: export TF_VAR_bucket_name ?= $(shell cat "$(DMUA_VARIABLES)/$*/BUCKET_NAME")
plan-%: export TF_VAR_cors_origins ?= $(shell cat "$(DMUA_VARIABLES)/$*/CORS_ORIGINS")
plan-%: export TF_VAR_create_domain_mapping ?= $(shell cat "$(DMUA_VARIABLES)/$*/CREATE_DOMAIN_MAPPING")
plan-%: export TF_VAR_domain_name ?= $(shell cat "$(DMUA_VARIABLES)/DOMAIN_NAME")
plan-%: export TF_VAR_environment = $*
plan-%: export TF_VAR_image = $(DOCKER_IMAGE):$(VERSION)
plan-%: export TF_VAR_location ?= $(shell cat "$(DMUA_VARIABLES)/GOOGLE_REGION")
plan-%:
	@terraform plan

.PHONY: apply
apply: export TF_CLI_ARGS_apply = -auto-approve tfplan
apply:
	@terraform apply
