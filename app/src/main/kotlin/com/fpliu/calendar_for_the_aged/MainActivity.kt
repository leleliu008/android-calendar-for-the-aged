package com.fpliu.calendar_for_the_aged

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fpliu.newton.util.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(CalendarActivity::class)
        finish()
    }
}
