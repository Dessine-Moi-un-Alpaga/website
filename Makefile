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

download-%-assets: BUCKET_NAME = $(shell cat "$(DMUA_VARIABLES)/$*/BUCKET_NAME")
download-%-assets:
	@gsutil -m rsync -r -d gs://$(BUCKET_NAME) assets/

upload-%-assets: BUCKET_NAME = $(shell cat "$(DMUA_VARIABLES)/$*/BUCKET_NAME")
upload-%-assets:
	@gsutil -m rsync -x ".DS_Store" -r -d assets/ gs://$(BUCKET_NAME)
