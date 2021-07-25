It's a location tracker app which gets coordinates from GPS module and sends to a remote server. 

Functionality:
*Implement users registration/login via FirebaseAuth (email+password).
*If app can't send location to server (no network or whatever) - save it to db and send later (WorkManager).
*Tracking sensitivity - period each 10 min
*Tracking is restored after reboot and update.

Technology stack: 
Language - Kotlin
Architecture pattern - MVVM
View Binding 
Authorization - Firebase Auth
Remote server - Firebase Cloud Firestore
Database - Room
Multithreading - RxJava2 + RxAndroid2 
DI - Dagger2 
Broadcast Receiver
WorkManager