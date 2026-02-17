resource "github_repository" "git_repository" {
  allow_merge_commit   = false
  allow_update_branch  = true
  description          = var.github_repository_description
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

resource "random_bytes" "gradle_configuration_cache_encryption_key" {
  length = 16
}

locals {
  secrets = {
    CREDENTIALS           = local.credentials
    GRADLE_ENCRYPTION_KEY = random_bytes.gradle_configuration_cache_encryption_key.base64
    SMTP_SERVER_PASSWORD  = var.smtp_server_password
    SONARCLOUD_TOKEN      = var.sonarcloud_token
  }
  variables = {
    ARTIFACT_REGISTRY_LOCATION = var.artifact_registry_location
    ARTIFACT_REPOSITORY        = var.artifact_repository
    DOMAIN_NAME                = var.domain_name
    FIRESTORE_LOCATION         = var.firestore_location
    GOOGLE_PROJECT             = var.project_id
    GOOGLE_PROJECT_NUMBER      = var.project_number
    GOOGLE_REGION              = var.region
    SMTP_SERVER_ADDRESS        = var.smtp_server_address
    SMTP_SERVER_PORT           = var.smtp_server_port
    SMTP_SERVER_USERNAME       = var.smtp_server_username
  }
}

resource "github_repository_environment" "development_environment" {
  environment = "development"
  repository  = github_repository.git_repository.name
}

resource "github_repository_environment" "production_environment" {
  environment = "production"
  repository  = github_repository.git_repository.name
}

resource "github_actions_environment_variable" "development_variables" {
  for_each = local.variables

  environment   = github_repository_environment.development_environment.environment
  repository    = github_repository.git_repository.name
  value         = each.value
  variable_name = each.key
}

resource "github_actions_environment_variable" "production_variables" {
  for_each = local.variables

  environment   = github_repository_environment.production_environment.environment
  repository    = github_repository.git_repository.name
  value         = each.value
  variable_name = each.key
}

resource "github_actions_environment_secret" "development_secrets" {
  for_each = local.secrets

  plaintext_value = each.value
  environment     = github_repository_environment.development_environment.environment
  repository      = github_repository.git_repository.name
  secret_name     = each.key
}

resource "github_actions_environment_secret" "production_secrets" {
  for_each = local.secrets

  plaintext_value = each.value
  environment     = github_repository_environment.production_environment.environment
  repository      = github_repository.git_repository.name
  secret_name     = each.key
}

resource "github_actions_environment_variable" "development_bucket_name_variable" {
  environment   = github_repository_environment.development_environment.environment
  repository    = github_repository.git_repository.name
  value         = var.dev_bucket_name
  variable_name = "BUCKET_NAME"
}

resource "github_actions_environment_variable" "production_bucket_name_variable" {
  environment   = github_repository_environment.production_environment.environment
  repository    = github_repository.git_repository.name
  value         = var.prod_bucket_name
  variable_name = "BUCKET_NAME"
}

resource "github_actions_environment_variable" "development_cors_origins_variable" {
  environment   = github_repository_environment.development_environment.environment
  repository    = github_repository.git_repository.name
  value         = "[\"*\"]"
  variable_name = "CORS_ORIGINS"
}

resource "github_actions_environment_variable" "production_cors_origins_variable" {
  environment   = github_repository_environment.production_environment.environment
  repository    = github_repository.git_repository.name
  value         = "[\"https://${var.domain_name}\"]"
  variable_name = "CORS_ORIGINS"
}

resource "github_actions_environment_variable" "development_create_domain_mapping_variable" {
  environment   = github_repository_environment.development_environment.environment
  repository    = github_repository.git_repository.name
  value         = false
  variable_name = "CREATE_DOMAIN_MAPPING"
}

resource "github_actions_environment_variable" "production_create_domain_mapping_variable" {
  environment   = github_repository_environment.production_environment.environment
  repository    = github_repository.git_repository.name
  value         = true
  variable_name = "CREATE_DOMAIN_MAPPING"
}

resource "github_actions_environment_variable" "development_environment_variable" {
  environment   = github_repository_environment.development_environment.environment
  repository    = github_repository.git_repository.name
  value         = "development"
  variable_name = "ENVIRONMENT"
}

resource "github_actions_environment_variable" "production_environment_variable" {
  environment   = github_repository_environment.production_environment.environment
  repository    = github_repository.git_repository.name
  value         = "production"
  variable_name = "ENVIRONMENT"
}