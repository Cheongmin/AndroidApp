#!/usr/bin/env bash
export DEPLOY_BRANCH="$TRAVIS_BRANCH"
export DEPLOY_COMMIT="$TRAVIS_COMMIT"

SLACK_TEXT="[ \`$DEPLOY_BRANCH\` | \`$DEPLOY_COMMIT\` ] ${TRAVIS_COMMIT_MESSAGE:-none} "
CHANNEL_NAME="development"

ls app/build/outputs/apk/debug/

curl \
  -F "token=$SLACK_KEY" \
  -F "channels=$CHANNEL_NAME" \
  -F "initial_comment=$SLACK_TEXT" \
  -F "file=@app/build/outputs/apk/debug/app-debug.apk" \
  https://slack.com/api/files.upload
