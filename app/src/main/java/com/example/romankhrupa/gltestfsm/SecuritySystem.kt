package com.example.romankhrupa.gltestfsm

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class SecuritySystem(mContext:Context,val listener:(State)->Unit) {
    private val securityBehaviorList = getSecurityBehavior(mContext) // behavior from json
    private var activeState = State.AlarmDisarmedAllUnlocked

    fun updateState(action: String){
        setNewState(action,securityBehaviorList)
        listener.invoke(activeState)
    }

    private fun setNewState(action: String,securityBehavioList: List<SecurityBehavior>){
        for(sb in securityBehaviorList){
            if(action==sb.action){
                for(sc in sb.states){
                    if(sc.start==activeState){
                        activeState = sc.end
                        break
                    }
                }
                break
            }
        }
    }
}

data class StateChange(var start: State ,var end: State)
data class SecurityBehavior(var action: String,var states: List<StateChange>)


fun getSecurityBehavior(mContext: Context):List<SecurityBehavior>{
    val gson = GsonBuilder().create()
    val json = getSecurityDataFromAssets(mContext)
    return gson.fromJson(json, object : TypeToken<List<SecurityBehavior>>() {}.type) as List<SecurityBehavior>

}
fun getSecurityDataFromAssets( mContext:Context):String{
    val fileName = "securityData"
    return mContext.assets.open(fileName).bufferedReader().use {
        it.readText()
    }

}