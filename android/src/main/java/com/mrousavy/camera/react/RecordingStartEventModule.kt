package com.mrousavy.camera.react

import android.content.Context
import android.util.Log
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.uimanager.ThemedReactContext

class RecordingStartEventModule(
  reactContext: ReactApplicationContext
) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String = "RecordingStartEventModule"

  // RN wymaga tych metod jeśli używamy NativeEventEmitter po stronie JS
  @ReactMethod
  fun addListener(eventName: String) {
    // no-op
  }

  @ReactMethod
  fun removeListeners(count: Int) {
    // no-op
  }

  companion object {
    fun emitFirstFrameTimestamp(ctx: Context, firstFrameNs: Long) {
      // spróbuj zdobyć ReactApplicationContext z kontekstu widoku kamery
      val reactCtx: ReactApplicationContext? = when (ctx) {
        is ReactApplicationContext -> ctx
        is ThemedReactContext -> ctx.reactApplicationContext
        else -> null
      }

      if (reactCtx == null) {
        Log.w(
          "RecordingStartEventModule",
          "Cannot emit first frame timestamp: no ReactApplicationContext (ctx=${ctx.javaClass.name})"
        )
        return
      }

      // payload jaki dostanie JS przez NativeEventEmitter
      val payload = Arguments.createMap().apply {
        // RN nie ma int64, więc lecimy w double
        putDouble("firstFrameNs", firstFrameNs.toDouble())
      }

      reactCtx
        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
        .emit("VisionCamera_FirstFrameEncoded", payload)
    }
  }
}
