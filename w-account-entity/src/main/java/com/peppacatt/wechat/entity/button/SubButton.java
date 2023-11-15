package com.peppacatt.wechat.entity.button;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 二级菜单按钮
 */
public class SubButton extends AbstractButton {
    @Getter
    @Setter
    private List<AbstractButton> sub_button;
}
