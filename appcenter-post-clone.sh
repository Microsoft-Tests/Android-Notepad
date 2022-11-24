#!/usr/bin/env bash
# fail if any command fails
set -e
# debug log
set -x
echo "source directory is ${APPCENTER_SOURCE_DIRECTORY}..."
echo "app name is ${APP_DISPLAY_NAME}"

COMMIT_HASH=`git rev-parse --short HEAD`
echo "Commit hash is ${COMMIT_HASH}..."

TAG_NUMBER=`git describe --tags --abbrev=0`
echo "Tag is ${TAG_NUMBER}..."

if [[ ! -z "${IS_PROD}" ]] && [[ -z "${TAG_NUMBER}" ]]; then
    echo "Is prod but not tag set.. not intended build. Will fail."
    exit 0
fi

cd ..
git clone  -b beta https://github.com/flutter/flutter.git
export PATH=`pwd`/flutter/bin:$PATH

pushd ./flutter
git checkout 3.3.7
popd

flutter pub get
flutter doctor

echo "Installed flutter to `pwd`/flutter"

if [[ ! -z "${IS_PROD}" ]] && [[ ! -z "${TAG_NUMBER}" ]]; then
    echo "Build production app with version number ${TAG_NUMBER}......."
    cp -r "${APPCENTER_SOURCE_DIRECTORY}/ios/dev/GoogleService-Info-prod.plist" "${APPCENTER_SOURCE_DIRECTORY}/ios/Runner/GoogleService-Info.plist"
    plutil -replace CFBundleDisplayName -string "Enpal" $APPCENTER_SOURCE_DIRECTORY/ios/Runner/Info.plist
    plutil -replace CFBundleName -string "Enpal" $APPCENTER_SOURCE_DIRECTORY/ios/Runner/Info.plist
    plutil -replace CFBundleIdentifier -string "de.enpal.customerApp" $APPCENTER_SOURCE_DIRECTORY/ios/Runner/Info.plist
    plutil -replace CFBundleURLTypes.0.CFBundleURLSchemes -xml '<array><string>de.enpal.customerApp</string></array>' $APPCENTER_SOURCE_DIRECTORY/ios/Runner/Info.plist
    plutil -replace "com\.apple\.developer\.associated-domains" -xml '<array><string>applinks:enpal.page.link</string></array>' $APPCENTER_SOURCE_DIRECTORY/ios/Runner/Runner.entitlements
    flutter build ios --release --no-codesign --build-name ${TAG_NUMBER} --build-number $APPCENTER_BUILD_ID
else
    echo "Build DEV app....."
    flutter build ios --release --no-codesign --build-number $APPCENTER_BUILD_ID
fi
