terraform {
  required_providers {
    github = {
      source = "integrations/github"
      version = "~> 6.4.0"
    }
    google = {
      source = "hashicorp/google"
      version = "~> 6.14.1"
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
