package com.view.labelview

/**
 * @Author shige chen
 * @Date 2020/7/23 - 9:47
 * @Description
 * @Email shigechen@globalsources.com
 */
sealed class DrawableState {
    object Top : DrawableState()
    object Left : DrawableState()
    object Right : DrawableState()
    object Bottom : DrawableState()
}