resource "bcrypt_hash" "password_hash" {
  cleartext = var.password
  cost      = var.bcrypt_cost
}

locals {
  credentials = "${var.username}:${bcrypt_hash.password_hash.id}"
}

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

resource "local_file" "dotenv_variables" {
  content = <<-EOT
  ARTIFACT_REGISTRY_LOCATION=${var.artifact_registry_location}
  ARTIFACT_REPOSITORY=${var.artifact_repository}
  DOMAIN_NAME=${var.domain_name}
  FIRESTORE_LOCATION=${var.firestore_location}
  GOOGLE_PROJECT=${var.project_id}
  GOOGLE_REGION=${var.region}
  GOOGLE_ZONE=${var.zone}
  SMTP_SERVER_ADDRESS=${var.smtp_server_address}
  SMTP_SERVER_PORT=${var.smtp_server_port}
  SMTP_SERVER_USERNAME=${var.smtp_server_username}
  EOT
  filename = "${var.home_directory}/.dmua/variables/.env"
}

resource "local_file" "dotenv_development_variables" {
  content = <<-EOT
  BUCKET_NAME=${var.dev_bucket_name}
  CORS_ORIGINS="[\"*\"]"
  CREATE_DOMAIN_MAPPING=false
  EOT
  filename = "${var.home_directory}/.dmua/variables/development/.env"
}

resource "local_file" "dotenv_production_variables" {
  content = <<-EOT
  BUCKET_NAME=${var.prod_bucket_name}
  CORS_ORIGINS="[\"https://${var.domain_name}\"]"
  CREATE_DOMAIN_MAPPING=true
  EOT
  filename = "${var.home_directory}/.dmua/variables/production/.env"
}

resource "local_sensitive_file" "dotenv_secrets" {
  content = <<-EOT
  CREDENTIALS='${local.credentials}'
  SMTP_SERVER_PASSWORD=${var.smtp_server_password}
  SONARCLOUD_TOKEN=${var.sonarcloud_token}
  EOT
  filename = "${var.home_directory}/.dmua/secrets/.env"
}
