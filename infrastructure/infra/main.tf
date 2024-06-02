resource "google_artifact_registry_repository" "artifact_registry" {
  provider = google-beta

  format        = "docker"
  location      = var.artifact_registry_location
  repository_id = var.artifact_repository

  cleanup_policies {
    id = "delete-all"
    action = "DELETE"

    condition {}
  }

  cleanup_policies {
    id = "keep-dev-and-prod-releases"
    action = "KEEP"

    condition {
      tag_state = "TAGGED"
      tag_prefixes = [
        "dev",
        "prod",
        "development",
        "production",
      ]
    }
  }
}

resource "google_project_service" "cloud_resource_manager_api" {
  disable_dependent_services = false
  disable_on_destroy         = false
  service                    = "cloudresourcemanager.googleapis.com"
}

resource "google_project_service" "cloud_run_api" {
  disable_dependent_services = false
  disable_on_destroy         = false
  service                    = "run.googleapis.com"
}

resource "google_project_service" "iam_service_api" {
  disable_dependent_services = false
  disable_on_destroy         = false
  service                    = "iam.googleapis.com"

  depends_on = [
    google_project_service.cloud_resource_manager_api
  ]
}

resource "google_project_service" "firestore" {
  disable_dependent_services = false
  disable_on_destroy         = false
  service                    = "firestore.googleapis.com"
}

resource "google_firestore_database" "database" {
  name                        = "(default)"
  location_id                 = var.firestore_location
  type                        = "FIRESTORE_NATIVE"
  concurrency_mode            = "PESSIMISTIC"
  app_engine_integration_mode = "DISABLED"

  depends_on = [google_project_service.firestore]
}

resource "google_firebase_project" "default" {
  provider = google-beta
}

resource "google_secret_manager_secret" "send_grid_api_key_secret" {
  secret_id = "send-grid-api-key"

  replication {
    auto {}
  }
}

resource "google_secret_manager_secret" "credentials_secret" {
  secret_id = "credentials"

  replication {
    auto {}
  }
}

resource "google_secret_manager_secret_version" "send_grid_api_key_secret_version" {
  secret      = google_secret_manager_secret.send_grid_api_key_secret.id
  secret_data = var.send_grid_api_key
}

resource "google_secret_manager_secret_version" "credentials_secret_version" {
  secret      = google_secret_manager_secret.credentials_secret.id
  secret_data = var.credentials
}
