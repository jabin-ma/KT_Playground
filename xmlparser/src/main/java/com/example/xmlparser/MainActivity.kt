package com.example.xmlparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Xml
import org.xmlpull.v1.XmlPullParser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fio = resources.openRawResource(R.raw.test)
        val xmlParser = Xml.newPullParser()
        xmlParser.setInput(fio,null)
        xmlParser tagWith "config"

        xmlParser.nextDepth {
//            Log.d(TAG, "depth1: $eventType $name $text")
            nextDepth() {
                val tag = getAttributeValue(null,"tag")
                Log.d(TAG, "parse:$name ${nextText()} $tag")
            }
        }

        Log.d(TAG, "onCreate: END")
    }

    companion object{
        const val TAG ="MainActivity"
    }
}