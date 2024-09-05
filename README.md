# Swol Workout Tracker
Swol is a workout tracker app built using Kotlin and the Android SDK, designed to help users track their workouts, monitor muscle volume progress, and take progress photos. The app uses a **Model-View-Controller (MVC) architecture** and integrates with the **wger API** to classify exercises by muscle group.

## Features
* **Track Workouts**: Input exercises along with sets and reps to track your workout sessions.
* **Muscle Volume Analysi**s: Automatically gather muscle group data using the wger API and calculate exercise volume based on sets and reps.
* **Progress Over Time**: View your workout progress over a selected period, including volume tracked by muscle groups.
* **Camera Functionality**: Take progress pictures to visually document your transformation over time. All images are stored locally on your device.
* **Local Data Storage**: Data is stored using Room, ensuring persistence across app restarts.
* **Modern UI**: Styled with AndroidX for a clean and responsive interface.

## Tech Stack

* **Language**: Kotlin
* **Architecture**: MVC (Model-View-Controller)
* **Local Storage**: Room (SQLite)
* **Data Binding**: ViewBinding
* **Network Requests**: Retrofit and Moshi
* **UI Components**: AndroidX, Material Design
* **Image Loading**: Glide
* **Charts**: WilliamChart (for displaying workout volume)

## Installation

1. Clone Repository
```
git clone https://github.com/your-username/swol-workout-tracker.git
```
2. Open the project in Android Studio
    * Select Open an existing project.
    * Navigate to the location where you cloned the repository and select the project folder.
3. Sync Gradle
    * Android Studio should automatically prompt you to sync the Gradle files.
    * If the sync does not start automatically, go to the menu and select **File > Sync Project with Gradle Files**.
4. Build the project
    * Once the Gradle sync is complete, click on the **Build > Make Project** option to build the project.
5. Run the app:
    * Connect an Android device or start an emulator.
    * Click the Run button or select **Run > Run 'app'** from the menu.

## Dependencies
* **Room**: Local database for storing exercise and volume data.
* **Retrofit + Moshi**: Handling network requests and parsing JSON data from the wger API.
* **AndroidX**: Lifecycle components, ViewModel, LiveData, and UI.
* **Glide**: Loading and caching images for progress pictures.
* **WilliamChart**: For visualizing workout progress and muscle volume data.

## Images
![Main Screen](https://github.com/bharath-temp/Swol/blob/main/images/Android%20Large%20-%20Main%20Screen.png)

## License
This project is licensed under the MIT License.

