package com.example.daprbackgrounddataglobal.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey



@Entity(foreignKeys = [ForeignKey(entity = student::class,parentColumns = ["student_id"],childColumns=["id"])],indices = [Index(value=["id"],unique=true)])
data class Application (val id: Int, @PrimaryKey val application_id: Long, val phone_no:Int)