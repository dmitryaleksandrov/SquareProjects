package com.example.squareprojects

import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector

const val RETRY_INTERVAL = 500L // ms

const val DEFAULT_TIMEOUT = 10_000L // ms

val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

fun waitForText(viewResId: Int, textResId: Int, timeoutMs: Long = DEFAULT_TIMEOUT): Boolean {
    val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    val resourceName = targetContext.resources.getResourceEntryName(viewResId)
    val text = targetContext.getString(textResId)

    return withTimeout(timeoutMs) {
        uiDevice.findObject(By.res(targetContext.packageName, resourceName))?.text == text
    }
}

fun waitForTextInRecyclerView(text: String, timeoutMs: Long = DEFAULT_TIMEOUT): Boolean {
    val listView = UiScrollable(UiSelector().className(RecyclerView::class.java))
    return withTimeout(timeoutMs) {
        try {
            listView.getChildByText(UiSelector().className(TextView::class.java), text, true)
            true
        } catch (t: Throwable) {
            false
        }
    }
}

fun withTimeout(timeoutMs: Long, block: () -> Boolean): Boolean {
    val timestamp = System.currentTimeMillis() + timeoutMs

    var result = block()
    while (System.currentTimeMillis() < timestamp && !result) {
        Thread.sleep(RETRY_INTERVAL)
        result = block()
    }

    return result
}
