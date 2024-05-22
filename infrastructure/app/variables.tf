variable "artifact_registry_location" {
  type = string
}

variable "artifact_repository" {
  type = string
}

variable "bucket_name" {
  type = string
}

variable "cors_origins" {
  default = []
  type    = list(string)
}

variable "create_domain_mapping" {
  default = false
  type    = bool
}

variable "docker_tag" {
  type = string
}

variable "domain_name" {
  default = null
  type    = string
}

variable "environment" {
  type = string
}

variable "location" {
  type = string
}
