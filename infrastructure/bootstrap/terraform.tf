terraform {
  required_providers {
    github = {
      source = "integrations/github"
      version = "~> 6.7"
    }
    google = {
      source = "hashicorp/google"
      version = "~> 7.9"
    }
    local = {
      source = "hashicorp/local"
      version = "~> 2.5"
    }
    random = {
      source = "hashicorp/random"
      version = "~> 3.8"
    }
  }
}
