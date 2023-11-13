package com.peppacatt.wechat.entity.button;

import lombok.Getter;
import lombok.Setter;

/**
 * view类型按钮
 */
public class ViewButton extends AbstractButton {
    @Getter
    @Setter
    private String url;

    public ViewButton() {
        this.setType("view");
    }
}
