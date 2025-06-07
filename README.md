# Lottie Animation Swiper – Android Kotlin📽️
This Android project demonstrates a smooth, interactive swipe-based navigation using Lottie animations. Users can swipe left or right to transition through a sequence of screens, with each transition consisting of both static screen animations and in-between transition animations — including support for reverse (backward) animations.

## Features✨
🚶‍♂️ Swipe gesture support (left/right) to move between screens.

🎞️ Forward and reverse animation playback using Lottie.

🔁 Smooth handling of transitions by stitching static + in-between animations.

🔘 Dynamic page indicators (dots) and screen counter UI.

💡 Handles edge cases and animation loops gracefully.

## Screenshots / Demo📱
(Add demo GIFs or screenshots here if possible)

## How It Works🧩
The animation flow is handled by alternating between:

* Static screen animations (e.g. screen_1s, screen_2s, etc.)

* Transition animations between screens (e.g. screen_1_2_transitions, screen_2_3_transitions, etc.)

* Reverse animations for backward swipes (e.g. screen_2_1_transitions, screen_3_2_transitions, etc.)

## Animation Flow📜
The app uses two animation sequences:

- animations[] for forward swipe (includes screens and forward transitions)

- revAnimation[] for reverse swipe (includes screens and reverse transitions)

Each swipe triggers:

#### Transition Animation (e.g. screen_1_2_transitions)

#### Target Screen Animation (e.g. screen_2s) — looped if it's the last screen

## Gesture Detection🧠
Custom gesture detection is implemented using:

       OnSwipeTouchListener
This detects:

       onSwipeLeft() → Forward screen

       onSwipeRight() → Previous screen

(Top/Bottom gestures are available to extend)

Gesture detection is hooked into the main screen container with:

       trainingScreenLayout.setOnTouchListener(OnSwipeTouchListener)

## Edge Handling🧪
* Prevents index underflow and overflow when swiping beyond start or end.

* Fades text in/out during transitions.

* Proper handling of LottieDrawable.INFINITE to loop last animation.

* Adds AnimatorListener to repeat animation only when necessary.

## Project Structure📦
#### MainActivity.kt
Core logic for animation control, gesture response, and UI updates.

#### OnSwipeTouchListener.java
A reusable class for gesture detection.

## Dependencies🛠
 [Lottie for Android](https://github.com/airbnb/lottie-android)

#### implementation 'com.airbnb.android:lottie:6.1.0' // or latest

## Getting Started🚀
* Clone the repo:

       git clone https://github.com/your-username/animation-lottie-kotlin.git
* Open in Android Studio.

* Place your Lottie JSON animations in the res/raw directory.

* Run the app on an emulator or physical device.

## Customization Tips🔧
* Add more screens and transitions by updating the animations[] and revAnimation[] arrays.

* Extend gesture listeners to include up/down swipes for advanced interactions.

* Customize the dots and counter UI in addBottomDots().

## References📚

 [Lottie Animation Documentation](https://airbnb.io/lottie/#/)

[GestureDetector Android Docs](https://developer.android.com/reference/android/view/GestureDetector)

## Author🧑‍💻
### Himanshu HC👤
 [LinkedIn🔗](https://www.linkedin.com/in/himanshuhc/)
