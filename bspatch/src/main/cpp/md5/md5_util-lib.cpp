//
// Created by aron on 17-11-15.
//

#include <jni.h>
#include "md5.h"
#include <string.h>
#include "md5_util-lib.h"

#define TAG "JNI_MD5"

// JNI interface functions, be careful about the naming.
extern "C"
JNIEXPORT jstring JNICALL Java_io_silvrr_installment_jni_Encrypt_Encrypt_md5SaltEncode
        (JNIEnv *env, jclass jclass1, jstring old_string, jboolean isSixteen, jboolean isLow) {
    const char *old = env->GetStringUTFChars(old_string, 0);
    int len = strlen(old);
    char *salt = (char *) SALT;
    char md5_str[len + strlen(salt) + 1];
    strcpy(md5_str, old);
    strcpy(&(md5_str[len]), salt);
    char output[32 + 1];
    md5_encode(md5_str, output, isLow);
    if (isSixteen) {
        char str[16 + 1];
        int i;
        for (i = 0; i < 16; i++) {
            str[i] = output[8 + i];
        }
        str[16] = '\0';
        env->ReleaseStringUTFChars(old_string, old);
        return env->NewStringUTF(str);
    }
    env->ReleaseStringUTFChars(old_string, old);
    return env->NewStringUTF(output);
}




