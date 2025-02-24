#include <windows.h>
#include "../jni.h"
#include <stdio.h>
#include "src_ActiveWindowTracker.h"

JavaVM *jvm;
jobject javaObject;

// Function to call Java method
void CallJavaMethod(HWND hwnd) {
    JNIEnv *env;
    if ((*jvm)->AttachCurrentThread(jvm, (void **)&env, NULL) != JNI_OK) {
        return;
    }

    DWORD processID;
    GetWindowThreadProcessId(hwnd, &processID);

    char windowTitle[256] = {0};
    GetWindowText(hwnd, windowTitle, sizeof(windowTitle));

    jclass cls = (*env)->GetObjectClass(env, javaObject);
    jmethodID methodID = (*env)->GetMethodID(env, cls, "onWindowChange", "(ILjava/lang/String;)V");

    if (methodID) {
        jstring jTitle = (*env)->NewStringUTF(env, windowTitle);
        (*env)->CallVoidMethod(env, javaObject, methodID, (jint)processID, jTitle);
        (*env)->DeleteLocalRef(env, jTitle);
    }
}

// Hook callback function
void CALLBACK WinEventProc(HWINEVENTHOOK hook, DWORD event, HWND hwnd, LONG idObject, LONG idChild, DWORD dwEventThread, DWORD dwmsEventTime) {
    if (event == EVENT_SYSTEM_FOREGROUND && hwnd != NULL) {
        CallJavaMethod(hwnd);
    }
}

// JNI function to start the hook
JNIEXPORT void JNICALL Java_src_ActiveWindowTracker_startHook(JNIEnv *env, jobject obj) {
    (*env)->GetJavaVM(env, &jvm);
    javaObject = (*env)->NewGlobalRef(env, obj);

    SetWinEventHook(EVENT_SYSTEM_FOREGROUND, EVENT_SYSTEM_FOREGROUND, NULL, WinEventProc, 0, 0, WINEVENT_OUTOFCONTEXT);

    // Keep message loop running
    MSG msg;
    while (GetMessage(&msg, NULL, 0, 0)) {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }
}
