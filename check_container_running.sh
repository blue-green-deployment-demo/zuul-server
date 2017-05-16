#!/bin/bash

# Author: Erik Kristensen
# Email: erik@erikkristensen.com
# License: MIT
# Nagios Usage: check_nrpe!check_docker_container!_container_id_
# Usage: ./check_docker_container.sh _container_id_
#
# The script checks if a container is running.
#   OK - running
#   WARNING - container is ghosted
#   CRITICAL - container is stopped
#   UNKNOWN - does not exist

CONTAINER=$1

RUNNING=$(docker inspect --format="{{ .State.Running }}" $CONTAINER 2> /dev/null)

if [ $? -eq 1 ]; then
  echo "UNKNOWN - $CONTAINER does not exist."
  docker pull rohitgkk/zuul
fi

if [ "$RUNNING" == "true" ]; then
  echo "$CONTAINER is running"
  docker stop zuul
  docker rm -f zuul
fi

if [ "$RUNNING" == "false" ]; then
  echo "CRITICAL - $CONTAINER is not running."
  docker rm -f zuul
fi
