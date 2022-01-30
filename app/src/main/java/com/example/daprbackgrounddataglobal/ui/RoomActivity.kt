package com.example.daprbackgrounddataglobal.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.daprbackgrounddataglobal.room.Application
import com.example.daprbackgrounddataglobal.room.database.database
import com.example.daprbackgrounddataglobal.room.student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

public class RoomActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val studentList : List<student> = arrayListOf(
            student(111,"Dalesh",32),
                    student(112,"Sachiy",21)
        )
        val appList : List<Application> = arrayListOf(
            Application(111,1,31111),
            Application(112,2,32222)
        )
        val db = database(this)

        CoroutineScope(Dispatchers.Default).launch{
            db?.detailsDao()?.insertStudent(studentList)
            db?.detailsDao()?.insertStudent(studentList)

val data = db?.detailsDao()?.getById(111)
            val st = db?.detailsDao()?.getApplicationByStudent()

            Log.i("AAA",st.toString())
        }
    }
}