# SchoolSafe - CBTis 122 Evacuation Guide

SchoolSafe is a specialized Android application designed for **CBTis 122** to assist students, staff, and visitors during emergency evacuations. The app provides real-time location tracking on a school map and interactive video guides for specific evacuation routes based on the user's starting building.

## 🚀 Key Features

*   **Interactive Campus Map**: Built with Google Maps Compose, featuring a satellite view and markers for all designated Assembly Points (Puntos de Reunión).
*   **Dynamic Route Selection**: Users can select their current building from a categorized list (Canchas, Bicéfalo, Caseta).
*   **Guided Video Evacuation**: High-quality video walkthroughs (Media3 ExoPlayer) showing the exact path from a selected building to the safe zone.
*   **Timeline of Critical Points**: A visual breakdown of each route, highlighting emergency exits, main hallways, and final assembly locations.
*   **Emergency SOS**: Quick-access button to dial 911 immediately.
*   **Protocol & Contacts**: Dedicated sections for safety protocols and important school emergency contacts.
*   **Dark Mode Support**: Fully themed for better visibility in different lighting conditions.

## 🛠 Tech Stack

*   **Language**: Kotlin
*   **UI Framework**: Jetpack Compose (Material 3)
*   **Maps**: Google Maps SDK for Android (Compose library)
*   **Media**: Media3 ExoPlayer & PlayerView
*   **Permissions**: Accompanist Permissions for location access.
*   **State Management**: Kotlin Coroutines & Flow
*   **Storage**: DataStore Preferences (for theme management)

## 📁 Project Structure

```text
app/src/main/java/com/example/cbtisapp/
├── MainActivity.kt          # Main navigation and Map entry point
├── RoutePlayerScreen.kt     # Video player and evacuation timeline
├── SelectionScreen.kt       # Building selection interface
├── RutaRepository.kt        # Logic for mapping buildings to resources
└── ThemeManager.kt          # Dark/Light mode logic
```

## 🏗 Setup & Installation

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/your-repo/cbtisapp.git
    ```
2.  **Google Maps API Key**:
    *   Create a project in the [Google Cloud Console](https://console.cloud.google.com/).
    *   Enable **Maps SDK for Android**.
    *   Add your API key to `local.properties` or your manifest:
        ```xml
        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="YOUR_API_KEY_HERE" />
        ```
3.  **Build**: Open the project in **Android Studio Ladybug** (or newer) and sync with Gradle.
4.  **Run**: Deploy to an Android device with API 24 (Nougat) or higher.

## 🛡 Safety Information

This application is a support tool. Always prioritize instructions from civil protection brigadiers and school authorities during a real emergency.

---
*Developed for the CBTis 122 Community.*
