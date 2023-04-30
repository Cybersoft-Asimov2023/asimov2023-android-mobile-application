package com.example.android.asimov2023.retrofit.Model

data class DirectorItem (
    var id: Int,
    var first_name: String,
    var last_name: String,
    var age: Int,
    var email: String,
    var password: String,
    var phone: String,
    var roles: List<String>,
    val token: String
)