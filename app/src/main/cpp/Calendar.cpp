#include"Calendar.h"
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<time.h>
#include<android/log.h>
#include<chinese-calendar.h>

#define TAG "Calendar.cpp"

#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__);
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,   TAG, __VA_ARGS__);
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,    TAG, __VA_ARGS__);
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,    TAG, __VA_ARGS__);
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,   TAG, __VA_ARGS__);
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,   TAG, __VA_ARGS__);

JNIEXPORT jstring JNICALL Java_com_fpliu_calendar_1for_1the_1aged_Calendar_getDate(JNIEnv *env, jclass clazz) {
    const char* week[] = {"日", "一", "二", "三", "四", "五", "六"};
    
    time_t tt = time(NULL);
    
    #ifdef DEBUG
        LOGI("tt = %ld\n", tt);
    #endif
    
    struct tm *tms = localtime(&tt);
    int year  = 1900 + tms->tm_year;
    int month = 1 + tms->tm_mon;
    int day   = tms->tm_mday;
    
    char *lunar = get_chinese_lunar_date(year, month, day);
    char *jieQi = get_chinese_jieqi(year, month, day);
    char *jieRi = get_chinese_festival3(year, month, day, lunar);
    
    #ifdef DEBUG
        LOGI("lunar = %s, jieQi = %s, jieRi = %s\n", lunar, jieQi, jieRi);
    #endif

    int jieQiLength;
    int jieRiLength;

    const char *jieQi2;
    const char *jieRi2;

    if (NULL == jieQi) {
        jieQiLength = 0;
        jieQi2 = "";
    } else {
        jieQiLength = strlen(jieQi);
        jieQi2 = jieQi;
    }

    if (NULL == jieRi) {
        jieRiLength = 0;
        jieRi2 = "";
    } else {
        jieRiLength = strlen(jieRi);
        jieRi2 = jieRi;
    }
    
    int a = sizeof("日");
    int length = 4 + 1 + 2 + 1 + 2 + 1 + 3 * a + 1 + 5 * a + 1 + jieQiLength + 1 +jieRiLength + 1;

    #ifdef DEBUG
        LOGI("a = %d, jieQiLength = %d, jieRiLength = %d, length = %d\n", a, jieQiLength, jieRiLength, length);
    #endif
    
    char str[length];
    memset(str, 0, length);
    sprintf(str, "%d|%d|%d|星期%s|%s|%s|%s", year, month, day, week[tms->tm_wday], lunar, jieQi2, jieRi2);

    if (NULL != lunar) {
        free(lunar);
    }

    if (NULL != jieQi) {
        free(jieQi);
    }
    
    if (NULL != jieRi) {
        free(jieRi);
    }
    
    #ifdef DEBUG
        LOGI("str = %s\n", str); 
    #endif

    return env->NewStringUTF(str);
}
