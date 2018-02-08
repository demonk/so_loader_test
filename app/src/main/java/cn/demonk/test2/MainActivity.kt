package cn.demonk.test2

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import dalvik.system.DexClassLoader
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var dyApkClassLoader: DexClassLoader
    private lateinit var dyApkClassLoader2: DexClassLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.text = "hello from java"
//        sample_text.text = stringFromJNI()
    }

    fun loadAPk(v: View) {
        val file = File(filesDir, "dyapk.apk")
//        if (!file.exists()) {
        val input = assets.open("dyapk.apk")
        val output = file.outputStream()
        val byteArray = ByteArray(512)
        var size: Int
        do {
            size = input.read(byteArray)
            if (size == -1)
                break
            output.write(byteArray, 0, size)

        } while (size != -1)
        output.flush()
        output.close()
        input.close()
//        }

        assert(file.exists())

        Log.e("demonk", "file=" + file.path)
        dyApkClassLoader = DexClassLoader(file.path, file.parent, File(cacheDir.parent, "lib").absolutePath, ClassLoader.getSystemClassLoader())
        Log.e("demonk", "cl=" + dyApkClassLoader)

        assert(dyApkClassLoader != null)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadso1(v: View) {
        if (dyApkClassLoader != null) {
            var entryClass = dyApkClassLoader.loadClass("cn.demonk.dyapk.FuncEntry")//java class
            entryClass.getMethod("loadLibOne").invoke(entryClass.newInstance())


//                Log.e("demonk","mechiod="+it)
//            }
//            var methodOne = entryClass::class.memberFunctions.stream().filter { it.name == "loadLibOne" }.findAny().get().javaMethod
//            Log.e("demonk","member="+methodOne)
//
//            methodOne!!.isAccessible=true
//            methodOne.invoke(entryClass)
//            entryClass::class.declaredMemberFunctions.firstOrNull{
//                it.name == "loadLibOne"
//            }?.call()
        }
    }

    fun loadso2(v: View) {
        if (dyApkClassLoader != null) {
            var entryClass = dyApkClassLoader.loadClass("cn.demonk.dyapk.FuncEntry")//java class
            entryClass.getMethod("loadLibTwo").invoke(entryClass.newInstance())
//            val entryClass = dyApkClassLoader.loadClass("cn.demonk.dyapk.FuncEntry")
//            entryClass::class.declaredMemberFunctions.forEach {
//                Log.e("demonk", "class=" + it)
//            }
//            entryClass::class.declaredMemberFunctions.firstOrNull {
//                it.name == "loadLibTwo"
//            }?.call()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadso3(v: View) {
        var so_source = File(filesDir.parentFile.path + File.separator + "lib", "libnative-lib.so")
        var so_dst = File(filesDir, "libnative-lib-other.so")

        val input = so_source.inputStream()
        val output = so_dst.outputStream()

        val byteArray = ByteArray(512)

        var size: Int

        do {
            size = input.read(byteArray, 0, 512)
            if (size == -1)
                break
            output.write(byteArray, 0, size)
        } while (size != -1)

        output.flush()
        output.close()
        input.close()


        if (dyApkClassLoader != null) {
            var entryClass = dyApkClassLoader.loadClass("cn.demonk.dyapk.FuncEntry")//java class
            entryClass.getMethod("loadLibThree").invoke(entryClass.newInstance())

        }

    }

    fun click_2(v: View) {
        val file = File(cacheDir, "dyapk.apk")
//        if (!file.exists()) {
        val input = assets.open("dyapk.apk")
        val output = file.outputStream()
        val byteArray = ByteArray(512)
        var size: Int
        do {
            size = input.read(byteArray)
            if (size == -1)
                break
            output.write(byteArray, 0, size)

        } while (size != -1)
        output.flush()
        output.close()
        input.close()
//        }

        assert(file.exists())

        Log.e("demonk", "file=" + file.path)
        dyApkClassLoader2 = DexClassLoader(file.path, file.parent, File(cacheDir.parent, "lib").absolutePath, ClassLoader.getSystemClassLoader())
        Log.e("demonk", "cl=" + dyApkClassLoader2)
    }

    fun loadso1_2(v: View) {
        if (dyApkClassLoader2 != null) {
            var entryClass = dyApkClassLoader2.loadClass("cn.demonk.dyapk.FuncEntry")//java class
            entryClass.getMethod("loadLibOne").invoke(entryClass.newInstance())
        }

    }

    fun loadso2_2(v: View) {
        if (dyApkClassLoader2 != null) {
            var entryClass = dyApkClassLoader2.loadClass("cn.demonk.dyapk.FuncEntry")//java class
            entryClass.getMethod("loadLibTwo").invoke(entryClass.newInstance())
        }
    }


    fun loadso3_2(v:View){
        var so_source = File(filesDir.parentFile.path + File.separator + "lib", "libnative-lib.so")
        var so_dst = File(filesDir, "libnative-lib-other.so")

        val input = so_source.inputStream()
        val output = so_dst.outputStream()

        val byteArray = ByteArray(512)

        var size: Int

        do {
            size = input.read(byteArray, 0, 512)
            if (size == -1)
                break
            output.write(byteArray, 0, size)
        } while (size != -1)

        output.flush()
        output.close()
        input.close()


        if (dyApkClassLoader2 != null) {
            var entryClass = dyApkClassLoader2.loadClass("cn.demonk.dyapk.FuncEntry")//java class
            entryClass.getMethod("loadLibThree").invoke(entryClass.newInstance())

        }
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    external fun stringFromJNI(): String

//    companion object {
//
//        // Used to load the 'native-lib' library on application startup.
//        init {
//            System.loadLibrary("native-lib")
//        }
//    }
}
