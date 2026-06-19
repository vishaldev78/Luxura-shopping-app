package com.shops.ecomm.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.shops.ecomm.domain.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val usersCollection = firestore.collection("users")

    suspend fun saveUser(user: User) {
        usersCollection.document(user.id).set(user).await()
    }

    suspend fun getUser(userId: String): User? {
        return usersCollection.document(userId).get().await().toObject(User::class.java)
    }

    suspend fun updateAddress(userId: String, address: String) {
        usersCollection.document(userId).update("address", address).await()
    }
}
