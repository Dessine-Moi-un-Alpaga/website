terraform {
  required_providers {
    github = {
      source = "integrations/github"
      version = "~> 6.5.0"
    }
    google = {
      source = "hashicorp/google"
      version = "~> 6.18.0"
    }
    local = {
      source = "hashicorp/local"
      version = "~> 2.5.1"
    }
  }
}
