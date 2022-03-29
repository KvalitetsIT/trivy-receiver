#!/bin/bash

if docker pull kvalitetsit/trivy-receiver-documentation:latest; then
    echo "Copy from old documentation image."
    docker cp $(docker create kvalitetsit/trivy-receiver-documentation:latest):/usr/share/nginx/html target/old
fi
