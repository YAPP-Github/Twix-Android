package com.twix.ui.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream

/**
 * 주어진 [Uri]로부터 이미지를 읽어 JPEG 형식의 [ByteArray]로 변환한다.
 *
 * 내부 동작 과정:
 * 1. [ContentResolver.openInputStream]으로 InputStream을 연다.
 * 2. [BitmapFactory.decodeStream]으로 Bitmap 디코딩
 * 3. JPEG(품질 90) 압축 후 ByteArray 반환
 *
 * 실패 케이스:
 * - InputStream 열기 실패
 * - 디코딩 실패 (손상 이미지 등)
 * - 압축 실패
 *
 * @param imageUri 변환할 이미지 Uri (content:// 또는 file://)
 * @return 변환 성공 시 JPEG 바이트 배열, 실패 시 null
 */
fun Context.uriToByteArray(imageUri: Uri): ByteArray? {
    return try {
        contentResolver.openInputStream(imageUri)?.use { inputStream ->
            val bitmap = BitmapFactory.decodeStream(inputStream) ?: return null

            ByteArrayOutputStream().use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                bitmap.recycle()
                outputStream.toByteArray()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
