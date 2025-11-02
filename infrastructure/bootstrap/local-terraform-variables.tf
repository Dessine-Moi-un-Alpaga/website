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
  password                      = "${var.password}"
  prod_bucket_name              = "${var.prod_bucket_name}"
  project_id                    = "${var.project_id}"
  project_name                  = "${var.project_name}"
  project_number                = "${var.project_number}"
  region                        = "${var.region}"
  send_grid_api_key             = "${var.send_grid_api_key}"
  smtp_server_address           = "${var.smtp_server_address}"
  smtp_server_password          = "${var.smtp_server_password}"
  smtp_server_port              = "${var.smtp_server_port}"
  smtp_server_username          = "${var.smtp_server_username}"
  sonarcloud_token              = "${var.sonarcloud_token}"
  username                      = "${var.username}"
  zone                          = "${var.zone}"
  EOT
  filename = "${var.home_directory}/.dmua/bootstrap/terraform.tfvars"
}

locals {
  credentials = "${var.username}:${bcrypt(var.password, var.bcrypt_cost)}"
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
  filename = "${var.home_directory}/.dmua/variables/development/BUCKET_NAME"
}

resource "local_file" "prod_bucket_name_variable_file" {
  content  = var.prod_bucket_name
  filename = "${var.home_directory}/.dmua/variables/production/BUCKET_NAME"
}

resource "local_file" "dev_cors_origins_variable_file" {
  content  = "[\"*\"]"
  filename = "${var.home_directory}/.dmua/variables/development/CORS_ORIGINS"
}

resource "local_file" "prod_cors_origins_variable_file" {
  content  = "[\"https://${var.domain_name}\"]"
  filename = "${var.home_directory}/.dmua/variables/production/CORS_ORIGINS"
}

resource "local_file" "dev_create_domain_mapping_variable_file" {
  content  = false
  filename = "${var.home_directory}/.dmua/variables/development/CREATE_DOMAIN_MAPPING"
}

resource "local_file" "prod_create_domain_mapping_variable_file" {
  content  = true
  filename = "${var.home_directory}/.dmua/variables/production/CREATE_DOMAIN_MAPPING"
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

resource "local_file" "smtp_server_address_variable_file" {
  content = var.smtp_server_address
  filename = "${var.home_directory}/.dmua/variables/SMTP_SERVER_ADDRESS"
}

resource "local_file" "smtp_server_port_variable_file" {
  content = var.smtp_server_port
  filename = "${var.home_directory}/.dmua/variables/SMTP_SERVER_PORT"
}

resource "local_file" "smtp_server_username_variable_file" {
  content = var.smtp_server_username
  filename = "${var.home_directory}/.dmua/variables/SMTP_SERVER_USERNAME"
}

resource "local_sensitive_file" "credentials_secret_file" {
  content  = local.credentials
  filename = "${var.home_directory}/.dmua/secrets/CREDENTIALS"
}

resource "local_sensitive_file" "send_grid_api_key_secret_file" {
  content  = var.send_grid_api_key
  filename = "${var.home_directory}/.dmua/secrets/SEND_GRID_API_KEY"
}

resource "local_sensitive_file" "smt_server_password_secret_file" {
  content  = var.smtp_server_password
  filename = "${var.home_directory}/.dmua/secrets/SMTP_SERVER_PASSWORD"
}

resource "local_sensitive_file" "sonarcloud_token_secret_file" {
  content  = var.sonarcloud_token
  filename = "${var.home_directory}/.dmua/secrets/SONARCLOUD_TOKEN"
}
