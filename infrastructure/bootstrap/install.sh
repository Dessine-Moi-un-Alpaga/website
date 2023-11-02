#!/usr/bin/env bash

PARANOIA_LEVEL=12

set -euo pipefail

DMUA_HOME="${HOME}/.dmua"
DMUA_SECRETS="${DMUA_HOME}/secrets"
DMUA_VARIABLES="${DMUA_HOME}/variables"

mkdir -p "${DMUA_SECRETS}"
mkdir -p "${DMUA_VARIABLES}"

chmod go-rwx "${DMUA_HOME}"
chmod go-rwx "${DMUA_SECRETS}"
chmod go-rwx "${DMUA_VARIABLES}"

function prompt_for_configuration_item() {
  display_name="$1"
  name="$2"
  default_value="${3:-}"
  is_secret="${4:-false}"
  prompt="${display_name} ["

  if [ "${is_secret}" = "false" ]
  then
    filename="${DMUA_VARIABLES}/${name}"
  else
    filename="${DMUA_SECRETS}/${name}"
  fi

  if [ -f "${filename}" ]
  then
    default_value=$(cat "${filename}")

    if [ "${is_secret}" = "false" ]
    then
      prompt="${prompt}${default_value}"
    else
      prompt="${prompt}** SECRET **"
    fi

    prompt="${prompt} (from ${filename})]: "
  else
    prompt="${prompt}${default_value}]: "
  fi

  echo ""
  read -p "${prompt}" value
  value=${value:-${default_value}}
  echo -n "${value}" > "${filename}"
  chmod go-rwx "${filename}"
  eval "${name}"='${value}'
}

function prompt_for_initial_api_credentials() {
  credentials_file="${DMUA_SECRETS}/CREDENTIALS"

  echo ""

  if [ -f "${credentials_file}" ]
  then
    echo "Existing API credentials were found in ${credentials_file}"
  else
    default_username="gilles"
    read -p "Username [${default_username}]: " username
    username=${username:-${default_username}}
    htpasswd -Bc -C ${PARANOIA_LEVEL} "${credentials_file}" ${username}
    chmod go-rwx "${credentials_file}"
    echo "Credentials saved to ${credentials_file}"
  fi
  echo "You can manage its contents directly using the htpasswd command"
  echo "WARNING: Only the bcrypt hash function is supported (htpasswd -B)"
  echo ""
}

prompt_for_configuration_item "Google Organization" "GOOGLE_ORGANIZATION" "787638144393"
prompt_for_configuration_item "Google Billing Account" "GOOGLE_BILLING_ACCOUNT" "012570-9EAEA4-73AB1E"
prompt_for_configuration_item "Google Project Name" "GOOGLE_PROJECT_NAME" "website"
prompt_for_configuration_item "Google Project ID" "GOOGLE_PROJECT" "gitops-8ab10a6068"
prompt_for_configuration_item "Domain Name" "DOMAIN_NAME" "dessinemoiunalpaga.com"
prompt_for_configuration_item "Google Region" "GOOGLE_REGION" "europe-north1"
prompt_for_configuration_item "Google Zone" "GOOGLE_ZONE" "europe-north1-a"
prompt_for_configuration_item "Artifact Registry Location" "ARTIFACT_REGISTRY_LOCATION" "europe"
prompt_for_configuration_item "Firestore Location" "FIRESTORE_LOCATION" "europe-west4"
prompt_for_configuration_item "SendGrid API Key" "SEND_GRID_API_KEY" "" "true"
prompt_for_configuration_item "GitHub Account or Organization" "GITHUB_ACCOUNT" "gilles-gosuin"
prompt_for_configuration_item "GitHub Repository" "GITHUB_REPOSITORY" "dessine-moi-un-alpaga"
prompt_for_configuration_item "Development Assets Google Cloud Bucket" "DEV_BUCKET" "maoqhh387yb56rt5"
prompt_for_configuration_item "Production Assets Google Cloud Bucket" "PROD_BUCKET" "assets.dessinemoiunalpaaga.com"

prompt_for_initial_api_credentials

gcloud auth login

set +e
gcloud projects describe "${GOOGLE_PROJECT}" > /dev/null 2>&1
result=$?
set -e

if [ "$result" != "0" ]
then
  echo -n "Google Project... "

  gcloud projects create "${GOOGLE_PROJECT}" \
    --labels=firebase=enabled \
    --name="${GOOGLE_PROJECT_NAME}" \
    --organization="${GOOGLE_ORGANIZATION}" \
    > /dev/null 2>&1

  echo "OK"
fi

google_project_number_file="${DMUA_VARIABLES}/GOOGLE_PROJECT_NUMBER"
GOOGLE_PROJECT_NUMBER=$(gcloud projects describe "${GOOGLE_PROJECT}" --format="value(projectNumber)")
echo -n "${GOOGLE_PROJECT_NUMBER}" > "${google_project_number_file}"
chmod go-rwx "${google_project_number_file}"

echo -n "Google Project Link to Billing Account... "
gcloud beta billing projects link "${GOOGLE_PROJECT}" \
  --billing-account="${GOOGLE_BILLING_ACCOUNT}" \
   > /dev/null 2>&1
echo "OK"

echo -n "Artifact Registry API... "
gcloud services enable artifactregistry.googleapis.com --project="${GOOGLE_PROJECT}"
echo "OK"

echo -n "IAM Credentials API... "
gcloud services enable iamcredentials.googleapis.com --project "${GOOGLE_PROJECT}"
echo "OK"

echo -n "Secret Manager API... "
gcloud services enable secretmanager.googleapis.com --project="${GOOGLE_PROJECT}"
echo "OK"

terraform_service_account_name=terraform
terraform_service_account_id="${terraform_service_account_name}@${GOOGLE_PROJECT}.iam.gserviceaccount.com"

set +e
gcloud iam service-accounts describe "${terraform_service_account_id}" > /dev/null 2>&1
result=$?
set -e

if [ "$result" != "0" ]
then
  echo -n "Terraform Service Account... "
  gcloud iam service-accounts create "${SERVICE_ACCOUNT_ID}" \
    --project="${PROJECT_ID}"
  echo "OK"
fi

gcloud projects add-iam-policy-binding "${GOOGLE_PROJECT}" \
  --member="serviceAccount:${terraform_service_account_id}" \
  --role="roles/owner" \
  > /dev/null 2>&1

gcloud projects add-iam-policy-binding "${GOOGLE_PROJECT}" \
  --member="serviceAccount:${terraform_service_account_id}" \
  --role="roles/run.admin" \
  > /dev/null 2>&1

terraform_state_bucket="gs://terraform-state-${GOOGLE_PROJECT}"

set +e
gsutil ls -b "${terraform_state_bucket}" > /dev/null 2>&1
result=$?
set -e

if [ "$result" != "0" ]
then
  echo -n "Terraform State... "

  gsutil mb \
    -b on \
    -l "${GOOGLE_REGION}" \
    -p "${GOOGLE_PROJECT}" \
    "${terraform_state_bucket}" \
    > /dev/null 2>&1

  echo "OK"
fi

workload_identity_location="global"
workload_identity_pool="github-actions-workflows"

set +e
gcloud iam workload-identity-pools describe "${workload_identity_pool}" \
  --location="${workload_identity_location}" \
  > /dev/null 2>&1
result=$?
set -e

if [ "$?" != "0" ]
then
  echo "GitHub Action Workflows Identity Pool... "
  gcloud iam workload-identity-pools create "${workload_identity_pool}" \
    --project="${GOOGLE_PROJECT}" \
    --location="${wworkload_identity_location}" \
    --display-name="GitHub Actions Workflows" \
    > /dev/null 2>&1
  echo "OK"
fi

set +e
workload_identity_pool_provider="github-actions"
gcloud iam workload-identity-pools providers describe "${workload_identity_pool_provider}" \
  --project="${GOOGLE_PROJECT}" \
  --location="${workload_identity_location}" \
  --workload-identity-pool="${workload_identity_pool}" \
  > /dev/null 2>&1
result=$?
set -e

if [ "$result" != "0" ]
then
  echo "GitHub Actions Workload Identity Pool Provider... "
  gcloud iam workload-identity-pools providers create-oidc "${workload_identity_pool_provider}" \
    --project="${GOOGLE_PROJECT}" \
    --location="${workload_identity_location}" \
    --workload-identity-pool="${workload_identity_pool}" \
    --display-name="GitHub Actions" \
    --attribute-mapping="google.subject=assertion.sub,attribute.actor=assertion.actor,attribute.repository=assertion.repository" \
    --issuer-uri="https://token.actions.githubusercontent.com"
    > /dev/null 2>&1
    echo "OK"
fi

workload_identity_pool_id=$(gcloud iam workload-identity-pools describe "${workload_identity_pool}" \
  --project="${GOOGLE_PROJECT}" \
  --location="${workload_identity_location}" \
  --format="value(name)")

gcloud iam service-accounts add-iam-policy-binding "${terraform_service_account_id}" \
  --project="${GOOGLE_PROJECT}" \
  --role="roles/iam.workloadIdentityUser" \
  --member="principalSet://iam.googleapis.com/${workload_identity_pool_id}/attribute.repository/${GITHUB_ACCOUNT}/${GITHUB_REPOSITORY}" \
  > /dev/null 2>&1

echo ""
echo "Project bootstrap successful"
