#include <pthread.h>
#include <string.h>
#include <stdio.h>
#include <ctype.h>
#include <fcntl.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <jni.h>
#include <android/log.h>

#define DEBUG_TAG "NATIVE_HID"
#define BUF_LEN 512

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, DEBUG_TAG, __VA_ARGS__))
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, DEBUG_TAG, __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, DEBUG_TAG, __VA_ARGS__))

int sendToDevice(char device, char buf[BUF_LEN]);

struct options {
	const char    *opt;
	unsigned char val;
};

static struct options kmod[] = {
		{.opt = "--left-ctrl",		.val = 0x01},
		{.opt = "--right-ctrl",		.val = 0x10},
		{.opt = "--left-shift",		.val = 0x02},
		{.opt = "--right-shift",	.val = 0x20},
		{.opt = "--left-alt",		.val = 0x04},
		{.opt = "--right-alt",		.val = 0x40},
		{.opt = "--left-meta",		.val = 0x08},
		{.opt = "--right-meta",		.val = 0x80},
		{.opt = NULL}
};

static struct options kval[] = {
		{.opt = "1",	.val = 0x1E},
		{.opt = "2",	.val = 0x1F},
		{.opt = "3",	.val = 0x20},
		{.opt = "4",	.val = 0x21},
		{.opt = "5",	.val = 0x22},
		{.opt = "6",	.val = 0x23},
		{.opt = "7",	.val = 0x24},
		{.opt = "8",	.val = 0x25},
		{.opt = "9",	.val = 0x26},
		{.opt = "0",	.val = 0x27},
		{.opt = "--return",	.val = 0x28},
		{.opt = "--esc",	.val = 0x29},
		{.opt = "--bckspc",	.val = 0x2a},
		{.opt = "--tab",	.val = 0x2b},
		{.opt = "--spacebar",	.val = 0x2c},
		{.opt = "--caps-lock",	.val = 0x39},
		{.opt = "--f1",		.val = 0x3a},
		{.opt = "--f2",		.val = 0x3b},
		{.opt = "--f3",		.val = 0x3c},
		{.opt = "--f4",		.val = 0x3d},
		{.opt = "--f5",		.val = 0x3e},
		{.opt = "--f6",		.val = 0x3f},
		{.opt = "--f7",		.val = 0x40},
		{.opt = "--f8",		.val = 0x41},
		{.opt = "--f9",		.val = 0x42},
		{.opt = "--f10",	.val = 0x43},
		{.opt = "--f11",	.val = 0x44},
		{.opt = "--f12",	.val = 0x45},
		{.opt = "--insert",	.val = 0x49},
		{.opt = "--home",	.val = 0x4a},
		{.opt = "--pageup",	.val = 0x4b},
		{.opt = "--del",	.val = 0x4c},
		{.opt = "--end",	.val = 0x4d},
		{.opt = "--pagedown",	.val = 0x4e},
		{.opt = "--right",	.val = 0x4f},
		{.opt = "--left",	.val = 0x50},
		{.opt = "--down",	.val = 0x51},
		{.opt = "--kp-enter",	.val = 0x58},
		{.opt = "--up",		.val = 0x52},
		{.opt = "--num-lock",	.val = 0x53},
		{.opt = "--minus",	.val = 0x2d},
		{.opt = "--plus",	.val = 0x57},
		{.opt = "--comma",	.val = 0x36},
		{.opt = "--period",	.val = 0x37},
		{.opt = "--slash", .val = 0x38},
		{.opt = "--backslash", .val = 0x31},
		{.opt = "--equal", .val = 0x2e},
		{.opt = "--asterisk", .val = 0x55},
		{.opt = "--left-brace", .val = 0x2f},
		{.opt = "--right-brace", .val = 0x30},
		{.opt = "--tilde", .val = 0x35},
		{.opt = "--semicolon", .val = 0x33},
		{.opt = "--tick", .val = 0x34},
		{.opt = NULL}
};

static struct options mmod[] = {
		{.opt = "--b1", .val = 0x01},
		{.opt = "--b2", .val = 0x02},
		{.opt = "--b3", .val = 0x04},
		{.opt = NULL}
};


void Java_com_keepassdroid_hid_Hid_nativeSendCommandToKeyboard(JNIEnv* env, jobject thiz, jstring string) {
	char * sz = (*env)->GetStringUTFChars(env, string, NULL);
	//LOGD(sz);
	int result = sendToDevice('k',sz);
	//char mess[50];
	//sprintf(mess,"sendToKeyboard result: %d",result);
	//LOGI(mess);
	(*env)->ReleaseStringUTFChars(env, string, sz);
}

void Java_com_keepassdroid_hid_Hid_nativeSendCommandToMouse(JNIEnv* env, jobject thiz, jstring string) {
	char * sz = (*env)->GetStringUTFChars(env, string, NULL);
	//LOGD(sz);
	int result = sendToDevice('m',sz);
	//char mess[50];
	//sprintf(mess,"sendToMouse result: %d",result);
	//LOGI(mess);
	(*env)->ReleaseStringUTFChars(env, string, sz);
}

int keyboard_fill_report(char report[8], char buf[BUF_LEN], int *hold)
{
	char *tok = strtok(buf, " ");
	int key = 0;
	int i = 0;

	for (; tok != NULL; tok = strtok(NULL, " ")) {

		if (strcmp(tok, "--quit") == 0)
			return -1;

		if (strcmp(tok, "--hold") == 0) {
			*hold = 1;
			continue;
		}

		if (key < 6) {
			for (i = 0; kval[i].opt != NULL; i++)
				if (strcmp(tok, kval[i].opt) == 0) {
					report[2 + key++] = kval[i].val;
					break;
				}
			if (kval[i].opt != NULL)
				continue;
		}

		if (key < 6)
			if (islower(tok[0])) {
				report[2 + key++] = (tok[0] - ('a' - 0x04));
				continue;
			}

		for (i = 0; kmod[i].opt != NULL; i++)
			if (strcmp(tok, kmod[i].opt) == 0) {
				report[0] = report[0] | kmod[i].val;
				break;
			}
		if (kmod[i].opt != NULL)
			continue;

		if (key < 6)
			LOGE("keyboard_fill_report error");
	}
	return 8;
}

int mouse_fill_report(char report[8], char buf[BUF_LEN], int *hold)
{
	char *tok = strtok(buf, " ");
	int mvt = 0;
	int i = 0;
	for (; tok != NULL; tok = strtok(NULL, " ")) {

		if (strcmp(tok, "--quit") == 0)
			return -1;

		if (strcmp(tok, "--hold") == 0) {
			*hold = 1;
			continue;
		}

		for (i = 0; mmod[i].opt != NULL; i++)
			if (strcmp(tok, mmod[i].opt) == 0) {
				report[0] = report[0] | mmod[i].val;
				break;
			}
		if (mmod[i].opt != NULL)
			continue;

		if (!(tok[0] == '-' && tok[1] == '-') && mvt < 2) {
			errno = 0;
			report[1 + mvt++] = (char)strtol(tok, NULL, 0);
			if (errno != 0) {
				report[1 + mvt--] = 0;
			}
			continue;
		}

	}
	return 3;
}

int sendToDevice(char device, char buf[BUF_LEN]) {
	//LOGD(buf);
	char *filename = NULL;
	int fd = 0;
	int cmd_len;
	char report[8];
	int to_send = 8;
	int hold = 0;

	if (device == 'k')
		filename = "/dev/hidg0";
	else if (device == 'm')
		filename = "/dev/hidg1";
	else
		return 1;

	//LOGD(filename);
	if ((fd = open(filename, O_RDWR, 0666)) == -1) {
			return 2;
	}
	hold = 0;

	memset(report, 0x0, sizeof(report));
	if (device == 'k')
		to_send = keyboard_fill_report(report, buf, &hold);
	else if (device == 'm')
		to_send = mouse_fill_report(report, buf, &hold);
	else
		return 3;
	if (to_send == -1)
		return 4;
	if (write(fd, report, to_send) != to_send) {
		return 5;
	}
	if (!hold) {
		memset(report, 0x0, sizeof(report));
		if (write(fd, report, to_send) != to_send) {
			return 6;
		}
	}
	close(fd);
	return 0;
}
