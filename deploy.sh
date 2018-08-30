export DEPLOY_BRANCH="$TRAVIS_BRANCH"
export DEPLOY_COMMIT="$TRAVIS_COMMIT"

SLACK_KEY="xoxp-306235574197-306117945266-425836291329-85d75e247727657fe22d4f9481c4aff2"
SLACK_TEXT="[ \`$DEPLOY_BRANCH\` | \`$DEPLOY_COMMIT\` ] ${TRAVIS_COMMIT_MESSAGE:-none} "
CHANNEL_NAME="development"

curl \
  -F "token=$SLACK_KEY" \
  -F "channels=$CHANNEL_NAME" \
  -F "initial_comment=$SLACK_TEXT" \
  -F "file=@app/build/outputs/apk/app-debug.apk" \
  https://slack.com/api/files.upload
