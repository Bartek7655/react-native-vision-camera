class RecordingStartEventModule(
  reactContext: ReactApplicationContext
) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String = "RecordingStartEventModule"

  @ReactMethod
  fun addListener(eventName: String) {
    // RN requirement, no-op
  }

  @ReactMethod
  fun removeListeners(count: Int) {
    // RN requirement, no-op
  }

  companion object {
    fun emitFirstFrameTimestamp(ctx: Context, firstFrameNs: Long) {
      val reactCtx: ReactApplicationContext? =
        when (ctx) {
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

      val payload = Arguments.createMap().apply {
        putDouble("firstFrameNs", firstFrameNs.toDouble())
      }

      reactCtx
        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
        .emit("VisionCamera_FirstFrameEncoded", payload)
    }
  }
}
