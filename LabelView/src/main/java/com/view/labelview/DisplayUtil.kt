package com.view.labelview

import android.content.res.Resources
import kotlin.math.roundToInt

/**
 * @Author shige chen
 * @Date 2020/7/22 - 16:39
 * @Description
 * @Email shigechen@globalsources.com
 */
object DisplayUtil {
    /**
     * 获取屏幕宽度
     *
     * @return
     */
    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    fun dpToPx(dp: Float): Int {
        return (Resources.getSystem().displayMetrics.density * dp + 0.5f).roundToInt()
    }

    /**
     * px转sp
     *
     * @param px
     * @return
     */
    fun pxToSp(px: Int): Float {
        return (px / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).roundToInt()
            .toFloat()
    }

    /**
     * sp转px
     *
     * @param sp
     * @return
     */
    fun spToPx(sp: Int): Float {
        return (Resources.getSystem().displayMetrics.scaledDensity * sp + 0.5f).roundToInt().toFloat()
    }
}