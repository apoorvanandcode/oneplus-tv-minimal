# OnePlus 43Y1S package classification (2026-10-18)

Source: `installed_packages.txt` pulled from the TV. 128 packages.
Verified on device: HDMI inputs are `com.mediatek.tvinput/.hdmi.HDMIInputService/HW5` and `/HW6`.
Only `com.heytap.tv.livetv` can display a channel or input. Updates come from `com.google.android.gms`.

## SAFE TO REMOVE

### Batch A: preinstalled third-party apps you did not ask for
| Package | Reason |
|---|---|
| android.autoinstalls.config.OnePlus | Play Auto Install stub that pushes promo apps |
| com.fancode.tv | FanCode, preinstalled promo app |
| com.formulaone.production | F1 TV, preinstalled promo app |
| com.graymatrix.did | ZEE5, preinstalled promo app |
| com.jio.media.stb.ondemand | JioCinema STB, preinstalled promo app |
| com.jio.web.androidtv | JioPages browser, preinstalled promo app |
| com.jiostb.jiogames | JioGames, preinstalled promo app |
| com.lionsgateplay.videoapp | Lionsgate Play, preinstalled promo app |
| com.mubi | MUBI, preinstalled promo app |
| in.startv.hotstar | Hotstar, not in your list |
| com.oneplus.tv.userguide | OnePlus user manual app |
| com.google.android.videos | Google TV / Play Movies, not in your list |
| com.google.android.youtube.tvmusic | YouTube Music, you use Spotify |
| com.google.android.play.games | Play Games, not needed |

### Batch B: OnePlus extras, telemetry and diagnostics
| Package | Reason |
|---|---|
| com.heytap.tv.remotediagnosing | Remote diagnostics and telemetry |
| com.heytap.tv.remotecontrolinspect | Remote control test tool |
| com.heytap.oprcupgradeservice | Remote control firmware updater |
| com.heytap.tv.assistant.oneplus | OnePlus TV Manager (cleaner) |
| com.heytap.tv.btspeaker | Bluetooth speaker mode |
| com.oplus.onet | OnePlus phone synergy service |
| oneplus.smarthome | OnePlus smart home service |
| com.oneplus.tv.android.account | OnePlus account |
| com.heytap.olink | OnePlus Connect link service (17 MB RAM) |
| com.heytap.optvfactory | Factory menu UI |
| mediatek.factorymenu.ui | MediaTek factory menu UI |
| com.mediatek.androidbox | Miracast sink test app |
| com.oneplus.tv.screensaver | OnePlus screensaver |
| com.google.android.backdrop | Ambient mode screensaver |

### Batch C: Android extras you do not use
| Package | Reason |
|---|---|
| com.google.android.marvin.talkback | Screen reader |
| com.google.android.tts | Text to speech, only used by TalkBack |
| com.google.android.feedback | Sends feedback reports to Google |
| com.android.printspooler | Printing |
| com.android.dynsystem | Dynamic system images, developer tool |
| com.google.android.syncadapters.calendar | Calendar sync |
| com.google.android.onetimeinitializer | First boot helper, already ran |
| com.android.settings.intelligence | Settings search suggestions |
| com.android.wallpaperbackup | Wallpaper backup |
| com.android.htmlviewer | Shows license pages |

### Batch D: only after the custom launcher works
| Package | Reason |
|---|---|
| com.google.android.katniss | Assistant and voice search (42 MB RAM). Test phone typing after |
| com.google.android.tvrecommendations | Home screen recommendation rows (21 MB RAM) |
| com.mediatek.android.leanbacklauncher.partnercustomizer | Pins promo apps on the stock home |
| com.google.android.tvlauncher | Stock launcher (137 MB RAM). Disable last, once ours is home |

## RISKY (keep unless you decide otherwise)
| Package | Reason |
|---|---|
| com.neoyantra.airplaymirror.airplaymirrorappdemo | AirPlay receiver demo. Only way to mirror an iPhone screen. Ask user |
| com.heytap.optvmiracastservice | Miracast receiver for Android screen mirroring. Ask user |
| com.oneplus.tv.remote | OnePlus remote service, may handle physical remote keys |
| com.oneplus.tv.launcher | OnePlus launcher service, runs in background, may own the source panel |
| com.oneplus.tv.factory | Factory service, may apply panel calibration at boot |
| com.oneplus.oobe.tv | First setup wizard, removal can cause setup loops |
| com.google.android.tungsten.setupwraith | Android TV setup, used when adding Google accounts |
| com.google.android.sss.authbridge | Sign-in bridge for setup and accounts |
| com.mediatek.wwtv.webview | Vendor WebView, may be used by Live TV and settings |
| com.android.cts.ctsshim | Test shim, inert. No gain from removing |
| com.android.cts.priv.ctsshim | Test shim, inert. No gain from removing |

## KEEP
| Package | Reason |
|---|---|
| com.netflix.ninja | Netflix |
| com.spotify.tv.android | Spotify |
| com.sonyliv | Sony LIV |
| com.google.android.youtube.tv | YouTube |
| com.amazon.amazonvideo.livingroom | Prime Video |
| com.android.vending | Play Store |
| com.google.android.gms | Play Services |
| com.google.android.gsf | Google Services Framework |
| com.google.android.partnersetup | Play Services dependency |
| com.google.android.apps.mediashell | Chromecast built-in |
| com.google.android.tv.remote.service | Google TV app remote and keyboard |
| com.google.android.inputmethod.latin | Gboard, the system keyboard |
| com.google.android.webview | WebView for apps and sign-in pages |
| com.google.android.ext.services | Core Android services |
| com.google.android.ext.shared | Core Android library |
| com.google.android.packageinstaller | Installs apps |
| com.google.android.permissioncontroller | Permissions |
| com.google.android.modulemetadata | Mainline module info |
| com.google.android.tv.frameworkpackagestubs | Stubs that stop app crashes |
| com.heytap.tv.livetv | Displays HDMI inputs (only VIEW handler for channels) |
| com.mediatek.tvinput | HDMI, tuner and AV input services |
| com.mediatek.tv.service | TV hardware service |
| com.heytap.tvapiservice | Picture and sound settings bridge |
| com.android.providers.tv | TV input and channel database |
| com.dolby.android.audio.service | Dolby audio |
| com.heytap.tv.filebrowser | USB media playback |
| com.android.externalstorage | USB storage |
| com.android.providers.media | Media database |
| com.android.providers.media.module | Media database |
| com.android.providers.downloads | Downloads for Play Store |
| com.android.tv.settings | Settings app, also picture and sound |
| com.android.systemui | System UI |
| com.android.bluetooth | Bluetooth remote, audio, controllers |
| com.android.companiondevicemanager | Device pairing |
| com.android.inputdevices | Keyboard and controller layouts |
| com.mediatek.network | Network monitor |
| com.android.networkstack.inprocess | Wi-Fi and network |
| com.android.networkstack.permissionconfig | Network permissions |
| com.android.networkstack.tethering.inprocess | Network stack |
| com.android.wifi.resources | Wi-Fi |
| com.android.hotspot2.osulogin | Wi-Fi hotspot login |
| com.android.captiveportallogin | Wi-Fi login pages |
| com.android.pacprocessor | Proxy support |
| com.android.proxyhandler | Proxy support |
| com.android.vpndialogs | VPN prompts |
| com.android.certinstaller | Certificates |
| com.android.keychain | Certificates |
| com.android.se | Secure element, DRM |
| com.android.location.fused | Location for Play Services |
| com.android.providers.settings | Settings storage |
| com.android.providers.contacts | Account data |
| com.android.providers.calendar | Play Services dependency |
| com.android.providers.userdictionary | Keyboard dictionary |
| com.android.statementservice | App link verification |
| com.android.soundpicker | System sounds |
| com.android.dreams.basic | Basic screensaver fallback |
| com.android.backupconfirm | Backup prompts |
| com.android.localtransport | Backup transport |
| com.android.sharedstoragebackup | Backup |
| com.android.shell | ADB shell, needed for this work |
| android | Android framework |
| All `*.rro*`, `*overlay*` and `auto_generated_rro` packages (16) | Hardware and config overlays |
