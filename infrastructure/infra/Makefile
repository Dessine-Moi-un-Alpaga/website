.PHONY: init
init: export TF_CLI_ARGS_init = -input=false -reconfigure -upgrade -backend-config=bucket=terraform-state-$(GOOGLE_PROJECT) -backend-config=prefix=common/infra
init:
	@terraform init

.PHONY: plan
plan: export TF_CLI_ARGS_plan = -input=false -out=tfplan
plan: export TF_VAR_artifact_registry_location = $(ARTIFACT_REGISTRY_LOCATION)
plan: export TF_VAR_artifact_repository = $(ARTIFACT_REPOSITORY)
plan: export TF_VAR_credentials = $(CREDENTIALS)
plan: export TF_VAR_firestore_location = $(FIRESTORE_LOCATION)
plan: export TF_VAR_send_grid_api_key = $(SEND_GRID_API_KEY)
plan:
	@terraform plan

.PHONY: apply
apply: export TF_CLI_ARGS_apply = -auto-approve tfplan
apply:
	@terraform apply
