variable "artifact_registry_location" {
  description = "Artifact Registry Location"
  nullable    = false
  type        = string
}

variable "artifact_repository" {
  description = "Artifact Repository"
  nullable    = false
  type        = string
}

variable "bcrypt_cost" {
  default  = 12
  nullable = false
  type     = number
}

variable "billing_account" {
  description = "Google Billing Account"
  nullable    = false
  type        = string
}

variable "dev_bucket_name" {
  description = "Website Asset Bucket (Development)"
  nullable    = false
  type        = string
}

variable "domain_name" {
  description = "Domain Name"
  nullable    = false
  type        = string
}

variable "firestore_location" {
  description = "Firestore Location"
  nullable    = false
  type        = string
}

variable "github_account" {
  description = "GitHub Account"
  nullable    = false
  type        = string
}

variable "github_repository" {
  description = "GitHub Repository"
  nullable    = false
  type        = string
}

variable "github_repository_description" {
  description = "GitHub Repository Description"
  nullable    = false
  type        = string
}

variable "home_directory" {
  description = "Home directory"
  nullable    = false
  type        = string
}

variable "organization_id" {
  description = "Google Cloud Organization ID"
  nullable    = false
  type        = string
}

variable "password" {
  description = "API Password"
  nullable    = false
  sensitive   = true
  type        = string
}

variable "prod_bucket_name" {
  description = "Website Asset Bucket (Production)"
  nullable    = false
  type        = string
}

variable "project_id" {
  description = "Google Cloud Project ID"
  nullable    = false
  type        = string
}

variable "project_name" {
  description = "Google Cloud Project Name"
  nullable    = false
  type        = string
}

variable "project_number" {
  description = "Google Cloud Project Number"
  nullable    = false
  type        = string
}

variable "region" {
  description = "Google Cloud Region"
  nullable    = false
  type        = string
}

variable "send_grid_api_key" {
  description = "SendGrid API Key"
  nullable    = false
  sensitive   = true
  type        = string
}

variable "smtp_server_address" {
  description = "SMTP Server Address"
  nullable    = false
  type        = string
}

variable "smtp_server_password" {
  description = "SMTP Server Password"
  nullable    = false
  sensitive   = true
  type        = string
}

variable "smtp_server_port" {
  description = "SMTP Server Port"
  nullable    = false
  type        = string
}

variable "smtp_server_username" {
  description = "SMTP Server Username"
  nullable    = false
  type        = string
}

variable "sonarcloud_token" {
  description = "Sonarcloud Token"
  nullable    = false
  sensitive   = true
  type        = string
}

variable "username" {
  description = "Website API Username"
  nullable    = false
  type        = string
}

variable "zone" {
  description = "Google Cloud Zone"
  nullable    = false
  type        = string
}
