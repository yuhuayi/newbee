/*
 * Copyright 1999-2101 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.fastjson.parser;

/**
 * @author wenshao<szujobs@hotmail.com>
 */
public enum Feature {
    /**
	 * 自动关闭源
	 */
    AutoCloseSource,
    /**
	 * 允许发表评论
	 */
    AllowComment,
    /**
	 * 允许单引号
	 */
    AllowUnQuotedFieldNames,
    /**
	 * 
	 */
    AllowSingleQuotes,
    /**
	 * 
	 */
    InternFieldNames,
    /**
	 * 允许ISO8601日期格式
	 */
    AllowISO8601DateFormat,

    /**
     * {"a":1,,,"b":2}
     * 允许任意的逗号
     */
    AllowArbitraryCommas,

    /**
     * 
     */
    UseBigDecimal,
    
    /**
     * @since 1.1.2
     * 忽略不匹配
     */
    IgnoreNotMatch,

    /**
     * @since 1.1.3
     * 排序Feid快速匹配,
     */
    SortFeidFastMatch,
    
    /**
     * @since 1.1.3
     * 禁用ASM
     */
    DisableASM,
    
    /**
     * @since 1.1.7
     * 禁用循环引用检测
     */
    DisableCircularReferenceDetect,
    
    /**
     * @since 1.1.10
     * 初始化字符串字段为空
     */
    InitStringFieldAsEmpty
    ;

    private Feature(){
        mask = (1 << ordinal());
    }

    private final int mask;

    public final int getMask() {
        return mask;
    }

    public static boolean isEnabled(int features, Feature feature) {
        return (features & feature.getMask()) != 0;
    }

    public static int config(int features, Feature feature, boolean state) {
        if (state) {
            features |= feature.getMask();
        } else {
            features &= ~feature.getMask();
        }

        return features;
    }
}
