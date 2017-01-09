package javay.regex.expr;

public enum RTokenType {
    NORMAL, // 普通字符
    ESCAPE, // \
    SUBEXPR_BEGIN, // (
    SUBEXPR_END, // )
    CLASS_BEGIN, // [
    CLASS_END, // ]
}
