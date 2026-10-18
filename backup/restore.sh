#!/bin/zsh
# Puts the TV back to the state captured in this backup folder.
# Reinstalls every package that was present at backup time, re-enables anything
# disabled, and sets the stock Google TV launcher as home again.
# Usage: ./restore.sh   (TV must be connected: adb connect <TV_IP>:5555)

cd "${0:A:h}" || exit 1
adb get-state >/dev/null || { echo "TV not connected"; exit 1; }

current=$(adb shell pm list packages | sed 's/^package://' | tr -d '\r')
disabled=$(adb shell pm list packages -d | sed 's/^package://' | tr -d '\r')

sed 's/.*=//' all_packages_incl_uninstalled.txt | tr -d '\r' | while read -r pkg; do
  if ! print -r -- "$current" | grep -qx "$pkg"; then
    echo "Restoring $pkg"
    # install-existing can report success without restoring /data apps on this
    # firmware, so confirm with pm path and fall back to the backed-up APKs.
    adb shell cmd package install-existing "$pkg" </dev/null >/dev/null
    if [[ -z "$(adb shell pm path "$pkg" </dev/null)" ]]; then
      if [[ -d "apks/$pkg" ]]; then
        adb install-multiple -r apks/$pkg/*.apk </dev/null
      else
        echo "  could not restore $pkg (no APK backup)"
      fi
    fi
  fi
  if print -r -- "$disabled" | grep -qx "$pkg"; then
    echo "Re-enabling $pkg"
    adb shell pm enable "$pkg" </dev/null
  fi
done

adb shell cmd package set-home-activity com.google.android.tvlauncher/.MainActivity
echo "Done. Reboot the TV with: adb reboot"
