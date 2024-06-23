.PHONY: init-%
init-%: export TF_CLI_ARGS_init = -input=false -reconfigure -upgrade -backend-config=bucket=terraform-state-$(GOOGLE_PROJECT) -backend-config=prefix=$*/app
init-%:
	@terraform init

.PHONY: plan-%
plan-%: export TF_CLI_ARGS_plan = -input=false -out=tfplan
plan-%: export TF_VAR_artifact_registry_location = $(ARTIFACT_REGISTRY_LOCATION)
plan-%: export TF_VAR_artifact_repository = $(ARTIFACT_REPOSITORY)
plan-%: export TF_VAR_bucket_name = $(BUCKET_NAME)
plan-%: export TF_VAR_cors_origins = $(CORS_ORIGINS)
plan-%: export TF_VAR_create_domain_mapping = $(CREATE_DOMAIN_MAPPING)
plan-%: export TF_VAR_domain_name = $(DOMAIN_NAME)
plan-%: export TF_VAR_environment = $*
plan-%: export TF_VAR_image = $(DOCKER_IMAGE):$(VERSION)
plan-%: export TF_VAR_location = $(LOCATION)
plan-%:
	@terraform plan

.PHONY: apply
apply: export TF_CLI_ARGS_apply = -auto-approve tfplan
apply:
	@terraform apply
