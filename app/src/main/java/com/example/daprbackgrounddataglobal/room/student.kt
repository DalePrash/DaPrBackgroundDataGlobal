package com.example.daprbackgrounddataglobal.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class student (@PrimaryKey val student_id:Int, val name:String, val age:Int)