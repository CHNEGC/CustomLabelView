package com.view.labelview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlin.math.max

/**
 * @Author shige chen
 * @Date 2020/7/22 - 15:31
 * @Description 自定义标签View
 * @Email shigechen@globalsources.com
 */
class LabelView : ViewGroup {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        context.obtainStyledAttributes(attrs, R.styleable.LabelView).apply {
            verticalSpacing = getDimension(R.styleable.LabelView_verticalSpacing, DisplayUtil.dpToPx(10f).toFloat()).toInt()
            titleVerticalSpacing =
                getDimension(R.styleable.LabelView_titleVerticalSpacing, DisplayUtil.dpToPx(12f).toFloat()).toInt()
            horizontalSpacing = getDimension(R.styleable.LabelView_horizontalSpacing, DisplayUtil.dpToPx(10f).toFloat()).toInt()
            defLabelHeight = getDimension(R.styleable.LabelView_defLabelHeight, 0f).toInt()
            tagPaddingTop = getDimension(R.styleable.LabelView_tagPaddingTop, DisplayUtil.dpToPx(10f).toFloat()).toInt()
            tagPaddingLeft = getDimension(R.styleable.LabelView_tagPaddingLeft, DisplayUtil.dpToPx(10f).toFloat()).toInt()
            tagPaddingBottom = getDimension(R.styleable.LabelView_tagPaddingBottom, DisplayUtil.dpToPx(10f).toFloat()).toInt()
            tagPaddingRight = getDimension(R.styleable.LabelView_tagPaddingRight, DisplayUtil.dpToPx(10f).toFloat()).toInt()
            titleTextSize = getDimension(R.styleable.LabelView_titleTextSize, 18f)
            labelTextSize = getDimension(R.styleable.LabelView_labelTextSize, 16f)
            labelDrawablePadding =
                getDimension(R.styleable.LabelView_labelDrawablePadding, 0f).toInt()
            titleDrawablePadding =
                getDimension(R.styleable.LabelView_titleDrawablePadding, 0f).toInt()
            getInt(R.styleable.LabelView_labelDrawableGravity, 0).apply {
                when (this) {
                    1 -> labelDrawableGravity = DrawableState.Left
                    2 -> labelDrawableGravity = DrawableState.Top
                    3 -> labelDrawableGravity = DrawableState.Right
                    4 -> labelDrawableGravity = DrawableState.Bottom
                }
            }
            getInt(R.styleable.LabelView_titleDrawableGravity, 0).apply {
                when (this) {
                    1 -> titleDrawableGravity = DrawableState.Left
                    2 -> titleDrawableGravity = DrawableState.Top
                    3 -> titleDrawableGravity = DrawableState.Right
                    4 -> titleDrawableGravity = DrawableState.Bottom
                }
            }
            labelDefBackground = getSourceResourceId(
                R.styleable.LabelView_labelDefBackground,
                R.drawable.tag_bg_selector
            )
            labelSelBackground = getSourceResourceId(
                R.styleable.LabelView_labelSelBackground,
                R.drawable.tag_bg_selector
            )
            labelDrawable =
                getSourceResourceId(R.styleable.LabelView_labelDrawable, R.drawable.tag_bg_selector)
            maxColumn = getInt(R.styleable.LabelView_maxColumn, 4)
            maxRowNum = getInt(R.styleable.LabelView_maxRowNum, Int.MAX_VALUE)
            isAdaptive = getBoolean(R.styleable.LabelView_isAdaptive, false)
            isShowTitle = getBoolean(R.styleable.LabelView_isShowTitle, false)
            isMoreSelected = getBoolean(R.styleable.LabelView_isMoreSelected, false)
            isReverseElection = getBoolean(R.styleable.LabelView_isReverseElection, false)
            isShowUnlimited = getBoolean(R.styleable.LabelView_isShowUnlimited, false)
            isAllLabelShowDrawable = getBoolean(R.styleable.LabelView_isAllLabelShowDrawable, false)
            isSelectUnlimited = getBoolean(R.styleable.LabelView_isSelectUnlimited, false)
            showTitleStr = getString(R.styleable.LabelView_showTitleStr).toString()
            showUnlimited = getString(R.styleable.LabelView_showUnlimited).toString()
            titleTextColor =
                getColor(R.styleable.LabelView_titleTextColor, Color.parseColor("#2d2d2d"))
            labelTextColor =
                getColor(R.styleable.LabelView_labelTextColor, Color.parseColor("#2d2d2d"))
            labelTextColorSelect =
                getColor(R.styleable.LabelView_labelTextColorSelect, Color.parseColor("#E03236"))
        }.recycle()
    }

    /**
     * 子View之间的行间距
     */
    private var verticalSpacing = 0
    private var titleVerticalSpacing = 0

    /**
     * 子View之间的列间距
     */
    private var horizontalSpacing = 0
    private var isAdaptive = true
    private var isShowTitle = false
    private var showTitleStr = ""
    private var defLabelHeight = 0
    private var maxColumn = 4

    /**
     * 标签的上间距
     */
    private var tagPaddingTop = 10

    /**
     * 标签左间距
     */
    private var tagPaddingLeft = 15

    /**
     * 标签右间距
     */
    private var tagPaddingRight = 15

    /**
     * 标签底部间距
     */
    private var tagPaddingBottom = 10

    /**
     * 标签背景
     */
    private var labelDefBackground = 0

    /**
     * 选中背景
     */
    private var labelSelBackground = 0

    /**
     * 是否支持多选
     */
    private var isMoreSelected = true

    /**是否支持反选*/
    private var isReverseElection = true

    /**
     * 是否显示不限/全部
     */
    private var isShowUnlimited = false
    private var showUnlimited = ""
    private var labelDrawable = 0
    private var titleDrawable = 0
    private var isAllLabelShowDrawable = true
    private var labelDrawablePadding = 0
    private var titleDrawablePadding = 0
    private var labelDrawableGravity: DrawableState = DrawableState.Left
    private var titleDrawableGravity: DrawableState = DrawableState.Left

    /**
     * 是否默认选择不限
     */
    private var isSelectUnlimited = true

    /**
     * 标题字体大小
     */
    private var titleTextSize: Float = 0f

    /**
     * 标题默认字体验证
     */
    private var titleTextColor = Color.parseColor("#2d2d2d")

    /**
     * 标签字体
     */
    private var labelTextSize: Float = 12f

    /**
     * 标签默认颜色
     */
    private var labelTextColor = Color.parseColor("#2d2d2d")

    /**
     * 标签选择颜色
     */
    private var labelTextColorSelect = Color.parseColor("#E03236")

    /**
     * 最大行数 默认不限制
     */
    private var maxRowNum = Int.MAX_VALUE


    private var selectedBlock: ((Any?, Int) -> Unit)? = null

    private val allSelectedLabel by lazy {
        TextView(context).apply {
            text = showUnlimited
            gravity = Gravity.CENTER
            textSize = labelTextSize
            setTextColor(labelTextColor)
            setBackgroundResource(labelDefBackground)
            if (isAllLabelShowDrawable) {
                setDrawable(this, labelDrawableGravity, labelDrawablePadding, labelDrawable)
            }
            isSelected = isSelectUnlimited
        }.apply {
            setOnClickListener {
                selectAll()
            }
        }
    }

    private val titleLabel by lazy {
        TextView(context).apply {
            text = showTitleStr
            setTextColor(titleTextColor)
            gravity = Gravity.CENTER_VERTICAL
            textSize = titleTextSize
            layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            setDrawable(this, titleDrawableGravity, titleDrawablePadding, titleDrawable)
        }
    }
    private val mSelectLabel = mutableListOf<TextView>()
    private val mSelectValue = mutableListOf<Any>()

    /**获取选中结果*/
    val mSelectValueList: List<Any> get() = listOf(*mSelectValue.toTypedArray())

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //测量子View的大小

        val widthModel = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightModel = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //测量子View视图
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        var width = 0
        var height = 0

        var row = 1//行数
        var rowWidth = 0//行宽
        var rowMaxHeight = 0//行高

        //遍历所有得子View
        for (index in 0 until childCount) {
            if (row >= maxRowNum) {
                break
            }
            val child = getChildAt(index)

            //计算设置子view的大小
            if (!isShowTitle || (isShowTitle && index != 0)) {
                calculationChildSize(child, widthSize)
            }

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (child.visibility != View.GONE) {
                rowWidth += childWidth
                //判断子View得宽度是否大于父容器得宽度，如果大就换行处理
                if (rowWidth + paddingLeft + paddingRight > widthSize) {
                    //下一行
                    rowWidth = childWidth
                    //计算高度
                    height += if (row == 1 && isShowTitle) {
                        rowMaxHeight + titleVerticalSpacing
                    } else {
                        rowMaxHeight + verticalSpacing
                    }
                    //下一行得高度
                    rowMaxHeight = childHeight
                    //记录行数
                    row += 1
                } else {
                    //单行得高度
                    rowMaxHeight = max(rowMaxHeight, childHeight)
                }
                if (index < childCount - 1) {
                    rowWidth += horizontalSpacing
                }
            }
        }

        //最后一行的高度
        height += rowMaxHeight
        //最后一行的内间距
        height += paddingTop + paddingBottom

        //如果标签只有一行，则就设置宽度包裹标签
        if (row == 1) {
            width += rowWidth + paddingLeft + paddingRight
        } else {
            //如果标签超一行，则设置宽度填充父布局
            width = widthSize
        }
        //测量父容器的大小
        setMeasuredDimension(
            if (widthModel == MeasureSpec.EXACTLY) widthSize else width,
            if (heightModel == MeasureSpec.EXACTLY) heightSize else height
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val parentLeft = paddingLeft
        val parentRight = r - l - paddingRight
        val parentTop = paddingTop

        //子View左右间距
        var childViewLeft = parentLeft
        var childViewTop = parentTop

        var row = 1//行数
        var rowMaxHeight = 0//最大行高值

        for (index in 0 until childCount) {
            if (row >= maxRowNum) {
                break
            }
            val childView = getChildAt(index) ?: continue

            val childViewWidth = childView.measuredWidth
            val childViewHeight = childView.measuredHeight

            if (childView.visibility != View.GONE) {
                if (childViewLeft + childViewWidth > parentRight) {
                    //换行
                    childViewLeft = parentLeft
                    //如果显示title的时候，也要计算title与tag之间的高度
                    childViewTop += if (row == 1 && isShowTitle) {
                        rowMaxHeight + titleVerticalSpacing
                    } else {
                        rowMaxHeight + verticalSpacing
                    }
                    rowMaxHeight = childViewHeight

                    //记录行数
                    row++
                } else {
                    rowMaxHeight = max(rowMaxHeight, childViewHeight)
                }
                //重新绘制子view的位置
                childView.layout(
                    childViewLeft,
                    childViewTop,
                    childViewLeft + childViewWidth,
                    childViewTop + childViewHeight
                )
                childViewLeft = childViewLeft.plus(childViewWidth)
                if (index < childCount - 1) {
                    childViewLeft += horizontalSpacing
                }
            }
        }
    }

    //计算子View的大小
    private fun calculationChildSize(childView: View?, parentWidth: Int) {
        if (null != childView) {
            childView.setBackgroundResource(labelDefBackground)
            childView.setPadding(
                tagPaddingLeft,
                tagPaddingTop,
                tagPaddingRight,
                tagPaddingBottom
            )
            if (isAdaptive) {
                val mParas: MarginLayoutParams = layoutParams as MarginLayoutParams
                val newChildWidth =
                    (parentWidth - mParas.rightMargin - mParas.leftMargin - paddingRight - paddingLeft -
                            (horizontalSpacing * (maxColumn - 1))) / maxColumn
                childView.layoutParams.width = newChildWidth
                childView.layoutParams.height =
                    if (defLabelHeight > 0) defLabelHeight else LayoutParams.WRAP_CONTENT
            } else {
                childView.layoutParams.width = LayoutParams.WRAP_CONTENT
                childView.layoutParams.height =
                    if (defLabelHeight > 0) defLabelHeight else LayoutParams.WRAP_CONTENT

            }
        }
    }

    private fun setDrawable(
        label: TextView,
        drawableGravity: DrawableState,
        drawablePadding: Int,
        drawable: Int
    ) {
        label.compoundDrawablePadding = drawablePadding
        when (drawableGravity) {
            DrawableState.Left -> label.setCompoundDrawablesWithIntrinsicBounds(
                drawable,
                0,
                0,
                0
            )
            DrawableState.Right -> label.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                drawable,
                0
            )
            DrawableState.Top -> label.setCompoundDrawablesWithIntrinsicBounds(
                0,
                drawable,
                0,
                0
            )
            DrawableState.Bottom -> label.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                0,
                drawable
            )
        }
    }

    /**创建标签 带默认选中项*/
    fun <T : Any> onCreateLabel(
        labels: List<T>,
        selectLabels: List<T>? = null,
        block: ((Int) -> String?)? = null
    ) {
        mSelectValue.clear()
        selectLabels?.let { mSelectValue.addAll(it) }
        onCreateLabel(labels, block)
    }

    /**统一创建标签*/
    fun <T : Any> onCreateLabel(
        labels: List<T>,
        block: ((Int) -> String?)? = null
    ) {
        removeAllViews()
        mSelectLabel.clear()
        mSelectValue.clear()
        if (isShowTitle) {
            addView(titleLabel)
        }
        //显示不限或者全选
        if (isShowUnlimited) {
            addView(allSelectedLabel)
        }
        labels.forEachIndexed { index, value ->
            val label = TextView(context).apply {
                text = if (value is String) value else block?.invoke(index) ?: ""
                gravity = Gravity.CENTER
                textSize = labelTextSize
                setTextColor(labelTextColor)
                setDrawable(this, labelDrawableGravity, labelDrawablePadding, labelDrawable)
            }.apply {
                setOnClickListener {
                    selectLabel(this, index, value)
                }
            }
            addView(label)
        }
    }

    private fun selectAll() {
        mSelectLabel.forEach {
            it.isSelected = false
            setSelectedState(it)
        }
        mSelectLabel.clear()
        mSelectValue.clear()
        allSelectedLabel.isSelected = true
        setSelectedState(allSelectedLabel)
        selectedBlock?.invoke(null, -1)
    }

    private fun unSelectedAll() {
        allSelectedLabel.isSelected = false
        setSelectedState(allSelectedLabel)
    }

    private fun <T : Any> selectLabel(label: TextView, position: Int, value: T) {
        selectedBlock?.invoke(value, position)
        if (isReverseElection && label in mSelectLabel) {
            label.isSelected = false
            mSelectValue.remove(value)
            mSelectLabel.remove(label)
        } else if (isMoreSelected) {
            unSelectedAll()
            label.isSelected = true
            if (value !in mSelectValue) {
                mSelectValue.add(value)
            }
            if (label !in mSelectLabel) {
                mSelectLabel.add(label)
            }
        } else {
            unSelectedAll()
            mSelectLabel.forEach {
                it.isSelected = false
                setSelectedState(it)
            }
            mSelectLabel.clear()
            mSelectValue.clear()
            label.isSelected = true
            mSelectLabel.add(label)
            mSelectValue.add(value)
        }
        setSelectedState(label)
    }

    private fun setSelectedState(label: TextView) {
        label.setTextColor(if (label.isSelected) labelTextColorSelect else labelTextColor)
        label.setBackgroundResource(if (label.isSelected) labelSelBackground else labelDefBackground)
    }

    fun setSelectedBlock(selectedBlock: (Any?, Int) -> Unit) {
        this.selectedBlock = selectedBlock
    }
}