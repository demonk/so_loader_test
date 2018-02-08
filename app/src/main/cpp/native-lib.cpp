#include <jni.h>
#include <string>
#include <android/log.h>

#define  LOG_E(...)  __android_log_print(ANDROID_LOG_ERROR,"Netclient",__VA_ARGS__)

int *a = (int *) malloc(sizeof(int));

extern "C"
JNIEXPORT jstring
JNICALL
Java_cn_demonk_test2_MainActivity_loadtestparent(
        JNIEnv *env,
        jobject /* this */) {
    *a = 55;
    std::string hello = "Hello from native2";
    LOG_E("demonk,2testwo=%d,%p", *a, &a);
    LOG_E("demonk,2point=%#x", a);
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT jstring
JNICALL
Java_cn_demonk_dyapk_FuncEntry_loadtest(
        JNIEnv *env,
        jobject /* this */) {
    *a = 77;
    std::string hello = "Hello from native";
    LOG_E("demonk,testwo=%d,%p", *a, &a);
    LOG_E("demonk,point=%#x", a);
    return env->NewStringUTF(hello.c_str());
}

