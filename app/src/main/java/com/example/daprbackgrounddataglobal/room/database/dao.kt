package com.example.daprbackgrounddataglobal.room.database

import androidx.room.*
import com.example.daprbackgrounddataglobal.room.Student_Data
import com.example.daprbackgrounddataglobal.room.student


@Dao
interface dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(st: List<student>)

    @Query("SELECT * FROM student WHERE student_id = :student_id")
    @Transaction
   suspend fun getById(student_id: Int):List<Student_Data>

@Query("SELECT * FROM student")
  suspend fun  getApplicationByStudent():Student_Data
}