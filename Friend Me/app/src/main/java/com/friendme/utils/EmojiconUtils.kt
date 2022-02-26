package com.friendme.utils

import android.util.Log
import java.lang.Exception
import java.lang.StringBuilder

class EmojiconUtils {

    fun escapeJavaStringEmoji(st: String): String? {
        val builder = StringBuilder()
        try {
            for (i in 0 until st.length) {
                val c = st[i]
                if (!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c) && !Character.isWhitespace(
                        c
                    )
                ) {
                    var unicode = c.toString()
                    val code = c.toInt()
                    if (!(code >= 0 && code <= 255)) {
                        unicode = "\\u" + Integer.toHexString(c.toInt())
                    }
                    builder.append(unicode)
                } else {
                    builder.append(c)
                }
            }
            Log.i("Unicode Block", builder.toString())
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return builder.toString()
    }

    fun unescapeJava(escaped: String): String? {
        var escaped = escaped
        if (escaped.indexOf("\\u") == -1) return escaped
        var processed = ""
        var position = escaped.indexOf("\\u")
        while (position != -1) {
            if (position != 0) processed += escaped.substring(0, position)
            val token = escaped.substring(position + 2, position + 6)
            escaped = escaped.substring(position + 6)
            processed += token.toInt(16).toChar()
            position = escaped.indexOf("\\u")
        }
        processed += escaped
        return processed
    }
}