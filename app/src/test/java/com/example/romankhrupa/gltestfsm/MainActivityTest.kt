package com.example.romankhrupa.gltestfsm

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import android.widget.TextView
import android.widget.Button
import android.widget.ImageView
import org.hamcrest.core.IsEqual.equalTo
import android.widget.LinearLayout
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)

class MainActivityTest{
    @Test
    fun shouldNotBeNull() {
       val mainActivity = Robolectric.buildActivity(MainActivity::class.java)
        assertNotNull(mainActivity)
    }

    @Test
    fun clickingButtonShouldChangeResultsViewText() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)

        val button = activity.findViewById<Button>(R.id.button_lock)
        val alertText = activity.findViewById(R.id.tAlertDescription) as TextView
        button.performClick()
        assertThat(alertText.text.toString(),equalTo("AlarmDisarmedAllLocked"))
    }
    @Test
    fun imageNotNull() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val img = activity.findViewById<ImageView>(R.id.iAlertBulb)
        assertNotNull(img)

    }

    @Test
    @Throws(Exception::class)
    fun shouldHaveDefaultMargin() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val btn = activity.findViewById(R.id.button_lock) as Button
        val bottomMargin = (btn.layoutParams as LinearLayout.LayoutParams).bottomMargin
        assertEquals(8, bottomMargin)
        val topMargin = (btn.layoutParams as LinearLayout.LayoutParams).topMargin
        assertEquals(8, topMargin)
        val rightMargin = (btn.layoutParams as LinearLayout.LayoutParams).rightMargin
        assertEquals(8, rightMargin)
        val leftMargin = (btn.layoutParams as LinearLayout.LayoutParams).leftMargin
        assertEquals(8, leftMargin)
    }

    @Test
    fun testButtonClickShouldShowToast() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val view = activity.findViewById(R.id.iAlertBulb) as ImageView
        view.performClick()
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("Alert Bulb"))
    }
}