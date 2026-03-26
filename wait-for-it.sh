#!/bin/bash
set -e
host="$1"
shift
port="$1"
shift
cmd="$@"
echo "Waiting for $host:$port..."
timeout 60 bash -c "until nc -z $host $port; do sleep 1; done"
echo "$host:$port is available - executing command"
exec $cmd