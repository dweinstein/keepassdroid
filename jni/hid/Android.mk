LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := hid
LOCAL_SRC_FILES := hid.c
LOCAL_LDLIBS 	:= -llog

include $(BUILD_SHARED_LIBRARY)
