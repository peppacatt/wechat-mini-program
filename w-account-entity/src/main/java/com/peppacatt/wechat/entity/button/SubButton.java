package com.peppacatt.wechat.entity.button;

import lombok.Data;

import java.util.List;

/**
 * 二级菜单按钮
 */
@Data
public class SubButton {
    private String name;
    private List<AbstractButton> buttons;
}
