# 🛍️ Luxura - Premium E-Commerce Android App

<p align="center">

<img src="https://images.unsplash.com/photo-1441986300917-64674bd600d8?q=80&w=2070&auto=format&fit=crop" width="100%" />

</p>


<p align="center">
A modern premium E-Commerce Android application built with Kotlin, Jetpack Compose, Firebase and Clean Architecture.
</p>


<p align="center">

<a href="https://github.com/vishaldev78/Luxura-shopping-app/releases">
<img src="https://img.shields.io/github/v/release/vishaldev78/Luxura-shopping-app?style=for-the-badge"/>
</a>

<img src="https://img.shields.io/badge/Kotlin-100%25-purple?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Jetpack%20Compose-UI-blue?style=for-the-badge"/>

</p>


## 📱 Download App

Latest APK:

👉 [Download Luxura APK](https://github.com/vishaldev78/Luxura-shopping-app/releases/latest)


---

# ✨ Features


## 🔐 Authentication

- Firebase Authentication
- Google Sign-In
- Secure user profile management
- Light / Dark theme support


## 🛒 Shopping

- Product browsing
- Category filtering
- Smart search
- Product details
- Wishlist
- Real-time cart


## 💳 Payments

Supported payment options:

- Razorpay
  - UPI
  - Cards
  - Net Banking

- Stripe
  - International payments

- Cash on Delivery


## 📦 Orders

- Order placement
- Order history
- Order tracking
- Success animations


---

# 🖼️ Screenshots


## Home Screen

<img src="screenshots/home.png" width="250"/>


## Product Details

<img src="screenshots/details.png" width="250"/>


## Cart

<img src="screenshots/cart.png" width="250"/>


## Checkout

<img src="screenshots/checkout.png" width="250"/>



> Add your screenshots inside `/screenshots` folder.


---

# 🛠️ Tech Stack


## Android

- Kotlin
- Android SDK
- Jetpack Compose
- Material 3


## Architecture

- MVVM
- Clean Architecture
- Repository Pattern


## Dependency Injection

- Hilt


## Backend

- Firebase Authentication
- Cloud Firestore
- Firebase Storage
- Firebase Crashlytics
- Firebase Performance


## Networking

- Retrofit
- OkHttp
- Gson


## Database

- Firestore
- Room Database
- DataStore


## Image Loading

- Coil


## Async

- Kotlin Coroutines
- Flow
- StateFlow


## API

- Platzi Fake Store API


---

# 🏗️ Architecture


```
Presentation Layer
        |
        |
Domain Layer
        |
        |
Data Layer
        |
        |
Firebase / API / Database
```


---

# 🚀 Installation


### Requirements

- Android Studio Ladybug+
- JDK 17+
- Firebase Project


Clone repository:

```bash
git clone https://github.com/vishaldev78/Luxura-shopping-app.git
```


Open project:

```
Android Studio
      ↓
Sync Gradle
      ↓
Run App
```


Add:

```
google-services.json
```

inside:

```
app/
```


---

# 📂 Project Structure


```
app

├── data
│   ├── api
│   ├── repository
│
├── domain
│   ├── model
│   ├── usecase
│
├── presentation
│   ├── screens
│   ├── components
│   └── viewmodel
```


---

# 🔒 Security

- Secure Firebase rules
- Protected API handling
- Crash monitoring
- Network state monitoring


---

# 📈 Future Improvements

- Push Notifications
- Coupon System
- Seller Dashboard
- AI Product Recommendation
- Delivery Tracking


---

# 👨‍💻 Developer

**Vishal**

Android Developer  
Kotlin | Jetpack Compose | Firebase


---

# 📄 License

MIT License


---

⭐ If you like this project, consider giving it a star.
