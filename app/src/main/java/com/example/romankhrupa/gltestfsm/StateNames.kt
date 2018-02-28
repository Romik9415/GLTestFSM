package com.example.romankhrupa.gltestfsm

enum class State(val value:String) {
    AlarmDisarmedAllUnlocked("AlarmDisarmedAllUnlocked"),
    AlarmDisarmedDriverUnlocked("AlarmDisarmedDriverUnlocked"),
    AlarmDisarmedAllLocked("AlarmDisarmedAllLocked"),
    AlarmArmedAllLocked("AlarmArmedAllLocked")
}
