TERRAFORM_INFRA_VARS := \
	-var 'artifact_registry_location=$(ARTIFACT_REGISTRY_LOCATION)' \
	-var 'artifact_repository=$(ARTIFACT_REPOSITORY)' \
	-var 'credentials=$(value CREDENTIALS)' \
	-var 'firestore_location=$(FIRESTORE_LOCATION)' \
	-var 'send_grid_api_key=$(SEND_GRID_API_KEY)'

TERRAFORM_APP_VARS := \
	-var 'artifact_registry_location=$(ARTIFACT_REGISTRY_LOCATION)' \
	-var 'artifact_repository=$(ARTIFACT_REPOSITORY)' \
	-var 'docker_tag=$(DOCKER_TAG)' \
	-var 'environments={"development":{"bucket_name":"$(DEV_BUCKET)","cors_origins":["*"]},"production":{"bucket_name":"$(PROD_BUCKET)","create_domain_mapping":true}}' \
	-var 'location=$(GOOGLE_REGION)'

TERRAFORM_DEV_APP_VARS := \
	$(TERRAFORM_APP_VARS) \
	-var 'environment=development'

TERRAFORM_PROD_APP_VARS := \
	$(TERRAFORM_APP_VARS) \
	-var 'environment=production'

TERRAFORM_INFRA_GLOBAL_OPTIONS := -chdir=stacks/infra
TERRAFORM_APP_GLOBAL_OPTIONS := -chdir=stacks/app

TERRAFORM_BACKEND_OPTIONS := -backend-config=bucket=terraform-state-$(GOOGLE_PROJECT)
TERRAFORM_INFRA_BACKEND_OPTIONS := $(TERRAFORM_BACKEND_OPTIONS) -backend-config=prefix=common/infra
TERRAFORM_DEV_APP_BACKEND_OPTIONS := $(TERRAFORM_BACKEND_OPTIONS) -backend-config=prefix=development/app
TERRAFORM_PROD_APP_BACKEND_OPTIONS := $(TERRAFORM_BACKEND_OPTIONS) -backend-config=prefix=production/app

TERRAFORM_APPLY_OPTIONS := -input=false -auto-approve
TERRAFORM_INIT_OPTIONS := -input=false -reconfigure -upgrade
TERRAFORM_PLAN_OPTIONS := -input=false
TERRAFORM_UNLOCK_OPTIONS := -force ${LOCK_ID}

init:
	@terraform $(TERRAFORM_INFRA_GLOBAL_OPTIONS) init $(TERRAFORM_INIT_OPTIONS) $(TERRAFORM_INFRA_BACKEND_OPTIONS) $(TERRAFORM_INFRA_VARS)

plan:
	@terraform $(TERRAFORM_INFRA_GLOBAL_OPTIONS) plan $(TERRAFORM_PLAN_OPTIONS) $(TERRAFORM_INFRA_VARS)

apply:
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

unlock:
	@terraform $(TERRAFORM_INFRA_GLOBAL_OPTIONS) force-unlock $(TERRAFORM_UNLOCK_OPTIONS)

unlock-app:
	@terraform $(TERRAFORM_APP_GLOBAL_OPTIONS) force-unlock $(TERRAFORM_UNLOCK_OPTIONS)
