/*
 * Copyright (c) 2016 Nokia Solutions and Networks. All rights reserved.
 */

package com.nsn.lex;

/**
 * @author Lex Li
 * @date 28/09/2016
 */
public enum OperationType
{
    START("start"), ONGOING("ongoing"), FAILURE("failure"), SUCCESS("success");

    private String value;

    OperationType( String value )
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
