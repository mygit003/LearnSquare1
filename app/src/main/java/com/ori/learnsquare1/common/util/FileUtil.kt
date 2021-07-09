package com.ori.learnsquare1.common.util

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.*

/**
 * 创建人 zhengpf
 * 时间 2021/5/31 14:37
 * 类说明:
 */
object FileUtil {

    private const val TAG = "FileUtil"


    /**
     * 缓存文件路径
     */
    val CACHE_FILE_PATH = "cachefile" + File.separator

    /**
     * 图片路径
     */
    val IMAGE_PATH = "image" + File.separator

    /**
     * 保存apk文件路径
     */
    val APK_UPDATE_PATH = "update" + File.separator

    fun getCacheFilePath(context: Context): String {
        return getFileDirPath(context) + CACHE_FILE_PATH
    }

    private fun getFileDirPath(context: Context): String? {
        var dirPath = ""
        dirPath = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            context.getExternalFilesDir("")!!.absolutePath + File.separator
        } else {
            context.filesDir.absolutePath + File.separator
        }
        return dirPath
    }


    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     */
    fun isExistFile(path: String?): Boolean {
        return try {
            val file = File(path)
            file.exists()
        } catch (e: Exception) {
            false
        }
    }


    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    fun deleteFile(filePath: String?): Boolean {
        val file = File(filePath)
        return if (file.isFile && file.exists()) {
            file.delete()
        } else false
    }


    /**
     * 删除文件夹下的所有文件
     *
     * @param path
     * @return
     */
    fun delAllFile(path: String?): Boolean {
        var flag = false
        val dirFile = File(path)
        if (!dirFile.exists()) {
            return flag
        }
        if (!dirFile.isDirectory) {
            return flag
        }
        val files = dirFile.listFiles()
        for (i in files.indices) {
            if (files[i].isFile) {
                //删除文件
                flag = deleteFile(files[i].absolutePath)
                if (!flag) break
            } else {
                //如果是文件夹，递归调用删除子文件夹和文件数据
                flag = delAllFile(files[i].absolutePath)
                if (!flag) break
            }
        }
        return if (!flag)
            false
        else
        //删除当前根目录
            dirFile.delete()
    }


    /**
     * 把字符串数据写入文件
     *
     * @param content 需要写入的字符串
     * @param path    文件路径名称
     * @param append  是否以添加的模式写入
     * @return 是否写入成功
     */
    fun writeFile(content: ByteArray?, path: String?, append: Boolean): Boolean {
        var res = false
        val f = File(path)
        var raf: RandomAccessFile? = null
        try {
            if (f.exists()) {
                if (!append) {
                    f.delete()
                    f.createNewFile()
                }
            } else {
                f.createNewFile()
            }
            if (f.canWrite()) {
                raf = RandomAccessFile(f, "rw")
                raf.seek(raf.length())
                raf.write(content)
                res = true
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            if (raf != null) {
                try {
                    raf.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return res
    }


    /**
     * 把字符串数据写入文件
     *
     * @param content 需要写入的字符串
     * @param path    文件路径名称
     * @param append  是否以添加的模式写入
     * @return 是否写入成功
     */
    fun writeFile(content: String, path: String?, append: Boolean): Boolean {
        return writeFile(content.toByteArray(), path, append)
    }


    /**
     * 保存数据
     *
     */
    fun saveFile(path: String?, fileName: String?, content: String) {
        var fos: FileOutputStream? = null
        var bos: BufferedOutputStream? = null
        try {
            // 创建指定路径的文件夹
            val directory = File(path)
            if (!directory.exists()) {
                directory.mkdirs()
            }
            // 创建指定路径的文件
            val file = File(path, fileName)
            // 如果文件不存在
            if (file.exists()) {
                // 创建新的空文件
                file.delete()
            }
            file.createNewFile()
            // 获取文件的输出流对象
            fos = FileOutputStream(file)
            bos = BufferedOutputStream(fos)
            // 获取字符串对象的byte数组并写入文件流
            bos.write(content.toByteArray())
            bos.flush()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                bos?.close()
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    fun copyFile(oldPath: String?, newPath: String?) {
        var fis: FileInputStream? = null
        var fos: FileOutputStream? = null
        try {
            var byteRead = 0
            val srcFile = File(oldPath)
            if (!srcFile.exists()) {
                return
            }
            fis = FileInputStream(oldPath)
            fos = FileOutputStream(newPath)
            val buffer = ByteArray(1024 * 4)
            while (fis.read(buffer).also { byteRead = it } != -1) {
                fos.write(buffer, 0, byteRead)
            }
            fos.flush()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                fis?.close()
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    fun copyFolder(oldPath: String?, newPath: String) {
        try {
            //判断目标文件夹是否存在
            val targetFile = File(newPath)
            if (!targetFile.exists()) {
                targetFile.mkdirs()
            }

            //遍历源文件夹
            val srcFile = File(oldPath)
            val files = srcFile.listFiles()
            if (files.size > 0) {
                for (file in files) {
                    if (file.isFile) {
                        copyFile(file.absolutePath, newPath + File.separator + file.name)
                    } else if (file.isDirectory) {
                        val dirNew = File(newPath + File.separator + file.name)
                        dirNew.mkdir() //在目标文件夹中创建文件夹
                        copyFolder(file.absolutePath, newPath + File.separator + file.name)
                    }
                }
            } else {
                Log.e("FileUtil", "source folder is empty")
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}