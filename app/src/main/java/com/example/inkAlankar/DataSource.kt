package com.example.inkAlankar

import android.graphics.Bitmap
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

class DataSource(private var reference: DatabaseReference) {
    fun insertDataIntoDatabase(map: Map<String, String>): Boolean {
        reference.child(map.getValue("email")).setValue(map)
        return true
    }

    fun doesDataExist(
        databaseReference: DatabaseReference,
        email: String,
        password: String,
        callback: (Boolean) -> Unit
    ) {
        databaseReference.child(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    callback.invoke(false)
                } else {
                    val storedPassword = dataSnapshot.child("password").value as String
                    if (storedPassword == password) {
                        callback.invoke(true)
                    } else {
                        callback.invoke(false)
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback.invoke(false) // Return false in case of error
            }
        })
    }

    fun getLoginDataFromDatabase(path: String, callback: (Map<String, Any>) -> Unit) {
        val map: MutableMap<String, Any> = mutableMapOf()
        reference.child(path).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {

                        val key = childSnapshot.key
                        val value = childSnapshot.value
                        if (key != null && value != null) {
                            map[key] = value
                        }
                        callback.invoke(map)
                    }

                } else {
                    callback.invoke(map)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun uploadBitmapToFirebaseStorage(bitmap: Bitmap, imageIndex: String) {
        val storageReference = Firebase.storage.reference
        val currentTimeMillis = System.currentTimeMillis()
        val storageRef = storageReference.child("$imageIndex/$currentTimeMillis.png")

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = storageRef.putBytes(data)
        uploadTask.addOnSuccessListener {
            // Image uploaded successfully
        }.addOnFailureListener {
            // Handle unsuccessful uploads
        }

    }



}