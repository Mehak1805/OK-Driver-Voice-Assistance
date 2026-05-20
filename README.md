# OK Driver Voice Assistant

OK Driver Voice Assistant is a Kotlin-based Android application designed specifically to provide a hands-free, system-wide voice assistant experience for drivers. It ensures safety on the road by allowing users to interact with their devices entirely through voice commands without needing to look at or touch the screen.

## Features

- **Wake-Word Detection:** Continuously listens for the "OK Driver" or "Hey Driver" wake-words using an efficient offline background process.
- **Persistent Background Service:** Runs reliably in the background to ensure the assistant is always ready when you are driving, handling microphone hardware handoffs smoothly.
- **System-Wide UI Overlay:** Features a non-intrusive UI overlay that provides visual feedback of the assistant's listening and processing states.
- **Text-to-Speech (TTS):** Communicates responses back to the driver audibly using integrated text-to-speech capabilities.
- **Conversational Memory:** Uses a local Room database to store and recall previous interactions, maintaining context during long drives.
- **Auto-Start on Boot:** Automatically initializes when the device boots up, so you don't have to launch the app manually before driving.

## Tech Stack

- **Language:** Kotlin
- **Architecture:** MVVM (Model-View-ViewModel)
- **Local Storage:** Room Database for persistent conversation history and DataStore for secure preferences.
- **Background Tasks:** Android Services and Broadcast Receivers
- **UI:** Jetpack Compose

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/Mehak1805/OK-Driver-Voice-Assistance.git
   ```
2. Open the project in Android Studio.
3. Build and run the project on an Android device or emulator.
4. Ensure you grant the necessary permissions (Microphone, Overlay/Draw over other apps, etc.) when prompted.

## Usage

1. Launch the app and grant the required permissions.
2. The background service will start listening.
3.  Say **"OK Driver"** to activate the assistant.
4. Speak your command, and the assistant will process it and respond audibly.
4. Say **"OK Driver"** to activate the assistant.
5. Speak your command, and the assistant will process it and respond audibly.
