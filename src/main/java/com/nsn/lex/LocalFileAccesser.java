/*
 * Copyright (c) 2016 Nokia Solutions and Networks. All rights reserved.
 */

package com.nsn.lex;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author Lex Li
 * @date 27/09/2016
 */
public class LocalFileAccesser
{
    public static URL getFile()
    {
        try
        {
            Enumeration<URL> filePaths = LocalFileAccesser.class.getClassLoader().getResources( "nio-data.txt" );
            if( filePaths.hasMoreElements() )
            {
                return filePaths.nextElement();

            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        return null;
    }
}
