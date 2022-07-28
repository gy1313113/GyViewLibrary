package com.gaoyu.signaturepad

import android.graphics.Color
import android.graphics.drawable.Drawable

/**
 * 手写签名板配置文件
 *
 * @author Created by gaoyu on 2022/7/27 13:24
 */
open class SignaturePadConfig {
    /**
     * 背景颜色
     */
    var bgColor: Int = Color.WHITE
    /**
     * 背景drawable
     */
    var bgDrawable: Drawable? = null
    /**
     * 字的线宽
     */
    var textLineWidth: Float = 10f
    /**
     * 字的颜色
     */
    var textColor: Int = Color.BLACK

}