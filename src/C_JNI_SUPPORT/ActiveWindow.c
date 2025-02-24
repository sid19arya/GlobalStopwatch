#include "../jni.h"
#include <windows.h>
#include <stdio.h>
#include "src_ActiveWindow.h"

JNIEXPORT jstring JNICALL Java_src_ActiveWindow_getActiveWindowTitle(JNIEnv *env, jobject obj) {
    char windowTitle[256];
    HWND hwnd = GetForegroundWindow();
    if (hwnd) {
        GetWindowText(hwnd, windowTitle, sizeof(windowTitle));
        return (*env)->NewStringUTF(env, windowTitle);
    }
    return (*env)->NewStringUTF(env, "");
}
