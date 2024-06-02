DMUA_SECRETS := $(HOME)/.dmua/secrets
DMUA_VARIABLES := $(HOME)/.dmua/variables

.PHONY: init
init: GOOGLE_PROJECT ?= $(shell cat "$(DMUA_VARIABLES)/GOOGLE_PROJECT")
init: export TF_CLI_ARGS_init = -input=false -reconfigure -upgrade -backend-config=bucket=terraform-state-$(GOOGLE_PROJECT) -backend-config=prefix=common/infra
init:
	@terraform init

.PHONY: plan
plan: export TF_CLI_ARGS_plan = -input=false -out=tfplan
plan: export TF_VAR_artifact_registry_location ?= $(shell cat "$(DMUA_VARIABLES)/ARTIFACT_REGISTRY_LOCATION")
plan: export TF_VAR_artifact_repository ?= $(shell cat "$(DMUA_VARIABLES)/ARTIFACT_REPOSITORY")
plan: export TF_VAR_credentials ?= $(shell cat "$(DMUA_SECRETS)/CREDENTIALS")
plan: export TF_VAR_firestore_location ?= $(shell cat "$(DMUA_VARIABLES)/FIRESTORE_LOCATION")
plan: export TF_VAR_send_grid_api_key ?= $(shell cat "$(DMUA_SECRETS)/SEND_GRID_API_KEY")
plan:
	@terraform plan

.PHONY: apply
apply: export TF_CLI_ARGS_apply = -auto-approve tfplan
apply:
	@terraform apply
