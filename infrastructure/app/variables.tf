variable "artifact_registry_location" {
  nullable = false
  type     = string
}

variable "artifact_repository" {
  nullable = false
  type     = string
}

variable "bucket_name" {
  nullable = false
  type     = string
}

variable "cors_origins" {
  default = []
  nullable = false
  type = list(string)
}

variable "create_domain_mapping" {
  default  = false
  nullable = false
  type     = bool
}

variable "domain_name" {
  default = null
  type    = string
}

variable "environment" {
  nullable = false
  type     = string
}

variable "image" {
  nullable = false
  type     = string
}

variable "location" {
  nullable = false
  type     = string
}
