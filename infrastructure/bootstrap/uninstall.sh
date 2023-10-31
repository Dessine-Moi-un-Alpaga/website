#!/usr/bin/env bash

project_id_file="${HOME}/.dmua/GOOGLE_PROJECT"

if [ -f "${project_id_file}" ]
then
  GOOGLE_PROJECT=$(cat "${project_id_file}")
  gcloud auth login
  gcloud projects delete "${GOOGLE_PROJECT}" --quiet
fi

rm -rf "~/.dmua"
echo "Project tear down successful"
