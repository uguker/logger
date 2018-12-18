package com.uguke.java.logger;

/**
 * 功能描述：制表工具
 * @author LeiJue
 * @date 2018/12/18
 */
public enum  Table {
    /** **/
    DOUBLE("╔", "╗", "╚", "╝", "╠", "╣", "═", "║", "═", "\u3000\u3000"),
    SINGLE("┌", "┐", "└", "┘", "├", "┤", "─", "│", "─", "\u3000\u3000"),
    SINGLE2("┏", "┓", "┗", "┛", "┣", "┫", "━", "┃", "━", "\u3000\u3000");

    String mTL;
    String mTR;
    String mBL;
    String mBR;
    String mDL;
    String mDR;
    String mDM;
    String mBV;
    String mBH;
    String mIndent;
    StringBuilder mBuilder;

    Table(String tl, String tr, String bl, String br,
                 String dl, String dr, String dm, String bv,
                 String bh, String indent) {
        this.mTL = tl;
        this.mTR = tr;
        this.mBL = bl;
        this.mBR = br;
        this.mDL = dl;
        this.mDR = dr;
        this.mDM = dm;
        this.mBV = bv;
        this.mBH = bh;
        this.mIndent = indent;
        this.mBuilder = new StringBuilder();
    }

    public static Table getDouble() {
        return DOUBLE;
    }

    public static Table getSingle() {
        return SINGLE;
    }

    public String getTopLeft() {
        return mTL;
    }

    public String getTopRight() {
        return mTR;
    }

    public String getBottomLeft() {
        return mBL;
    }

    public String getBottomRight() {
        return mBR;
    }

    public String getDividerLeft() {
        return mDL;
    }

    public String getDividerRight() {
        return mDR;
    }

    public String getDividerMiddle() {
        return mDM;
    }

    public String getBV() {
        return mBV;
    }

    public String getBorderHorizontal() {
        return mBH;
    }


    public String getIndent() {
        return mIndent;
    }

    public String getTopBorder(int length) {
        mBuilder.setLength(0);
        for (int i = 0; i < length * 2 - 2; i++) {
            mBuilder.append(mBH);
        }
        return mTL + mBuilder.toString() + mTR;
    }

    public String getBottomBorder(int length) {
        mBuilder.setLength(0);
        for (int i = 0; i < length * 2 - 2; i++) {
            mBuilder.append(mBH);
        }
        return mBL + mBuilder.toString() + mBR;
    }

    public String getContentDivider(int length) {
        mBuilder.setLength(0);
        for (int i = 0; i < length * 2 - 2; i++) {
            mBuilder.append(mBH);
        }
        return mDL + mBuilder.toString() + mDR;
    }
}
