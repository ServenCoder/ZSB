package com.serven.zsb.toolkit.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by zhangjiayuan
 * Date: 2018/11/27
 */
public class SimpleLunarCalendar {
    private static final long minTimeInMillis = -2206425952001L;
    private static final long maxTimeInMillis = 4102416000000L;
    private static final int[] lunarInfo = new int[]{19416, 19168, 42352, 21717, 53856, 55632, 21844, 22191, 39632, 21970, 19168, 42422, 42192, 53840, 53909, 46415, 54944, 44450, 38320, 18807, 18815, 42160, 46261, 27216, 27968, 43860, 11119, 38256, 21234, 18800, 25958, 54432, 59984, 27285, 23263, 11104, 34531, 37615, 51415, 51551, 54432, 55462, 46431, 22176, 42420, 9695, 37584, 53938, 43344, 46423, 27808, 46416, 21333, 19887, 42416, 17779, 21183, 43432, 59728, 27296, 44710, 43856, 19296, 43748, 42352, 21088, 62051, 55632, 23383, 22176, 38608, 19925, 19152, 42192, 54484, 53840, 54616, 46400, 46752, 38310, 38335, 18864, 43380, 42160, 45690, 27216, 27968, 44870, 43872, 38256, 19189, 18800, 25776, 29859, 59984, 27480, 23232, 43872, 38613, 37600, 51552, 55636, 54432, 55888, 30034, 22176, 43959, 9680, 37584, 51893, 43344, 46240, 47780, 44368, 21977, 19360, 42416, 20854, 21183, 43312, 31060, 27296, 44368, 23378, 19296, 42726, 42208, 53856, 60005, 54576, 23200, 30371, 38608, 19195, 19152, 42192, 53430, 53855, 54560, 56645, 46496, 22224, 21938, 18864, 42359, 42160, 43600, 45653, 27951, 44448, 19299, 37759, 18936, 18800, 25776, 26790, 59999, 27424, 42692, 43759, 37600, 53987, 51552, 54615, 54432, 55888, 23893, 22176, 42704, 21972, 21200, 43448, 43344, 46240, 46758, 44368, 21920, 43940, 42416, 21168, 45683, 26928, 29495, 27296, 44368, 19285, 19311, 42352, 21732, 53856, 59752, 54560, 55968, 27302, 22239, 19168, 43476, 42192, 53584, 62034, 54560};
    private static final String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    private static final String[] lunarString1 = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final String[] lunarString2 = new String[]{"初", "十", "廿", "卅", "正", "腊", "冬", "闰"};
    private int lunarYear;
    private int lunarMonth;
    private int lunarDay;
    private boolean isLeap;
    private boolean isLeapYear;
    private int maxDayInMonth = 29;

    public SimpleLunarCalendar(long TimeInMillis) {
        this.init(TimeInMillis);
    }

    public SimpleLunarCalendar(Date date) {
        if (date == null) {
            date = new Date();
        }

        this.init(date.getTime());
    }

    private void init(long timeInMillis) {
        if (timeInMillis > -2206425952001L && timeInMillis < 4102416000000L) {
            Calendar baseDate = new GregorianCalendar(1900, 0, 31);
            long offset = (timeInMillis - baseDate.getTimeInMillis()) / 86400000L;
            this.lunarYear = 1900;

            for(int daysInLunarYear = getLunarYearDays(this.lunarYear); this.lunarYear < 2100 && offset >= (long)daysInLunarYear; daysInLunarYear = getLunarYearDays(++this.lunarYear)) {
                offset -= (long)daysInLunarYear;
            }

            int leapMonth = getLunarLeapMonth(this.lunarYear);
            this.isLeapYear = leapMonth > 0;
            int lunarMonth = 1;
            boolean isDecrease = true;
            boolean isLeap = false;
            int daysInLunarMonth = 0;

            label62:
            while(true) {
                while(true) {
                    if (lunarMonth >= 13 || offset <= 0L) {
                        break label62;
                    }

                    if (isLeap && !isDecrease) {
                        daysInLunarMonth = getLunarLeapDays(this.lunarYear);
                        isDecrease = true;
                    } else {
                        daysInLunarMonth = getLunarMonthDays(this.lunarYear, lunarMonth);
                    }

                    if (offset < (long)daysInLunarMonth) {
                        break label62;
                    }

                    offset -= (long)daysInLunarMonth;
                    if (leapMonth == lunarMonth && !isLeap) {
                        isDecrease = false;
                        isLeap = true;
                    } else {
                        ++lunarMonth;
                    }
                }
            }

            this.maxDayInMonth = daysInLunarMonth != 0 ? daysInLunarMonth : getLunarMonthDays(this.lunarYear, lunarMonth);
            this.lunarMonth = lunarMonth;
            this.isLeap = lunarMonth == leapMonth && isLeap;
            this.lunarDay = (int)offset + 1;
        }

    }

    private static int getLunarYearDays(int lunarYear) {
        int daysInLunarYear = 348;

        for(int i = 32768; i > 8; i >>= 1) {
            daysInLunarYear += (lunarInfo[lunarYear - 1900] & i) != 0 ? 1 : 0;
        }

        daysInLunarYear += getLunarLeapDays(lunarYear);
        return daysInLunarYear;
    }

    private static int getLunarLeapDays(int lunarYear) {
        return getLunarLeapMonth(lunarYear) > 0 ? ((lunarInfo[lunarYear - 1899] & 15) == 15 ? 30 : 29) : 0;
    }

    private static int getLunarLeapMonth(int lunarYear) {
        int leapMonth = lunarInfo[lunarYear - 1900] & 15;
        leapMonth = leapMonth == 15 ? 0 : leapMonth;
        return leapMonth;
    }

    private static int getLunarMonthDays(int lunarYear, int lunarMonth) {
        int daysInLunarMonth = (lunarInfo[lunarYear - 1900] & 65536 >> lunarMonth) != 0 ? 30 : 29;
        return daysInLunarMonth;
    }

    private static String getLunarYearString(int lunarYear) {
        String lunarYearString = "";
        String year = String.valueOf(lunarYear);

        for(int i = 0; i < year.length(); ++i) {
            char yearChar = year.charAt(i);
            int index = Integer.parseInt(String.valueOf(yearChar));
            lunarYearString = lunarYearString + lunarString1[index];
        }

        return lunarYearString;
    }

    private static String getLunarMonthString(int lunarMonth) {
        String lunarMonthString = "";
        if (lunarMonth == 1) {
            lunarMonthString = lunarString2[4];
        } else {
            if (lunarMonth > 9) {
                lunarMonthString = lunarMonthString + lunarString2[1];
            }

            if (lunarMonth % 10 > 0) {
                lunarMonthString = lunarMonthString + lunarString1[lunarMonth % 10];
            }
        }

        return lunarMonthString;
    }

    private static String getLunarDayString(int lunarDay) {
        if (lunarDay >= 1 && lunarDay <= 30) {
            int i1 = lunarDay / 10;
            int i2 = lunarDay % 10;
            String c1 = lunarString2[i1];
            String c2 = lunarString1[i2];
            if (lunarDay < 11) {
                c1 = lunarString2[0];
            }

            if (i2 == 0) {
                c2 = lunarString2[1];
            }

            return c1 + c2;
        } else {
            return "";
        }
    }

    public String getAnimalString() {
        return this.lunarYear == 0 ? null : Animals[(this.lunarYear - 4) % 12];
    }

    public String getDayString() {
        return this.lunarDay == 0 ? null : getLunarDayString(this.lunarDay);
    }

    public String getMonthString() {
        return this.lunarMonth == 0 ? null : (this.isLeap() ? "闰" : "") + getLunarMonthString(this.lunarMonth);
    }

    public String getYearString() {
        return this.lunarYear == 0 ? null : getLunarYearString(this.lunarYear);
    }

    public String getDateString() {
        return this.lunarYear == 0 ? null : this.getYearString() + "年" + this.getMonthString() + "月" + this.getDayString() + "日";
    }

    public boolean isLeap() {
        return this.isLeap;
    }

    public boolean isLeapYear() {
        return this.isLeapYear;
    }

    public boolean isBigMonth() {
        return this.getMaxDayInMonth() > 29;
    }

    public int getMaxDayInMonth() {
        return this.lunarYear == 0 ? 0 : this.maxDayInMonth;
    }

    public int getDay() {
        return this.lunarDay;
    }

    public int getMonth() {
        return this.lunarMonth;
    }

    public int getYear() {
        return this.lunarYear;
    }
}
