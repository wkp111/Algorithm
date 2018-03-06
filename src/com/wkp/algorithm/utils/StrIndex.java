package com.wkp.algorithm.utils;

/**
 * 字符串查找 字符串匹配算法
 */
public class StrIndex {

    /**
     * KMP算法
     * @param target 长字符串
     * @param source 短字符串
     * @return
     */
    public static int indexOfKMP(String target, String source) {
        int i = 0, j = 0;
        int[] next = next(source);
        while (i < target.length() && j < source.length()) {
            if (j == -1 || target.charAt(i) == source.charAt(j)) {
                i++;
                j++;
            }else {
                j = next[j];
            }
        }
        return j == source.length() ? i - j : -1;
    }

    /**
     * KMP算法 next数组 根据最大长度数组推算
     * @param source
     * @return
     */
    private static int[] next(String source) {
        int[] next = new int[source.length()];
        int k = -1, j = 0;
        next[0] = -1;   //初始值为-1
        while (j < source.length() - 1) {
            if (k == -1 || source.charAt(k) == source.charAt(j)) {
                k++;
                j++;
                next[j] = source.charAt(k) == source.charAt(j) ? next[k] : k;
            }else {
                k = next[k];
            }
        }
        return next;
    }


    /*--------------------------------------------------------------分割线-------------------------------------------------------------------------*/


    /**
     * BM算法 效率比KMP算法高3-5倍
     * @param target
     * @param source
     * @return
     */
    public static int indexOfBM(String target, String source) {
        int[] bc = getBadChar(source);
        int[] gs = getGoodSuf(source);
        int sp = 0, cp;
        while (sp + source.length() <= target.length()) {
            for (cp = source.length() - 1; cp >= 0 && source.charAt(cp) == target.charAt(sp + cp); --cp);
            if (cp == -1) {
                break;
            }else {
                sp += Math.max(gs[cp], cp - bc[target.charAt(sp + cp)]);
            }
        }
        return (sp + source.length() <= target.length()) ? sp : -1;
    }

    /**
     * BM算法 获取坏字符表
     * @param source
     * @return
     */
    private static int[] getBadChar(String source) {
        int[] bc = new int[256];
        int length = source.length();
        for (int i = 0; i < 256; i++) {
            bc[i] = -1;
        }
        for (int i = 0; i < length; i++) {
            bc[source.charAt(i)] = i;
        }
        return bc;
    }

    /**
     * BM算法 后缀表
     * @param source
     * @return
     */
    private static int[] suffixes(String source) {
        int length = source.length();
        int num = 0;
        int[] suf = new int[length];
        suf[length - 1] = length;
        for (int i = length - 2; i >= 0; --i) {
            for (num = 0; num <= i && source.charAt(i - num) == source.charAt(length - num - 1); ++num);
            suf[i] = num;
        }
        return suf;
    }

    /**
     * BM算法 获取好后缀表
     * @param source
     * @return
     */
    private static int[] getGoodSuf(String source) {
        int length = source.length();
        int lastIndex = length - 1;
        int[] suf = suffixes(source);
        int[] gs = new int[length];
        //情况1 找不到对应的子串和前缀
        for (int i = 0; i < length; i++)
            gs[i] = length;
        //情况2 找对应前缀
        for (int i = lastIndex; i >= 0; --i) {
            if (suf[i] == i + 1) {
                for (int j = 0; j < lastIndex - i; j++) {
                    if (gs[j] == length) {
                        gs[j] = lastIndex - i;
                    }
                }
            }
        }
        //情况3 找中间的匹配子串
        for (int i = 0; i < lastIndex; i++) {
            gs[lastIndex - suf[i]] = lastIndex - i;
        }

        return gs;
    }


    /*--------------------------------------------------------------分割线-------------------------------------------------------------------------*/


    /**
     * Sunday算法 效率更高
     * @param target
     * @param source
     * @return
     */
    public static int indexOfSunday(String target, String source) {
        int tLen = target.length();
        int sLen = source.length();
        int[] map = new int[256];
        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }
        for (int i = 0; i < sLen; i++) {
            map[source.charAt(i)] = i;
        }
        for (int i = 0; i < tLen - sLen;) {
            int j = 0;
            while (j < sLen) {
                if (target.charAt(i) == source.charAt(j)) {
                    i++;
                    j++;
                }else {
                    int index = i + sLen - j;
                    int mapIndex = map[target.charAt(index)];
                    i = mapIndex == -1 ? index + 1 : index - mapIndex;
                    break;
                }
            }

            if (j == sLen) {
                return i - sLen;
            }
        }
        return -1;
    }
}
