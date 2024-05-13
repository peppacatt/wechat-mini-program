package com.peppacatt.wechat.entity.button;

import lombok.Data;

/**
 * 按钮抽象类
 */
@Data
public abstract class AbstractButton {
    private String type;
    private String name;
}
