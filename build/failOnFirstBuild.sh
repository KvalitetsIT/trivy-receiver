#!/bin/sh

echo "${GITHUB_REPOSITORY}"
echo "${DOCKER_SERVICE}"
if [ "${GITHUB_REPOSITORY}" != "KvalitetsIT/trivy-receiver" ] && [ "${DOCKER_SERVICE}" = "kvalitetsit/trivy-receiver" ]; then
  echo "Please run setup.sh REPOSITORY_NAME"
  exit 1
fi
