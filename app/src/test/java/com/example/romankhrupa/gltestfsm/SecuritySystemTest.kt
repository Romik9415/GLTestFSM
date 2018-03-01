package com.example.romankhrupa.gltestfsm

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import junit.framework.TestCase.assertTrue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.mockito.Mockito.*
import org.mockito.Mockito.`when`


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)

class SecuritySystemTest{
    private val activity = Robolectric.setupActivity(MainActivity::class.java)!!

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }
    @Test
    fun shouldFileNotBeNull() {
        val file = activity.assets.open("securityData")
        assertTrue(file!=null)
    }
    @Test
    fun fileTextNotEmpty() {
        val file = activity.assets.open("securityData")
        val fileText = file.bufferedReader().use {
            it.readText()
        }
        assertTrue(fileText!="")
    }
    @Test
    fun correctSecurityBehavior() {
        val file = activity.assets.open("securityData")
        val json = file.bufferedReader().use {
            it.readText()
        }
        val gson = GsonBuilder().create()
        val securityBehaviorList = gson.fromJson(json, object : TypeToken<List<SecurityBehavior>>() {}.type) as List<SecurityBehavior>
        Assert.assertEquals(securityBehaviorList.get(0).action,"lock")//first action
        Assert.assertEquals(securityBehaviorList.get(0).states.get(0).start,State.AlarmDisarmedAllUnlocked)// start state
        Assert.assertEquals(securityBehaviorList.get(0).states.get(0).end,State.AlarmDisarmedAllLocked)// end state

        Assert.assertEquals(securityBehaviorList.get(1).action,"lockX2")//second action
        Assert.assertEquals(securityBehaviorList.get(1).states.get(0).start,State.AlarmDisarmedAllUnlocked)// start state
        Assert.assertEquals(securityBehaviorList.get(1).states.get(0).end,State.AlarmArmedAllLocked)// end state

        Assert.assertEquals(securityBehaviorList.get(2).action,"unlock")//third action
        Assert.assertEquals(securityBehaviorList.get(2).states.get(0).start,State.AlarmDisarmedAllUnlocked)// start state
        Assert.assertEquals(securityBehaviorList.get(2).states.get(0).end,State.AlarmDisarmedAllUnlocked)// end state

        Assert.assertEquals(securityBehaviorList.get(3).action,"unlockX2")//fourth action
        Assert.assertEquals(securityBehaviorList.get(3).states.get(0).start,State.AlarmDisarmedAllUnlocked)// start state
        Assert.assertEquals(securityBehaviorList.get(3).states.get(0).end,State.AlarmDisarmedAllUnlocked)// end state
    }
    @Test
    fun shouldBackActiveState() {
        val securitySystem = mock(SecuritySystem::class.java)
        `when`(securitySystem.getActiveState()).thenReturn(State.AlarmDisarmedAllUnlocked)
    }
    @Test
    fun shouldChangeActiveState() {
        val ss = SecuritySystem(activity,{})
        Assert.assertEquals(ss.getActiveState(),State.AlarmDisarmedAllUnlocked)
        ss.updateState("lock")
        Assert.assertEquals(ss.getActiveState(),State.AlarmDisarmedAllLocked)
    }

    @Test
    fun shouldNotChangeActiveState() {
        val ss = SecuritySystem(activity,{})
        Assert.assertEquals(ss.getActiveState(),State.AlarmDisarmedAllUnlocked)
        ss.updateState("")
        Assert.assertEquals(ss.getActiveState(),State.AlarmDisarmedAllUnlocked)
    }
    @Test
    fun shouldBackFourActions() {
        val securitySystem = SecuritySystem(activity,{})
        val list = securitySystem.getSecurityBehaviorList()
        Assert.assertEquals(list.size,4)//size of list for actions
    }

    @Test
    fun shouldNoTDoUseMethods() {
        val ss = mock(SecuritySystem::class.java)
        verify(ss, never()).updateState("lock")
        verify(ss, never()).getSecurityBehaviorList()
    }
}