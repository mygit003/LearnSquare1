package com.ori.learnsquare1.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

/**
 * 创建人: zhengpf
 * 修改时间: 2020/6/26 15:21
 * 类说明:Context扩展函数
 */

/**
 * 实现将特定文本复制到剪贴板的功能。
 * @param[label] User-visible label for the clip data.
 * @param[text] The actual text in the clip.
 */
fun Context.copyTextIntoClipboard(text: CharSequence?, label: String? = "") {
    if (text.isNullOrEmpty()) return
    val cbs = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        ?: return
    cbs.setPrimaryClip(ClipData.newPlainText(label, text))
}

fun Context.showToast(message: CharSequence) {
    Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show()
}


fun Context.showToast(resId: Int) {
    Toast.makeText(this, getText(resId), Toast.LENGTH_SHORT).show()
}