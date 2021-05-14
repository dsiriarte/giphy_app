package com.davidsantiagoiriarte.data.helpers

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.*

class DownloadGifHelper(private val context: Context) {

    fun downloadImage(
        gifName: String,
        url: String,
        onDownloadFinished: (internalLink: String) -> Unit
    ) {
        Glide.with(context)
            .download(url)
            .listener(
                object : RequestListener<File> {
                    override fun onLoadFailed(
                        glideException: GlideException?,
                        model: Any?,
                        target: Target<File>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        glideException?.let { throw it }
                        return false
                    }

                    override fun onResourceReady(
                        resource: File?,
                        model: Any?,
                        target: Target<File>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        onDownloadFinished(
                            saveGifImage(
                                resource?.let { getBytesFromFile(it) },
                                "$gifName.gif"
                            )
                        )
                        return true
                    }

                }
            ).submit()
    }

    private fun saveGifImage(bytes: ByteArray?, imgName: String?): String {
        var fos: FileOutputStream? = null
        try {
            val contextWrapper = ContextWrapper(context)
            val customDownloadDirectory = contextWrapper.getDir(LOCAL_STORAGE_DIR, Context.MODE_PRIVATE)
            if (!customDownloadDirectory.exists()) {
                val isFileMade: Boolean = customDownloadDirectory.mkdirs()
            }
            if (customDownloadDirectory.exists()) {
                val file = File(customDownloadDirectory, imgName)
                fos = FileOutputStream(file)
                fos.write(bytes)
                fos.flush()
                fos.close()
                if (file != null) {
                    val values = ContentValues()
                    values.put(MediaStore.Images.Media.TITLE, file.name)
                    values.put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
                    values.put(MediaStore.Images.Media.DESCRIPTION, "")
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/gif")
                    values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
                    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                    values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                    val contentResolver: ContentResolver = context.contentResolver
                    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                    return file.absolutePath
                }
                throw FileNotFoundException("Error creating file")
            } else {
                throw Exception("Error creating dir")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    @Throws(IOException::class)
    private fun getBytesFromFile(file: File): ByteArray? {
        val length = file.length()
        if (length > Int.MAX_VALUE) {
            throw IOException("File is too large!")
        }
        val bytes = ByteArray(length.toInt())
        var offset = 0
        var numRead = 0
        val `is`: InputStream = FileInputStream(file)
        `is`.use { `is` ->
            while (offset < bytes.size
                && `is`.read(bytes, offset, bytes.size - offset).also { numRead = it } >= 0
            ) {
                offset += numRead
            }
        }
        if (offset < bytes.size) {
            throw IOException("Could not completely read file " + file.name)
        }
        return bytes
    }

}

const val LOCAL_STORAGE_DIR="Giphy"