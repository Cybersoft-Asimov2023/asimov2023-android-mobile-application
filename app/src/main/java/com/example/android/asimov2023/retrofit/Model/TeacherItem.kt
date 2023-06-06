package com.example.android.asimov2023.retrofit.Model

data class TeacherItem(
    var id: Int,
    val age: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val password: String,
    val phone: String,
    val point: Int,
    val roles: List<String>,
    val token: String
)