package core;

public class KoboldSyntax {
    /**
     * C++ Keywords
     */
    public enum CPP {
        _asm(1),
        _auto(1),
        _bool(1),
        _char(1),
        _class(1),
        _const(1),
        _consteval(1),
        _constexpr(1),
        _constinit(1),
        _const_cat(1),
        _double(1),
        _dynamic_cast(1),
        _enum(1),
        _explicit(1),
        _export(1),
        _extern(1),
        _false(1),
        _float(1),
        _friend(1),
        _private(1),
        _protected(1),
        _public(1),
        _inline(1),
        _int(1),
        _long(1),
        _mutable(1),
        _nullptr(1),
        _namespace(1),
        _register(1),
        _reinterpret_cast(1),
        _requires(1),
        _short(1),
        _static(1),
        _static_cast(1),
        _template(1),
        _this(1),
        _thread_local(1),
        _true(1),
        _typedef(1),
        _unsigned(1),
        _virtual(1),
        _void(1),
        _volatile(1),
        _wchar_t(1),
        _struct(1),
        _union(1),

        _break(2),
        _case(2),
        _catch(2),
        _continue(2),
        _default(2),
        _delete(2),
        _do(2),
        _else(2),
        _for(2),
        _goto(2),
        _if(2),
        _new(2),
        _return(2),
        _switch(2),
        _synchronized(2),
        _throw(2),
        _try(2),
        _using(2),
        _while(2),
        ;

        private final int color;

        /**
         * Initializes the CPP enum with the color value.
         * @param color Color
         */
        CPP(int color) {
            this.color = color;
        }

        /**
         * Returns the color related to the keyword.
         * @return KoboldColors.Colors
         */
        public KoboldColors.Colors getColor() {
            if(this.color == 1)
                return KoboldColors.Colors.GREEN;
            else if(this.color == 2)
                return KoboldColors.Colors.RED;

            return KoboldColors.Colors.WHITE;
        }
    }
}
