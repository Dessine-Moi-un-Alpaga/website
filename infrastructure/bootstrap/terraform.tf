terraform {
  required_providers {
    bcrypt = {
      source = "viktorradnai/bcrypt"
      version = "0.1.2"
    }
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
