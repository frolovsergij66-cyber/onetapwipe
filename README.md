# OneTapWipe

A minimal Android app that factory-resets **your own device** with one tap.

It uses the standard Android Device Administration API (`DevicePolicyManager.wipeData()`).
Android does not allow an app to wipe a device without the user manually granting
Device Administrator rights, so the first tap asks you to enable those rights in system
settings. After that, tapping the button shows a confirmation dialog and then performs a
full factory reset.

## What it does

1. First tap → system prompt to enable Device Administrator rights.
2. Next tap → "Are you sure?" dialog.
3. Confirm → factory reset (all data erased, cannot be undone).

## Build on GitHub (no Android Studio needed)

This repo includes a GitHub Actions workflow (`.github/workflows/build.yml`) that
builds the APK in the cloud on every push to `main`.

1. Push the repo to GitHub.
2. Open the **Actions** tab → wait for the "Build APK" run to finish (green check).
3. Open the finished run → **Artifacts** → download `OneTapWipe-debug-apk`.
4. Unzip it to get `app-debug.apk`.

Install the APK on your phone (enable "Install from unknown sources"), open it,
grant Device Administrator rights on first tap.

You can also open the project in **Android Studio** and build locally if you prefer.

## Warnings

- The reset erases **everything**, including your Google account.
- After reset, Factory Reset Protection (FRP) will ask for the Google account that was
  signed in before the wipe — make sure you know those credentials.
- Back up anything you want to keep first.

## Intended use

This is for wiping a device you own (e.g. before selling or giving it away).
It is transparent, open source, and requires an explicit, manual grant of admin rights.

## License

MIT
