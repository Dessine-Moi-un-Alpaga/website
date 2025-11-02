variable "artifact_registry_location" {
  nullable = false
  type     = string
}

variable "artifact_repository" {
  nullable = false
  type     = string
}

variable "credentials" {
  nullable  = false
  sensitive = true
  type      = string
}

variable "firestore_location" {
  nullable = false
  type     = string
}

variable "send_grid_api_key" {
  nullable  = false
  sensitive = true
  type      = string
}

variable "smtp_server_password" {
  nullable  = false
  sensitive = true
  type      = string
}
