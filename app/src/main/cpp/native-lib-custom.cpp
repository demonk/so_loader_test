#include <jni.h>
#include <string>
#include <android/log.h>

#define  LOG_E(...)  __android_log_print(ANDROID_LOG_ERROR,"Netclient",__VA_ARGS__)

int *a = (int *) malloc(sizeof(int));

extern "C"
jstring loadtestparent(
        JNIEnv *env,
        jobject /* this */) {
    *a = 55;
    std::string hello = "Hello from native2";
    LOG_E("demonk,2testwo=%d,%p", *a, &a);
    LOG_E("demonk,2point=%#x", a);
    return env->NewStringUTF(hello.c_str());
}

jstring loadtest(
        JNIEnv *env,
        jobject /* this */) {
    *a = 77;
    std::string hello = "Hello from native";
    LOG_E("demonk,testwo=%d,%p", *a, &a);
    LOG_E("demonk,point=%#x", a);
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    __android_log_print(ANDROID_LOG_INFO, "JNI", "JNI onload!!");
    JNIEnv *env = 0;
    if ((vm->GetEnv((void **) &env, JNI_VERSION_1_6)) != JNI_OK) {
        return JNI_ERR;
    }

//    JNINativeMethod methods[] = {
//            {"loadtestparent", "(Ljava/lang/String;Ljava/lang/String;)Z", (void *) &loadtestparent},
//            {"loadtest",       "(Ljava/lang/String;)Z",                   (void *) &loadtest}
//    };

#define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
    //jclass jclazz = env->FindClass("cn/ninegame/library/util/NativeUtil");
//    jclass jclazz=env->FindClass("cn/uc/gamesdk/preloader/natives/SoftLinkHelper");
    jclass jclazz = env->FindClass("cn/demonk/dyapk/FuncEntry");
    jthrowable ex = env->ExceptionOccurred();
    if (0 != ex) {
        env->ExceptionClear();
    }
    if (jclazz == NULL) {
        jclazz = env->FindClass("cn/demonk/test2/MainActivity");
        if (jclazz == NULL) {
            return JNI_ERR;
        }
        LOG_E("demonk,loading MainActivity");
        JNINativeMethod methods[] = {
                {"loadtestparent", "()Ljava/lang/String;", (void *) &loadtestparent}
        };
        if (env->RegisterNatives(jclazz, methods, NELEM(methods)) < 0) {
            return JNI_ERR;
        }
    } else {
        LOG_E("demonk,loading FuncEntry");
        JNINativeMethod methods[] = {
                {"loadtest", "()Ljava/lang/String;", (void *) &loadtest}
        };
        if (env->RegisterNatives(jclazz, methods, NELEM(methods)) < 0) {
            return JNI_ERR;
        }
    }

    return JNI_VERSION_1_6;
}
