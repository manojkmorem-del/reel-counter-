package com.example.reelcounter

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class ReelCounterService : AccessibilityService() {

    private var count = 0
    private var lastScrollTime = 0L

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val currentTime = System.currentTimeMillis()
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
            val root = rootInActiveWindow ?: return
            val hasReelMarkers = root.findAccessibilityNodeInfosByText("Audio").isNotEmpty() ||
                                 root.findAccessibilityNodeInfosByText("Remix").isNotEmpty()

            if (hasReelMarkers && (currentTime - lastScrollTime > 1200L)) {
                lastScrollTime = currentTime
                count++
                Toast.makeText(applicationContext, "Reel #$count", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onInterrupt() {}
}
