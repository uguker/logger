package com.uguke.logger.constant;

public class Table {

    public static final Table DOUBLE = new Table("╔", "╗", "╚", "╝", "╠", "╣", "═", "║", "═", "\u3000\u3000", 1.7f);
    public static final Table SINGLE = new Table("┌", "┐", "└", "┘", "├", "┤", "─", "│", "─", "\u3000\u3000", 1.7f);

    private String topLeft;
    private String topRight;
    private String bottomLeft;
    private String bottomRight;
    private String dividerLeft;
    private String dividerRight;
    private String dividerMiddle;
    private String borderVertical;
    private String borderHorizontal;
    private String indent;
    private float ratio;

    private StringBuilder builder;

    public Table(String topLeft, String topRight,
                 String bottomLeft, String bottomRight,
                 String dividerLeft, String dividerRight,
                 String dividerMiddle, String borderVertical,
                 String borderHorizontal, String indent, float ratio) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
        this.dividerLeft = dividerLeft;
        this.dividerRight = dividerRight;
        this.dividerMiddle = dividerMiddle;
        this.borderVertical = borderVertical;
        this.borderHorizontal = borderHorizontal;
        this.indent = indent;
        this.ratio = ratio;
        this.builder = new StringBuilder();
    }

    public static Table getDouble() {
        return DOUBLE;
    }

    public static Table getSingle() {
        return SINGLE;
    }

    public String getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(String topLeft) {
        this.topLeft = topLeft;
    }

    public String getTopRight() {
        return topRight;
    }

    public void setTopRight(String topRight) {
        this.topRight = topRight;
    }

    public String getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(String bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public String getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(String bottomRight) {
        this.bottomRight = bottomRight;
    }

    public String getDividerLeft() {
        return dividerLeft;
    }

    public void setDividerLeft(String dividerLeft) {
        this.dividerLeft = dividerLeft;
    }

    public String getDividerRight() {
        return dividerRight;
    }

    public void setDividerRight(String dividerRight) {
        this.dividerRight = dividerRight;
    }

    public String getDividerMiddle() {
        return dividerMiddle;
    }

    public void setDividerMiddle(String dividerMiddle) {
        this.dividerMiddle = dividerMiddle;
    }

    public String getBorderVertical() {
        return borderVertical;
    }

    public void setBorderVertical(String borderVertical) {
        this.borderVertical = borderVertical;
    }

    public String getBorderHorizontal() {
        return borderHorizontal;
    }

    public void setBorderHorizontal(String borderHorizontal) {
        this.borderHorizontal = borderHorizontal;
    }

    public String getIndent() {
        return indent;
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public String getTopBorder(int length, boolean equalLength) {
        builder.setLength(0);
        length = equalLength ? length : (int) (length * ratio);
        for (int i = 0; i < length; i++) {
            builder.append(borderHorizontal);
        }
        return topLeft + builder.toString() + topRight;
    }

    public String getBottomBorder(int length, boolean equalLength) {
        builder.setLength(0);
        length = equalLength ? length : (int) (length * ratio);
        for (int i = 0; i < length; i++) {
            builder.append(borderHorizontal);
        }
        return bottomLeft + builder.toString() + bottomRight;
    }

    public String getContentDivider(int length, boolean equalLength) {
        builder.setLength(0);
        length = equalLength ? length : (int) (length * ratio);
        for (int i = 0; i < length; i++) {
            builder.append(borderHorizontal);
        }
        return dividerLeft + builder.toString() + dividerRight;
    }
}
