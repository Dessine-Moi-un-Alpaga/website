terraform {
  required_providers {
    github = {
      source = "integrations/github"
      version = "~> 6.6.0"
    }
    google = {
      source = "hashicorp/google"
      version = "~> 6.30.0"
    }
    local = {
      source = "hashicorp/local"
      version = "~> 2.5.1"
    }
  }
}
