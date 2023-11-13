package com.example.inkAlankar

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class DataSource(private var reference: DatabaseReference) {
    var flag = false
    fun insertDataIntoDatabase(map: Map<String, String>): Boolean {
//        if (!checkExistingDataInDatabase(map)) {
//
//            return false
//        }
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


}