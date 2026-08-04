variable "api_key_hash" {
  nullable = false
  type     = string
}

variable "artifact_registry_location" {
  nullable = false
  type     = string
}

variable "artifact_repository" {
  nullable = false
  type     = string
}

variable "firestore_location" {
  nullable = false
  type     = string
}

variable "smtp_server_password" {
  nullable  = false
  sensitive = true
  type      = string
}
