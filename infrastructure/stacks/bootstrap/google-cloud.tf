resource "google_project" "project" {
  billing_account = var.billing_account
  name            = var.project_name
  project_id      = var.project_id
  org_id          = var.organization_id
}

resource "google_storage_bucket" "terraform_state" {
  location                    = var.region
  name                        = "terraform-state-${google_project.project.project_id}"
  project                     = google_project.project.project_id
  uniform_bucket_level_access = true
}

resource "google_iam_workload_identity_pool" "github_actions_workflow_identity_pool" {
  display_name              = "GitHub Actions Workflows"
  project                   = google_project.project.project_id
  workload_identity_pool_id = "github-actions-workflows"
}

resource "google_iam_workload_identity_pool_provider" "github_actions_workflow_identity_pool_provider" {
  attribute_condition = "assertion.repository == 'Dessine-Moi-un-Alpaga/website'"
  attribute_mapping   = {
    "google.subject"       = "assertion.sub"
    "attribute.actor"      = "assertion.actor"
    "attribute.repository" = "assertion.repository"
  }
  display_name                       = "GitHub Actions"
  project                            = google_project.project.project_id
  workload_identity_pool_id          = google_iam_workload_identity_pool.github_actions_workflow_identity_pool.workload_identity_pool_id
  workload_identity_pool_provider_id = "github-actions"

  oidc {
    issuer_uri = "https://token.actions.githubusercontent.com"
  }
}

resource "google_service_account" "terraform_service_account" {
  account_id = "terraform"
  project    = google_project.project.project_id
}

resource "google_project_iam_member" "terraform_service_account_project_owner" {
  member  = "serviceAccount:${google_service_account.terraform_service_account.email}"
  role    = "roles/owner"
  project = google_project.project.project_id
}

resource "google_project_iam_member" "terraform_service_account_cloud_run_admin" {
  member  = "serviceAccount:${google_service_account.terraform_service_account.email}"
  role    = "roles/run.admin"
  project = google_project.project.project_id
}

resource "google_service_account_iam_member" "github_actions_workload_identity_user" {
  member             = "principalSet://iam.googleapis.com/projects/${google_project.project.number}/locations/global/workloadIdentityPools/${google_iam_workload_identity_pool.github_actions_workflow_identity_pool.workload_identity_pool_id}/attribute.repository/${var.github_account}/${var.github_repository}"
  role               = "roles/iam.workloadIdentityUser"
  service_account_id = google_service_account.terraform_service_account.name
}
