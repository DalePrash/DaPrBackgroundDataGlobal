package com.example.daprbackgrounddataglobal.room

import androidx.room.Embedded
import androidx.room.Relation

data class Student_Data (

    @Embedded
    val student:student,

    @Relation(parentColumn = "student_id",
            entityColumn = "id"
                )
            val application:Application
)