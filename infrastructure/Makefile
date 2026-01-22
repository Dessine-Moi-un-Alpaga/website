ifdef CI
export TF_IN_AUTOMATION = true
endif

.PHONY: app/%
app/%:
	@$(MAKE) --directory=app $*

.PHONY: bootstrap/%
bootstrap/%:
	@$(MAKE) --directory=bootstrap $*

.PHONY: infra/%
infra/%:
	@$(MAKE) --directory=infra $*
