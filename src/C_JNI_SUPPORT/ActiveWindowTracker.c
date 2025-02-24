#include <windows.h>
#include "../jni.h"
#include <stdio.h>
#include "src_ActiveWindowTracker.h"

static HWINEVENTHOOK hook;
static JavaVM *jvm;
static jobject globalCallback; // To call Java method

void CALLBACK WinEventProc(HWINEVENTHOOK hWinEventHook, DWORD event, HWND hwnd, 
    LONG idObject, LONG idChild, DWORD dwEventThread, DWORD dwmsEventTime) {
if (idObject != OBJID_WINDOW || idChild != CHILDID_SELF) return;

DWORD pid;
GetWindowThreadProcessId(hwnd, &pid);

char title[256];
GetWindowTextA(hwnd, title, sizeof(title));

// Get JNI environment
JNIEnv *env;
if ((*jvm)->AttachCurrentThread(jvm, (void **)&env, NULL) != JNI_OK) {
printf("Failed to attach thread to JVM\n");
return;
}

// Get Java method ID
jclass cls = (*env)->GetObjectClass(env, globalCallback);
jmethodID methodID = (*env)->GetMethodID(env, cls, "onWindowChange", "(ILjava/lang/String;)V");

if (methodID) {
jstring jTitle = (*env)->NewStringUTF(env, title);
(*env)->CallVoidMethod(env, globalCallback, methodID, (jint)pid, jTitle);
(*env)->DeleteLocalRef(env, jTitle);
} else {
printf("Java method not found!\n");
}
}

// Starts the hook
JNIEXPORT void JNICALL Java_src_ActiveWindowTracker_nativeStartHook(JNIEnv *env, jobject obj) {
(*env)->GetJavaVM(env, &jvm);  // Store JVM reference
globalCallback = (*env)->NewGlobalRef(env, obj); // Create a global reference

hook = SetWinEventHook(EVENT_SYSTEM_FOREGROUND, EVENT_SYSTEM_FOREGROUND, NULL, 
    WinEventProc, 0, 0, WINEVENT_OUTOFCONTEXT);

if (!hook) {
printf("Failed to set hook!\n");
return;
}

MSG msg;
while (GetMessage(&msg, NULL, 0, 0)) {
TranslateMessage(&msg);
DispatchMessage(&msg);
}
}

// Stops the hook
JNIEXPORT void JNICALL Java_src_ActiveWindowTracker_nativeStopHook(JNIEnv *env, jobject obj) {
if (hook) {
UnhookWinEvent(hook);
hook = NULL;
printf("Hook removed.\n");
}

if (globalCallback) {
(*env)->DeleteGlobalRef(env, globalCallback);
globalCallback = NULL;
}
}