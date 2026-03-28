#!/bin/bash

# AWS SSM Port Forwarding Script for RDS Access
# Usage: ./scripts/connect-rds.sh <instance-id> [local-port] [remote-host] [remote-port]

set -e

# Check if AWS CLI is installed
if ! command -v aws &> /dev/null; then
    echo "Error: AWS CLI is not installed. Please install it first."
    exit 1
fi

# Check if instance ID is provided
if [ -z "$1" ]; then
    echo "Usage: $0 <instance-id> [local-port] [remote-host] [remote-port]"
    echo ""
    echo "Example:"
    echo "  $0 i-1234567890abcdef0"
    echo "  $0 i-1234567890abcdef0 3306 my-rds.abc.ap-northeast-2.rds.amazonaws.com 3306"
    exit 1
fi

INSTANCE_ID=$1
LOCAL_PORT=${2:-3306}
REMOTE_HOST=${3:-""}
REMOTE_PORT=${4:-3306}

# If remote host is not provided, try to get it from .env file
if [ -z "$REMOTE_HOST" ]; then
    if [ -f ".env" ]; then
        source .env
        REMOTE_HOST=$DB_HOST
    fi
fi

# If still no remote host, ask user
if [ -z "$REMOTE_HOST" ] || [ "$REMOTE_HOST" = "localhost" ]; then
    echo "Error: Remote host not specified."
    echo "Please provide RDS endpoint as the third parameter or set DB_HOST in .env file."
    echo ""
    echo "Usage: $0 $INSTANCE_ID $LOCAL_PORT <rds-endpoint> $REMOTE_PORT"
    exit 1
fi

echo "Starting SSM port forwarding..."
echo "Instance ID: $INSTANCE_ID"
echo "Local Port: $LOCAL_PORT"
echo "Remote Host: $REMOTE_HOST"
echo "Remote Port: $REMOTE_PORT"
echo ""

# Check if port is already in use
if lsof -Pi :$LOCAL_PORT -sTCP:LISTEN -t >/dev/null 2>&1 ; then
    echo "Warning: Port $LOCAL_PORT is already in use."
    echo "Existing process:"
    lsof -Pi :$LOCAL_PORT -sTCP:LISTEN
    echo ""
    read -p "Do you want to kill the existing process? (y/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        kill $(lsof -t -i:$LOCAL_PORT) || true
        sleep 2
    else
        exit 1
    fi
fi

# Start SSM session with port forwarding
echo "Connecting to $REMOTE_HOST:$REMOTE_PORT via instance $INSTANCE_ID..."
echo "Port forwarding: localhost:$LOCAL_PORT -> $REMOTE_HOST:$REMOTE_PORT"
echo ""
echo "Press Ctrl+C to stop port forwarding"
echo ""

aws ssm start-session \
    --target "$INSTANCE_ID" \
    --document-name AWS-StartPortForwardingSessionToRemoteHost \
    --parameters "{\"host\":[\"$REMOTE_HOST\"],\"portNumber\":[\"$REMOTE_PORT\"],\"localPortNumber\":[\"$LOCAL_PORT\"]}"
