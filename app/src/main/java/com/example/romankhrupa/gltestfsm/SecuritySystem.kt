package com.example.romankhrupa.gltestfsm

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class SecuritySystem(mContext:Context, private val stateListener:(State)->Unit) {
    private val securityBehaviorList = getSecurityBehavior(mContext) // behavior from json
    private var activeState = State.AlarmDisarmedAllUnlocked //Initial state is AlarmDisarmed_AllUnlocked

    fun updateState(action: String){
        setNewState(action)
        stateListener.invoke(activeState)
    }

    private fun setNewState(action: String){
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

    fun getActiveState():State{
        return activeState
    }
    fun getSecurityBehaviorList():List<SecurityBehavior>{
        return securityBehaviorList
    }
}

data class StateTransition(var start: State, var end: State)
data class SecurityBehavior(var action: String,var states: List<StateTransition>)

private fun getSecurityBehavior(mContext: Context):List<SecurityBehavior>{
    val gson = GsonBuilder().create()
    val json = getSecurityDataFromAssets(mContext)
    return gson.fromJson(json, object : TypeToken<List<SecurityBehavior>>() {}.type) as List<SecurityBehavior>
}
private fun getSecurityDataFromAssets( mContext:Context):String{
    val fileName = "securityData"
    return mContext.assets.open(fileName).bufferedReader().use {
        it.readText()
    }

}