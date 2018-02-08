package cn.demonk.dyapk

import android.util.Log

/**
 * Created by guosen.lgs@alibaba-inc.com on 2/7/18.
 */

class FuncEntry() {

    external fun loadtest(): String
    external fun loadtesttwo(): String

    fun loadLibOne() {
        System.loadLibrary("native-lib")
        Log.e("demonk", "load one")
        loadtest()
    }

    fun loadLibTwo() {
        System.loadLibrary("other-native")
        Log.e("demonk", "load two")
        loadtesttwo()
    }

    fun loadLibThree(){
        System.load("/data/data/cn.demonk.test2/files/libnative-lib-other.so")
        Log.e("demonk", "load three")
        loadtest()
    }


}