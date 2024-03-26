package com.ithirteeng.secondpatternsclientproject.data.theme.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

fun provideFirebaseDatabase(): DatabaseReference {
    return Firebase.database.reference
}