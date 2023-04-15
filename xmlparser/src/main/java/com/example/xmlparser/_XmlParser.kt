package com.example.xmlparser

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParser.*
import org.xmlpull.v1.XmlPullParserException

infix fun XmlPullParser.tagWith(tag: String): Boolean {
    var type: Int
    while (next().also { type = it } != START_TAG && type != END_DOCUMENT) {
    }

    if (type != START_TAG) {
        throw XmlPullParserException("No start tag found")
    }

    if (!name.equals(tag)) {
        throw XmlPullParserException(
            "Unexpected start tag: found $name, expected $tag"
        )
    }
    return true
}


fun XmlPullParser.nextDepth(parserTag: XmlPullParser.() -> Unit) {
    val outerDepth = depth
    while (true) {
        val type: Int = next()
        if (type == END_DOCUMENT || type == END_TAG && depth == outerDepth) {
            return
        }
        if (type == START_TAG && depth == outerDepth + 1) { //next deep
            parserTag()
        }
        // others?
    }
}