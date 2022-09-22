package com.gvelesiani.passworx.common.extensions

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import java.io.*

fun Uri.readFileContent(context: Context?): String {
    val inputStream = this.let { context?.contentResolver?.openInputStream(it) }
    val reader = BufferedReader(
        InputStreamReader(
            inputStream
        )
    )
    val stringBuilder = StringBuilder()
    stringBuilder.append(reader.readLine())
    inputStream?.close()
    return stringBuilder.toString()
}

fun Uri.writeInFile(text: String, context: Context?) {
    try {
        val outputStream = context?.contentResolver?.openOutputStream(this)
        val bw = BufferedWriter(OutputStreamWriter(outputStream))
        bw.write(text)
        bw.flush()
        bw.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}