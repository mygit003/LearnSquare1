package com.ori.learnsquare1.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri

import android.os.Build

import androidx.core.content.FileProvider
import java.io.File


/**
 * 创建人: zhengpf
 * 修改时间: 2021/5/3 16:57
 * 类说明:
 */
object FileProviderUtil {

    fun getUriForFile(context: Context, file: File): Uri? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getUriForFile24(context, file)
        } else {
            Uri.fromFile(file)
        }
    }


    private fun getUriForFile24(context: Context, file: File): Uri? {
        return FileProvider.getUriForFile(context, context.getPackageName().toString() + ".fileprovider", file)
    }


    fun setIntentDataAndType(context: Context, intent: Intent, type: String?, file: File, writeAble: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setDataAndType(getUriForFile(context, file), type)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type)
        }
    }
}