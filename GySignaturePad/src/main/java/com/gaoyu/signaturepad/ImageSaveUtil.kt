package com.gaoyu.signaturepad

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.os.Build
import android.content.Intent
import android.provider.MediaStore
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.*
import java.lang.Exception

/**
 * 图片保存工具，同时适配android10以上和以下
 *
 * @author Created by gaoyu on 2022/8/1 14:45
 */
object ImageSaveUtil {
    /**
     * 保存图片到公共目录
     * 29 以下，需要提前申请文件读写权限
     * 29及29以上的，需要动态获取权限
     * 保存的文件在 Picture 目录下
     *
     * @param context 上下文
     * @param bitmap  需要保存的bitmap
     * @param format  图片格式
     * @param quality 压缩的图片质量
     * @param recycle 完成以后，是否回收Bitmap，建议为true
     * @return 文件的 uri
     */
    fun saveBitmap(
        context: Context,
        bitmap: Bitmap,
        format: CompressFormat,
        quality: Int,
        recycle: Boolean
    ): Uri? {
        val suffix: String = if (CompressFormat.JPEG == format) "JPG" else format.name
        var fileName = System.currentTimeMillis().toString() + "_" + quality + "." + suffix
        if (::fileName.isInitialized) {
            fileName = this.fileName + "." + suffix
        }
        if (Build.VERSION.SDK_INT < 29) {
            if (!isGranted(context)) {
                Log.e("ImageUtils", "need permission")
                if (::listener.isInitialized) {
                    listener.onFailed()
                }
                return null
            }
            val picDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val destFile = File(picDir, fileName)
            if (!save(bitmap, destFile, format, quality, recycle)) {
                if (::listener.isInitialized) {
                    listener.onFailed()
                }
                return null
            }
            var uri: Uri? = null
            if (destFile.exists()) {
                uri = Uri.parse("file://" + destFile.absolutePath)
                val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                intent.data = uri
                context.sendBroadcast(intent)
            }
            if (::listener.isInitialized) {
                listener.onSuccess()
            }
            return uri
        } else {
            // Android 10 以上使用
            val contentUri: Uri =
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else MediaStore.Images.Media.INTERNAL_CONTENT_URI
            val contentValues = ContentValues()
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/*")
            contentValues.put(
                MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + "/"
            )
            // 告诉系统，文件还未准备好，暂时不对外暴露
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 1)
            val uri = context.contentResolver.insert(contentUri, contentValues)
            if (uri == null) {
                if (::listener.isInitialized) {
                    listener.onFailed()
                }
                return null
            }
            var os: OutputStream? = null
            try {
                os = context.contentResolver.openOutputStream(uri)
                bitmap.compress(format, quality, os)
                // 告诉系统，文件准备好了，可以提供给外部了
                contentValues.clear()
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                context.contentResolver.update(uri, contentValues, null, null)
                if (::listener.isInitialized) {
                    listener.onSuccess()
                }
                return uri
            } catch (e: Exception) {
                e.printStackTrace()
                // 失败的时候，删除此 uri 记录
                context.contentResolver.delete(uri, null, null)
                if (::listener.isInitialized) {
                    listener.onFailed()
                }
                return null
            } finally {
                try {
                    os?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun save(
        bitmap: Bitmap,
        file: File,
        format: CompressFormat,
        quality: Int,
        recycle: Boolean
    ): Boolean {
        if (isEmptyBitmap(bitmap)) {
            Log.e("ImageUtils", "bitmap is empty.")
            return false
        }
        if (bitmap.isRecycled) {
            Log.e("ImageUtils", "bitmap is recycled.")
            return false
        }
        if (!createFile(file, true)) {
            Log.e("ImageUtils", "create or delete file <\$file> failed.")
            return false
        }
        var os: OutputStream? = null
        var ret = false
        try {
            os = BufferedOutputStream(FileOutputStream(file))
            ret = bitmap.compress(format, quality, os)
            if (recycle && !bitmap.isRecycled) bitmap.recycle()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return ret
    }

    private fun isEmptyBitmap(bitmap: Bitmap?): Boolean {
        return bitmap == null || bitmap.isRecycled || bitmap.width == 0 || bitmap.height == 0
    }

    private fun createFile(file: File?, isDeleteOldFile: Boolean): Boolean {
        if (file == null) return false
        if (file.exists()) {
            if (isDeleteOldFile) {
                if (!file.delete()) return false
            } else return file.isFile
        }
        return if (!createDir(file.parentFile)) {
            false
        } else {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                false
            }
        }
    }

    private fun createDir(file: File?): Boolean {
        if (file == null) return false
        return if (file.exists()) file.isDirectory else file.mkdirs()
    }

    private fun isGranted(context: Context): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private lateinit var listener: BitmapSaveListener

    private lateinit var fileName: String

    /**
     * 设置图片保存成功和失败后的操作
     */
    fun setBitmapSaveListener(listener: BitmapSaveListener) {
        this.listener = listener
    }

    /**
     * 设置文件名，不带后缀，不设置就用默认的
     */
    fun setFileName(name: String) {
        this.fileName = name
    }

    interface BitmapSaveListener {
        /**
         * 图片保存成功
         */
        fun onSuccess()

        /**
         * 图片保存失败
         */
        fun onFailed()
    }
}