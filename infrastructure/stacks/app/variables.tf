variable "artifact_registry_location" {
  type = string
}

variable "artifact_repository" {
  type = string
}

variable "bucket_name" {
  type = string
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

variable "environments" {
  type = map(object({
    cors_origins           = optional(list(string))
    create_domain_mapping  =  optional(bool, false)
  }))
}

variable "location" {
  type = string
}
