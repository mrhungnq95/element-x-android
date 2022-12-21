package io.element.android.x.features.rageshake.crash

import android.content.Context
import android.os.Build
import io.element.android.x.core.data.tryOrNull
import java.io.PrintWriter
import java.io.StringWriter
import timber.log.Timber

class VectorUncaughtExceptionHandler(
    context: Context
) : Thread.UncaughtExceptionHandler {
    private val crashDataStore = CrashDataStore(context)
    private var previousHandler: Thread.UncaughtExceptionHandler? = null

    /**
     * Activate this handler.
     */
    fun activate() {
        previousHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * An uncaught exception has been triggered.
     *
     * @param thread the thread
     * @param throwable the throwable
     */
    @Suppress("PrintStackTrace")
    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        Timber.v("Uncaught exception: $throwable")
        val bugDescription = buildString {
            val appName = "ElementX"
            // append(appName + " Build : " + versionCodeProvider.getVersionCode() + "\n")
            append("$appName Version : 1.0") // ${versionProvider.getVersion(longFormat = true)}\n")
            // append("SDK Version : ${Matrix.getSdkVersion()}\n")
            append("Phone : " + Build.MODEL.trim() + " (" + Build.VERSION.INCREMENTAL + " " + Build.VERSION.RELEASE + " " + Build.VERSION.CODENAME + ")\n")
            append("Memory statuses \n")
            var freeSize = 0L
            var totalSize = 0L
            var usedSize = -1L
            tryOrNull {
                val info = Runtime.getRuntime()
                freeSize = info.freeMemory()
                totalSize = info.totalMemory()
                usedSize = totalSize - freeSize
            }
            append("usedSize   " + usedSize / 1048576L + " MB\n")
            append("freeSize   " + freeSize / 1048576L + " MB\n")
            append("totalSize   " + totalSize / 1048576L + " MB\n")
            append("Thread: ")
            append(thread.name)
            append(", Exception: ")
            val sw = StringWriter()
            val pw = PrintWriter(sw, true)
            throwable.printStackTrace(pw)
            append(sw.buffer.toString())
        }
        Timber.e("FATAL EXCEPTION $bugDescription")
        crashDataStore.setCrashData(bugDescription)
        // Show the classical system popup
        previousHandler?.uncaughtException(thread, throwable)
    }
}
