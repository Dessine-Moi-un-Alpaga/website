variable "artifact_registry_location" {
  type = string
}

variable "artifact_repository" {
  type = string
}

variable "credentials" {
  sensitive = true
  type      = string
}

variable "firestore_location" {
  type = string
}

variable "send_grid_api_key" {
  sensitive = true
  type      = string
}
