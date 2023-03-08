package core;

public class KoboldSyntax {
    public enum CPP {
        _asm("blue"),
        _auto("blue"),
        _bool("blue"),
        _char("blue"),
        _class("blue"),
        _const("blue"),
        _consteval("blue"),
        _constexpr("blue"),
        _constinit("blue"),
        _const_cat("blue"),
        _double("blue"),
        _dynamic_cast("blue"),
        _enum("blue"),
        _explicit("blue"),
        _export("blue"),
        _extern("blue"),
        _false("blue"),
        _float("blue"),
        _friend("blue"),
        _private("blue"),
        _protected("blue"),
        _public("blue"),
        _inline("blue"),
        _int("blue"),
        _long("blue"),
        _mutable("blue"),
        _nullptr("blue"),
        _namespace("blue"),
        _register("blue"),
        _reinterpret_cast("blue"),
        _requires("blue"),
        _short("blue"),
        _static("blue"),
        _static_cast("blue"),
        _template("blue"),
        _this("blue"),
        _thread_local("blue"),
        _true("blue"),
        _typedef("blue"),
        _unsigned("blue"),
        _virtual("blue"),
        _void("blue"),
        _volatile("blue"),
        _wchar_t("blue"),
        _struct("blue"),
        _union("blue"),

        _char8_t("green"),
        _char16_t("green"),
        _char32_t("green"),
        _std("green"),
        _sizeof("green"),

        _break("pink"),
        _case("pink"),
        _catch("pink"),
        _continue("pink"),
        _default("pink"),
        _delete("pink"),
        _do("pink"),
        _else("pink"),
        _for("pink"),
        _goto("pink"),
        _if("pink"),
        _new("pink"),
        _return("pink"),
        _switch("pink"),
        _synchronized("pink"),
        _throw("pink"),
        _try("pink"),
        _using("pink"),
        _while("pink"),
        ;

        private String color;

        CPP(String color) {
            this.color = color;
        }

        public KoboldColors.Colors getColor() {
            if(this.color.equals("blue"))
                return KoboldColors.Colors.BLUE;
            else if(this.color.equals("green"))
                return KoboldColors.Colors.LIGHT_BLUE;
            else if(this.color.equals("pink"))
                return KoboldColors.Colors.PINK;

            return KoboldColors.Colors.LIGHT;
        }
    }
}
