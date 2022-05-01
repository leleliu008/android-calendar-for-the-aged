package com.fpliu.calendar_for_the_aged;

public class Calendar {

    static {
        System.loadLibrary("app");
    }

    /**
     * @return 示例：2020|3|15|星期一|闰二月廿三|立春|消费者权益日
     */
    public static native String getDate();
}
