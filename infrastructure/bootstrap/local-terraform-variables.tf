resource "local_sensitive_file" "variables" {
  content  = <<-EOT
  artifact_registry_location    = "${var.artifact_registry_location}"
  artifact_repository           = "${var.artifact_repository}"
  billing_account               = "${var.billing_account}"
  dev_bucket_name               = "${var.dev_bucket_name}"
  domain_name                   = "${var.domain_name}"
  firestore_location            = "${var.firestore_location}"
  github_account                = "${var.github_account}"
  github_repository             = "${var.github_repository}"
  github_repository_description = "${var.github_repository_description}"
  home_directory                = "${var.home_directory}"
  organization_id               = "${var.organization_id}"
  prod_bucket_name              = "${var.prod_bucket_name}"
  project_id                    = "${var.project_id}"
  project_name                  = "${var.project_name}"
  project_number                = "${var.project_number}"
  region                        = "${var.region}"
  send_grid_api_key             = "${var.send_grid_api_key}"
  sonarcloud_token              = "${var.sonarcloud_token}"
  username                      = "${var.username}"
  zone                          = "${var.zone}"
  EOT
  filename = "${var.home_directory}/.dmua/bootstrap/terraform.tfvars"
}

resource "random_password" "password" {
  length = 12
  lower  = true
}

locals {
  credentials = "${var.username}:${random_password.password.bcrypt_hash}"
}

resource "local_file" "artifact_registry_location_variable_file" {
  content  = var.artifact_registry_location
  filename = "${var.home_directory}/.dmua/variables/ARTIFACT_REGISTRY_LOCATION"
}

resource "local_file" "artifact_repository_location_variable_file" {
  content  = var.artifact_repository
  filename = "${var.home_directory}/.dmua/variables/ARTIFACT_REPOSITORY"
}

resource "local_file" "dev_bucket_name_variable_file" {
  content  = var.dev_bucket_name
  filename = "${var.home_directory}/.dmua/variables/DEV_BUCKET"
}

resource "local_file" "domain_name_variable_file" {
  content  = var.domain_name
  filename = "${var.home_directory}/.dmua/variables/DOMAIN_NAME"
}

resource "local_file" "firestore_location_variable_file" {
  content  = var.firestore_location
  filename = "${var.home_directory}/.dmua/variables/FIRESTORE_LOCATION"
}

resource "local_file" "google_project_id_variable_file" {
  content  = var.project_id
  filename = "${var.home_directory}/.dmua/variables/GOOGLE_PROJECT"
}

resource "local_file" "google_region_variable_file" {
  content  = var.region
  filename = "${var.home_directory}/.dmua/variables/GOOGLE_REGION"
}

resource "local_file" "google_zone_variable_file" {
  content  = var.zone
  filename = "${var.home_directory}/.dmua/variables/GOOGLE_ZONE"
}

resource "local_file" "prod_bucket_name_variable_file" {
  content  = var.prod_bucket_name
  filename = "${var.home_directory}/.dmua/variables/PROD_BUCKET"
}

resource "local_sensitive_file" "credentials_secret_file" {
  content  = local.credentials
  filename = "${var.home_directory}/.dmua/secrets/CREDENTIALS"
}

resource "local_sensitive_file" "send_grid_api_key_secret_file" {
  content  = var.send_grid_api_key
  filename = "${var.home_directory}/.dmua/secrets/SEND_GRID_API_KEY"
}

resource "local_sensitive_file" "sonarcloud_token_secret_file" {
  content  = var.sonarcloud_token
  filename = "${var.home_directory}/.dmua/secrets/SONARCLOUD_TOKEN"
}
