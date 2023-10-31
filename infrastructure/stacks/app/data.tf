data "google_client_config" "default" {}

data "google_artifact_registry_repository" "repository" {
  location      = var.artifact_registry_location
  repository_id = var.artifact_repository
}

data "google_secret_manager_secret" "send_grid_api_key_secret" {
  secret_id = "send-grid-api-key"
}

data "google_secret_manager_secret" "credentials_secret" {
  secret_id = "credentials"
}
