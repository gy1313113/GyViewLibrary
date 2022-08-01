package com.gaoyu.signaturepad

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


/**
 * 手写签名板
 *
 * @author Created by gaoyu on 2022/7/27 13:00
 */
open class SignaturePad : View {
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    /**
     * 文本画笔
     */
    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 配置文件
     */
    open var config: SignaturePadConfig = SignaturePadConfig()

    private var path: Path = Path()

    private var cacheBitmap: Bitmap? = null

    private var cacheCanvas: Canvas? = null

    init {
        textPaint.strokeCap = Paint.Cap.ROUND
        textPaint.style = Paint.Style.STROKE
    }

    private fun init() {
        cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        cacheCanvas = Canvas(cacheBitmap!!)
        if (config.bgDrawable == null) {
            cacheCanvas!!.drawColor(config.bgColor)
        } else {
            config.bgDrawable!!.setBounds(0, 0, width, height)
            config.bgDrawable!!.draw(cacheCanvas!!)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)

        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)

        var w = widthSpecSize
        var h = heightSpecSize

        var wm = widthSpecMode
        var hm = heightSpecMode

        when {
            //长宽设置的wrap_content都当match_parent处理
            wm == MeasureSpec.AT_MOST -> wm = MeasureSpec.EXACTLY
            hm == MeasureSpec.AT_MOST -> hm = MeasureSpec.EXACTLY
        }

        super.onMeasure(MeasureSpec.makeMeasureSpec(w, wm), MeasureSpec.makeMeasureSpec(h, hm))
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(c: Canvas?) {
        if (cacheBitmap == null) {
            init()
        }
        if (c != null) {
            textPaint.strokeWidth = config.textLineWidth
            textPaint.color = config.textColor
            c.drawBitmap(cacheBitmap!!, 0f, 0f, null)
            c.drawPath(path, textPaint)
        }
    }

    override fun dispatchTouchEvent(e: MotionEvent?): Boolean {
        if (e != null) {
            when (e.action) {
                //防止上层ViewGroup拦截点击事件
                MotionEvent.ACTION_DOWN -> parent.requestDisallowInterceptTouchEvent(true)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(
                    false
                )
            }
        }
        return super.dispatchTouchEvent(e)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        if (e != null) {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(e.x, e.y)
                }
                MotionEvent.ACTION_MOVE -> {
                    path.lineTo(e.x, e.y)
                }
                MotionEvent.ACTION_UP -> {
                    cacheCanvas!!.drawPath(path, textPaint)
                    path.reset()
                }
            }
            invalidate()
        }
        return true
    }

    /**
     * 签名保存到相册，android10之后的WRITE_EXTERNAL_STORAGE权限需要自己动态获取
     */
    open fun saveSignature() {
        if (cacheCanvas == null || cacheBitmap == null) {
            return
        }
        //先判断SD卡是否正常
        val state = Environment.getExternalStorageState()
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            val dicPath =
                Environment.getExternalStorageDirectory().absolutePath + File.separator + Environment.DIRECTORY_PICTURES + File.separator
            val dic = File(dicPath)
            val fileName = System.currentTimeMillis().toString() + ".jpg"
            val file = File(dic, fileName)

            cacheCanvas!!.save()
            cacheCanvas!!.restore()

            val fos: FileOutputStream?
            try {
                fos = FileOutputStream(file)
                if (cacheBitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, fos)) {
                    fos.flush()
                    fos.close()
                    //图片导入相册
                    // MediaStore.Images.Media.insertImage(
                    //     context.contentResolver,
                    //     file.absolutePath, fileName, null
                    // )
                } else {
                    fos.close()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}