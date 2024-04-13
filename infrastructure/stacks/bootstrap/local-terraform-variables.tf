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

resource "local_sensitive_file" "infra_variables" {
  content  = <<-EOT
  artifact_registry_location = "${var.artifact_registry_location}"
  artifact_repository        = "${var.artifact_repository}"
  credentials                = "${var.username}:${random_password.password.bcrypt_hash}"
  firestore_location         = "${var.firestore_location}"
  send_grid_api_key          = "${var.send_grid_api_key}"
  EOT
  filename = "${path.module}/../infra/terraform.tfvars"
}

resource "local_sensitive_file" "app_variables" {
  content  = <<-EOT
  artifact_registry_location = "${var.artifact_registry_location}"
  artifact_repository        = "${var.artifact_repository}"
  domain_name                = "${var.domain_name}"
  environments               = {
    development = {
      bucket_name  = "${var.dev_bucket_name}"
      cors_origins = ["*"]
    }
    production = {
      bucket_name           = "${var.prod_bucket_name}"
      create_domain_mapping = true
    }
  }
  location = "${var.region}"
  EOT
  filename = "${path.module}/../app/terraform.tfvars"
}
