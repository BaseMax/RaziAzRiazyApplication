# RaziAzRiazyApplication

**Razi Az Riazy** is an Android application that loads the website [https://raziazriazy.ir](https://raziazriazy.ir) inside a native WebView. It is designed to provide a smooth and secure browsing experience for users, offline handling, and custom PDF handling.

## Features

- 📱 Loads [raziazriazy.ir](https://raziazriazy.ir) using Android WebView
- 🌍 Supports Geolocation APIs with user permission
- ⚠️ Shows a snackbar if there’s no internet connection
- 📄 Opens PDF links using external viewers
- 🔐 Enables WebView debugging in debug mode
- 💾 Supports DOM storage and geolocation database
- 🌐 Mixed content mode support for compatibility

## Requirements

- Android 5.0 (API 21) and above
- Internet access permission

## Build & Run

1. Clone the repo:
   ```bash
   git clone https://github.com/BaseMax/RaziAzRiazyApplication.git
   cd RaziAzRiazyApplication
   ```

2. Open the project in Android Studio

3. Connect your Android device or start an emulator

4. Run the app

## Code Overview

Main components:
- `MainActivity.java`: Loads the WebView and handles permissions, error handling, and navigation.
- `activity_main.xml`: Defines the layout with a CoordinatorLayout and WebView.

## Author

Max Base

GitHub: @BaseMax

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
