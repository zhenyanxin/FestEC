package com.diabin.latte.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by kl on 2018/12/31.
 */

public enum  EcIcons implements Icon{
    icon_scan('\ue614'),
    icon_ali_pay('\ue64b');

    private char character;
    EcIcons(char character){
        this.character=character;
    }
    @Override
    public String key() {
        return name().replace('_','-');
    }

    @Override
    public char character() {
        return character;
    }
}
