resource "github_repository" "git_repository" {
  allow_merge_commit   = false
  allow_update_branch  = true
  description          = var.github_repository_description
  has_downloads        = true
  has_issues           = true
  has_projects         = true
  has_wiki             = true
  homepage_url         = "https://${var.domain_name}"
  name                 = var.github_repository
  vulnerability_alerts = true

  pages {
    build_type = "workflow"

    source {
      branch = "main"
      path   = "/"
    }
  }
}

resource "random_password" "password" {
  length = 12
  lower  = true
}

resource "github_actions_variable" "artifact_registry_location" {
  repository    = github_repository.git_repository.name
  value         = var.artifact_registry_location
  variable_name = "ARTIFACT_REGISTRY_LOCATION"
}

resource "github_actions_variable" "dev_bucket" {
  repository    = github_repository.git_repository.name
  value         = var.dev_bucket_name
  variable_name = "DEV_BUCKET"
}

resource "github_actions_variable" "domain_name" {
  repository    = github_repository.git_repository.name
  value         = var.domain_name
  variable_name = "DOMAIN_NAME"
}

resource "github_actions_variable" "firestore_location" {
  repository    = github_repository.git_repository.name
  value         = var.firestore_location
  variable_name = "FIRESTORE_LOCATION"
}

resource "github_actions_variable" "google_project" {
  repository    = github_repository.git_repository.name
  value         = var.project_id
  variable_name = "GOOGLE_PROJECT"
}

resource "github_actions_variable" "google_project_name" {
  repository    = github_repository.git_repository.name
  value         = var.project_name
  variable_name = "GOOGLE_PROJECT_NAME"
}

resource "github_actions_variable" "google_project_number" {
  repository    = github_repository.git_repository.name
  value         = var.project_number
  variable_name = "GOOGLE_PROJECT_NUMBER"
}

resource "github_actions_variable" "google_region" {
  repository    = github_repository.git_repository.name
  value         = var.region
  variable_name = "GOOGLE_REGION"
}

resource "github_actions_variable" "google_zone" {
  repository    = github_repository.git_repository.name
  value         = var.zone
  variable_name = "GOOGLE_ZONE"
}

resource "github_actions_variable" "prod_bucket" {
  repository    = github_repository.git_repository.name
  value         = var.prod_bucket_name
  variable_name = "PROD_BUCKET"
}

resource "github_actions_secret" "credentials" {
  plaintext_value = "${var.username}:${random_password.password.bcrypt_hash}"
  repository      = github_repository.git_repository.name
  secret_name     = "CREDENTIALS"
}

resource "github_actions_secret" "send_grid_api_key" {
  plaintext_value = var.send_grid_api_key
  repository      = github_repository.git_repository.name
  secret_name     = "SEND_GRID_API_KEY"
}

resource "github_actions_secret" "sonarcloud_token" {
  plaintext_value = var.sonarcloud_token
  repository      = github_repository.git_repository.name
  secret_name     = "SONARCLOUD_TOKEN"
}
