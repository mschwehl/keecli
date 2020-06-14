export SEMVER_LAST_TAG=$(git describe --abbrev=0 --tags 2>/dev/null)
if [ -z $SEMVER_LAST_TAG ]; then
    >&2 echo "No tags defined"
    SEMVER_LAST_TAG="0.0.1"
fi
