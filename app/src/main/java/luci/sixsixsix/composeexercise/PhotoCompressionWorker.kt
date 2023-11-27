package luci.sixsixsix.composeexercise

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.roundToInt

class PhotoCompressionWorker(
    private val applicationContext: Context,
    private val params: WorkerParameters
): CoroutineWorker(applicationContext, params) {
    override suspend fun doWork(): Result {
        // to show notification : setForeground()
        // run on a dispatcher optimized for IO
        return withContext(Dispatchers.IO) {
            val stringUri = params.inputData.getString(KEY_CONTENT_URI)
            val compressionThresholdBytes = params.inputData.getLong(
                KEY_COMPRESSION_THRESHOLD,
                0L
            )
            val uri = Uri.parse(stringUri)
            // use takes care of closing the inputstream once done
            val bytes = applicationContext.contentResolver.openInputStream(uri)?.use {
                it.readBytes()
            } ?: return@withContext Result.failure()
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            var outputBytes: ByteArray
            var quality = 100
            do {
                val outputStream = ByteArrayOutputStream()
                outputStream.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                    outputBytes = outputStream.toByteArray()
                    quality -= (quality * 0.1).roundToInt()
                }
            } while (outputBytes.size > compressionThresholdBytes && quality > 5)

            val file = File(applicationContext.cacheDir, "${params.id}.jpg")
            file.writeBytes(outputBytes)
            Result.success(
                workDataOf(KEY_RESULT_PATH to file.absolutePath)
            )
        }
    }

    companion object {
        const val KEY_CONTENT_URI = "KEY_CONTENT_URI"
        const val KEY_COMPRESSION_THRESHOLD = "KEY_COMPRESSION_THRESHOLD"
        const val KEY_RESULT_PATH = "KEY_RESULT_PATH"
    }
}
