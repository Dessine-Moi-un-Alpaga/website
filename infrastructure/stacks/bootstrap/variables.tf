variable "artifact_registry_location" {
  description = "Artifact Registry Location"
  type        = string
}

variable "artifact_repository" {
  description = "Artifact Repository"
  type        = string
}

variable "bcrypt_cost" {
  default = 12
  type    = number
}

variable "billing_account" {
  description = "Google Billing Account"
  type        = string
}

variable "dev_bucket_name" {
  description = "Website Asset Bucket (Development)"
  type        = string
}

variable "domain_name" {
  description = "Domain Name"
  type        = string
}

variable "firestore_location" {
  description = "Firestore Location"
  type        = string
}

variable "github_account" {
  description = "GitHub Account"
  type        = string
}

variable "github_repository" {
  description = "GitHub Repository"
  type        = string
}

variable "github_repository_description" {
  description = "GitHub Repository Description"
  type        = string
}

variable "home_directory" {
  description = "Home directory"
  type        = string
}

variable "organization_id" {
  description = "Google Cloud Organization ID"
  type        = string
}

variable "prod_bucket_name" {
  description = "Website Asset Bucket (Production)"
  type        = string
}

variable "project_id" {
  description = "Google Cloud Project ID"
  type        = string
}

variable "project_name" {
  description = "Google Cloud Project Name"
  type        = string
}

variable "project_number" {
  description = "Google Cloud Project Number"
  type        = string
}

variable "region" {
  description = "Google Cloud Region"
  type        = string
}

variable "send_grid_api_key" {
  description = "SendGrid API Key"
  sensitive   = true
  type        = string
}

variable "sonarcloud_token" {
  description = "Sonarcloud Token"
  sensitive   = true
  type        = string
}

variable "username" {
  description = "Website API Username"
  type        = string
}

variable "zone" {
  description = "Google Cloud Zone"
  type        = string
}