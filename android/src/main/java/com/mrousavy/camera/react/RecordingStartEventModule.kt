package com.mrousavy.camera.react

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.Arguments
import com.facebook.react.modules.core.DeviceEventManagerModule

class RecordingStartEventModule(
  private val ctx: ReactApplicationContext
) : ReactContextBaseJavaModule(ctx) {

  override fun getName(): String = "RecordingStartEventModule"

  fun sendFirstFrameTimestamp(firstFrameNs: Long) {
    val payload = Arguments.createMap().apply {
      // RN nie ma int64, więc dajemy double
      putDouble("firstFrameNs", firstFrameNs.toDouble())
    }
    ctx
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit("VisionCamera_FirstFrameEncoded", payload)
  }
}
