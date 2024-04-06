resource "google_storage_bucket" "static_resource_bucket" {
  cors {
    max_age_seconds = 3600
    method          = [
      "GET",
      "HEAD",
      "OPTIONS"
    ]
    origin          = var.environments[var.environment].cors_origins
    response_header = ["*"]
  }
  force_destroy               = true
  location                    = var.location
  name                        = var.bucket_name
  uniform_bucket_level_access = true

  versioning {
    enabled = false
  }
}

resource "google_storage_bucket_iam_binding" "static_resource_bucket_public_access" {
  bucket  = google_storage_bucket.static_resource_bucket.name
  role    = "roles/storage.objectViewer"
  members = [
    "allUsers"
  ]
}

resource "google_service_account" "service_account" {
  account_id = var.environment
  project    = data.google_client_config.default.project
}

resource "google_artifact_registry_repository_iam_member" "artifact_registry_repository_binding" {
  location   = data.google_artifact_registry_repository.repository.location
  member     = "serviceAccount:${google_service_account.service_account.email}"
  project    = data.google_client_config.default.project
  repository = data.google_artifact_registry_repository.repository.repository_id
  role       = "roles/artifactregistry.reader"
}

resource "google_cloud_run_v2_service" "cloud_run_service" {
  ingress  = "INGRESS_TRAFFIC_ALL"
  location = var.location
  name     = var.environment

  template {
    execution_environment = "EXECUTION_ENVIRONMENT_GEN1"
    service_account       = google_service_account.service_account.email

    containers {
      image = var.docker_tag

      env {
        name  = "DMUA_BASE_ASSET_URL"
        value = "https://storage.googleapis.com/${google_storage_bucket.static_resource_bucket.name}"
      }

      env {
        name = "DMUA_CREDENTIALS"

        value_source {
          secret_key_ref {
            secret  = data.google_secret_manager_secret.credentials_secret.secret_id
            version = "latest"
          }
        }
      }

      env {
        name = "DMUA_EMAIL_ADDRESS"
        value = "contact@dessinemoiunalpaga.com"
      }

      env {
        name  = "DMUA_ENVIRONMENT"
        value = var.environment
      }

      env {
        name  = "DMUA_PROJECT"
        value = data.google_client_config.default.project
      }

      env {
        name = "DMUA_SEND_GRID_API_KEY"

        value_source {
          secret_key_ref {
            secret  = data.google_secret_manager_secret.send_grid_api_key_secret.secret_id
            version = "latest"
          }
        }
      }

      resources {
        cpu_idle = true
        limits   = {
          cpu    = "1"
          memory = "128Mi"
        }
        startup_cpu_boost = true
      }
    }

    scaling {
      min_instance_count = 0
      max_instance_count = 4
    }
  }
}

resource "google_cloud_run_v2_service_iam_member" "anonymous" {
  location = google_cloud_run_v2_service.cloud_run_service.location
  member   = "allUsers"
  name     = google_cloud_run_v2_service.cloud_run_service.name
  role     = "roles/run.invoker"
}

resource "google_cloud_run_domain_mapping" "application_domain_mapping" {
  count = var.environments[var.environment].create_domain_mapping ? 1 : 0

  location = google_cloud_run_v2_service.cloud_run_service.location
  name     = var.domain_name

  metadata {
    namespace = data.google_client_config.default.project
  }

  spec {
    route_name = google_cloud_run_v2_service.cloud_run_service.name
  }
}

resource "google_secret_manager_secret_iam_member" "send_grid_api_key_secret_accessor" {
  member    = "serviceAccount:${google_service_account.service_account.email}"
  role      = "roles/secretmanager.secretAccessor"
  secret_id = data.google_secret_manager_secret.send_grid_api_key_secret.secret_id
}

resource "google_secret_manager_secret_iam_member" "credentials_secret_accessor" {
  member    = "serviceAccount:${google_service_account.service_account.email}"
  role      = "roles/secretmanager.secretAccessor"
  secret_id = data.google_secret_manager_secret.credentials_secret.secret_id
}

resource "google_project_iam_member" "firestore_user" {
  member  = "serviceAccount:${google_service_account.service_account.email}"
  project = data.google_client_config.default.project
  role    = "roles/datastore.user"
}
