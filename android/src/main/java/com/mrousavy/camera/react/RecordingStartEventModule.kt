package com.mrousavy.camera.react

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.Arguments
import com.facebook.react.modules.core.DeviceEventManagerModule

class RecordingStartEventModule(
  reactContext: ReactApplicationContext
) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String = "RecordingStartEventModule"

  companion object {
    // Static helper, so we don't need to construct RecordingStartEventModule ourselves
    fun emitFirstFrameTimestamp(ctx: Context, firstFrameNs: Long) {
      val reactCtx = ctx as? ReactApplicationContext
        ?: (ctx as? com.facebook.react.uimanager.ThemedReactContext)?.reactApplicationContext
        ?: return // can't emit, no react context

      val payload = Arguments.createMap().apply {
        putDouble("firstFrameNs", firstFrameNs.toDouble())
      }

      reactCtx
        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
        .emit("VisionCamera_FirstFrameEncoded", payload)
    }
  }
}
