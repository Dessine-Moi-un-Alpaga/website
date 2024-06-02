ifeq ($(CI),true)
$(error "Bootstrap must only be run locally")
endif

WORK_DIR := $(HOME)/.dmua/bootstrap
VARIABLE_FILE := $(WORK_DIR)/terraform.tfvars

export TF_CLI_ARGS_init := -backend-config=path=$(WORK_DIR)/terraform.tfstate
export TF_CLI_ARGS_plan := -out=tfplan -var-file=$(VARIABLE_FILE)
export TF_CLI_ARGS_apply := tfplan

.PHONY: plan
plan:
	@touch $(VARIABLE_FILE)
	@terraform init
	@terraform plan

.PHONY: apply
apply:
	@terraform apply
