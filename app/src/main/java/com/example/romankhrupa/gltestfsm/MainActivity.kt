package com.example.romankhrupa.gltestfsm

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val securitySystem = SecuritySystem(baseContext,{
            newState->
            tAlertDescription.text = newState.value
            if(newState== State.AlarmArmedAllLocked){
                iAlertBulb.setColorFilter(ContextCompat.getColor(this, R.color.green))
            }
            else{
                iAlertBulb.setColorFilter(ContextCompat.getColor(this, R.color.red))
            }
        })

        iAlertBulb.setOnClickListener {
            Toast.makeText(this, "Alert Bulb",Toast.LENGTH_SHORT).show()
        }

        button_lock.setOnClickListener({
            securitySystem.updateState(it.tag.toString())
        })
        button_unlock.setOnClickListener({
            securitySystem.updateState(it.tag.toString())
        })
        button_lock_x2.setOnClickListener({
            securitySystem.updateState(it.tag.toString())
        })
        button_unlock_x2.setOnClickListener({
            securitySystem.updateState(it.tag.toString())
        })
    }

}
