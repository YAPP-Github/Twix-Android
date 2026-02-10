package com.twix.ui.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream

/**
 * 주어진 [Uri]로부터 이미지를 읽어 [ByteArray]로 변환한다.
 *
 * 내부 동작 과정:
 * 1. [ContentResolver.openInputStream]을 통해 Uri를 InputStream으로 연다.
 * 2. 스트림을 [BitmapFactory.decodeStream]으로 Bitmap으로 디코딩한다.
 * 3. Bitmap을 JPEG 형식(품질 90)으로 압축하여 [ByteArrayOutputStream]에 기록한다.
 * 4. 최종적으로 압축된 바이트 배열을 반환한다.
 *
 * @param imageUri 변환할 이미지의 Uri (content:// 또는 file://)
 * @return JPEG 압축된 이미지 바이트 배열, 실패 시 null
 */
fun Context.uriToByteArray(imageUri: Uri): ByteArray =
    try {
        val inputStream = contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)

        outputStream.toByteArray() ?: byteArrayOf()
    } catch (e: Exception) {
        e.printStackTrace()
        byteArrayOf()
    }
