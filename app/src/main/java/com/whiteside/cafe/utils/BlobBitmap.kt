package com.whiteside.cafe.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.firebase.firestore.Blob
import java.io.ByteArrayOutputStream


class BlobBitmap {

    companion object {
        fun convertBlobToBitmap(blob: Blob?): Bitmap? {
            if (blob != null) {
                return BitmapFactory.decodeByteArray(blob.toBytes(), 0, blob.toBytes().size)
            }
            return null
        }

        fun convertBitmapToBlob(bitmap: Bitmap): Blob {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return Blob.fromBytes(stream.toByteArray())
        }
    }

}