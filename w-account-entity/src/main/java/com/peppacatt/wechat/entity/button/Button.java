package com.peppacatt.wechat.entity.button;

import lombok.Data;

import java.util.List;

/**
 * 一级菜单按钮
 */
@Data
public class Button {
    private List<AbstractButton> buttons;
}
