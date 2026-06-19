# Luxura - Premium E-Commerce Experience

![Luxura Banner](https://images.unsplash.com/photo-1441986300917-64674bd600d8?q=80&w=2070&auto=format&fit=crop)

Luxura is a modern, high-performance Android e-commerce application designed to provide a premium shopping experience. Built with the latest Android technologies, it offers a seamless journey from product discovery to secure checkout.

## 🚀 Key Features

### 🔐 Authentication & Profile
*   **Google Sign-In**: Quick and secure authentication using Firebase Auth.
*   **User Profiles**: Manage personal information, shipping addresses, and order history.
*   **Theme Support**: Fully responsive Light and Dark mode support.

### 🛍️ Product Discovery
*   **Dynamic Home Screen**: Features premium banners, category-based browsing, and "New Arrivals" sections.
*   **Advanced Search**: Real-time product search with advanced filtering by category and price range.
*   **Interactive Details**: Multi-image product gallery, detailed descriptions, and user rating system.

### 🛒 Shopping Experience
*   **Real-time Cart**: Managed via Firestore for cross-device synchronization.
*   **Smart Wishlist**: Save favorite items for later with one tap.
*   **Seamless Checkout**: Integrated multi-payment support including:
    *   **Razorpay**: UPI, Cards, and Netbanking.
    *   **Stripe**: International card payments.
    *   **COD**: Cash on Delivery support.

### 📦 Post-Purchase
*   **Order Success**: Animated success states with immediate tracking options.
*   **Order Tracking**: Real-time status updates for placed orders.

---

## 🛠️ Tech Stack

*   **Language**: [Kotlin](https://kotlinlang.org/) - 100% Type-safe and modern.
*   **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) - Declarative UI for high performance.
*   **Dependency Injection**: [Hilt](https://dagger.dev/hilt/) - Clean and scalable architecture.
*   **Networking**: [Retrofit](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson).
*   **Backend**: [Firebase](https://firebase.google.com/)
    *   **Authentication**: Secure Google Sign-in.
    *   **Cloud Firestore**: Real-time NoSQL database for products, users, and orders.
*   **Image Loading**: [Coil](https://coil-kt.github.io/coil/) - Optimized image fetching and caching.
*   **Payments**: [Razorpay Android SDK](https://razorpay.com/docs/payments/payment-gateway/android-sdk/) & [Stripe Android SDK](https://stripe.com/docs/mobile/android).
*   **Architecture**: MVVM (Model-View-ViewModel) following Clean Architecture principles.

---

## 🏗️ Architecture

Luxura follows a **Clean Architecture** approach with three distinct layers:

1.  **Data Layer**: Handles API calls, Firestore operations, and local data persistence.
2.  **Domain Layer**: Contains business logic, Use Cases, and Repository interfaces (The heart of the app).
3.  **Presentation Layer**: Jetpack Compose UI components and ViewModels using StateFlow for reactive UI updates.

---

## 🚦 Getting Started

### Prerequisites
*   Android Studio Ladybug or later.
*   JDK 17 or higher.
*   Google Services JSON file (configured in Firebase Console).

### Installation
1.  Clone the repository:
    ```bash
    git clone https://github.com/yourusername/luxura-ecomm.git
    ```
2.  Open the project in Android Studio.
3.  Add your `google-services.json` to the `app/` directory.
4.  Configure your Razorpay and Stripe API keys in the respective configuration files.
5.  Build and Run!

---

## 📱 Screenshots

| Home Screen | Search & Filter | Cart & Checkout |
| :---: | :---: | :---: |
| ![Home](https://via.placeholder.com/300x600?text=Home+Screen) | ![Search](https://via.placeholder.com/300x600?text=Search+Screen) | ![Cart](https://via.placeholder.com/300x600?text=Cart+Screen) |

---

## 🛡️ Reliability
*   **Network Monitoring**: Automatic "No Internet" UI notifications using custom Connectivity Observers.
*   **Performance Tracking**: Firebase Performance Monitoring integrated.
*   **Error Reporting**: Firebase Crashlytics for real-time crash analysis.

---

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

Developed with ❤️ by the Luxura Team.
