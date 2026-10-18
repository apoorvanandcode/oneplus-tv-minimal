#!/bin/zsh
# Builds build/MinimalLauncher.apk without Gradle: aapt2, javac, d8, zipalign, apksigner.
set -e
cd "${0:A:h}"

SDK=/opt/homebrew/share/android-commandlinetools
BT=$SDK/build-tools/30.0.3
JAR=$SDK/platforms/android-30/android.jar
export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH

rm -rf build
mkdir -p build/classes build/dex

$BT/aapt2 link -I $JAR --manifest AndroidManifest.xml -o build/unsigned.apk
javac -source 8 -target 8 -bootclasspath $JAR -Xlint:-options -d build/classes $(find src -name '*.java')
$BT/d8 --min-api 30 --lib $JAR --output build/dex $(find build/classes -name '*.class')
(cd build/dex && zip -q ../unsigned.apk classes.dex)
$BT/zipalign -f 4 build/unsigned.apk build/aligned.apk

# Keep this keystore: updates to the installed launcher must be signed with the same key.
if [[ ! -f release.keystore ]]; then
  keytool -genkeypair -keystore release.keystore -alias launcher -keyalg RSA -keysize 2048 \
    -validity 10000 -storepass launcher -keypass launcher -dname "CN=Minimal Launcher"
fi
$BT/apksigner sign --ks release.keystore --ks-pass pass:launcher --out build/MinimalLauncher.apk build/aligned.apk
ls -l build/MinimalLauncher.apk
