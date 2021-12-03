//
// Created by Lebron Sn on 2017/2/6.
//

#ifndef BSDIFFPATCH_BS_H
#define BSDIFFPATCH_BS_H
#endif //BSDIFFPATCH_BS_H
#include <malloc.h>
#include <jni.h>
int mypatch(int argc,char * argv[]);
JNIEXPORT jint JNICALL
Java_io_silvrr_installment_module_weboffline_BsdiffPatch_patch
        (JNIEnv *env, jobject instance, jstring oldpath_, jstring newpath_,jstring patch_);
