//
// Created by ligs on 2/7/18.
//
#include <jni.h>
#include <string>
#include <android/log.h>

#define  LOG_E(...)  __android_log_print(ANDROID_LOG_ERROR,"Netclient",__VA_ARGS__)
int *a = (int *) malloc(sizeof(int));

extern "C"
JNIEXPORT jstring

JNICALL
Java_cn_demonk_dyapk_FuncEntry_loadtesttwo(
        JNIEnv *env,
        jobject /* this */) {
    *a = 66;
    std::string hello = "Hello from other native";
    LOG_E("demonk,test=%d,%d", *a, &a);
    LOG_E("demonk,point=%#x",a);
    return env->NewStringUTF(hello.c_str());
}
