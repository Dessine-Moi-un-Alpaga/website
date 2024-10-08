terraform {
  required_providers {
    github = {
      source = "integrations/github"
      version = "~> 6.2.0"
    }
    google = {
      source = "hashicorp/google"
      version = "~> 6.0.1"
    }
    local = {
      source = "hashicorp/local"
      version = "~> 2.5.1"
    }
    random = {
      source = "hashicorp/random"
      version = "~> 3.6.0"
    }
  }
}
