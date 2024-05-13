package com.peppacatt.wechat.entity.button;

import lombok.Getter;
import lombok.Setter;

/**
 * click类型按钮
 */
public class ClickButton extends AbstractButton {
    @Getter
    @Setter
    private String key;

    public ClickButton() {
        this.setType("click");
    }
}
