/*
 * Copyright (c) 2016 Nokia Solutions and Networks. All rights reserved.
 */

package com.nsn.lex;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;

/**
 * @author Lex Li
 * @date 19/09/2016
 */
public class MyFileChannel
{

    public static void main( String[] args ) throws IOException
    {
        RandomAccessFile aFile = null;

        aFile = new RandomAccessFile( LocalFileAccesser.getFile().getPath(), "rw" );

        FileChannel inChannel = aFile.getChannel();

        // create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate( 1000 );

        int bytesRead = inChannel.read( buf ); // read into buffer.
        char[] charContents = new char[48];
        List<String> fileContents = new ArrayList<String>();
        while( bytesRead != -1 )
        {

            buf.flip(); // make buffer ready for read
            int i = 0;
            while( buf.hasRemaining() )
            {
                charContents[i] = (char)buf.get();
                if( charContents[i] == '\r' && (char)buf.get() == '\n' )
                {
                    String temp = new String( charContents, 0, i );
                    if( !temp.isEmpty())
                        fileContents.add( temp );
                    charContents[i] = 0;
                    i = 0;
                }
                else
                {
                    i++;
                }
            }

            for( String s : fileContents )
            {
                System.out.println( s );
            }

            String packageName = MyFileChannel.class.getPackage().getName();
            List<OperationType> castedValues = new ArrayList<OperationType>();
            for( String line : fileContents )
            {

                String enumType = line.split( "\\." )[0];
                String enumValue = line.split( "\\." )[1].toLowerCase();

                try
                {
                    Class<?> f = Class.forName( packageName + "." + enumType );
                    Object[] enumConstants = f.getEnumConstants();
                    for( int j = 0; j < enumConstants.length; j++ )
                    {
                        String v = (String)enumConstants[j]
                                        .getClass().getMethod( "getValue" ).invoke( enumConstants[j] );
                        if( v.equals( enumValue ) )
                        {
                            castedValues.add( (OperationType)f.getEnumConstants()[j] );
                            break;
                        }
                    }
                }
                catch( ClassNotFoundException e )
                {
                    e.printStackTrace();
                }
                catch( NoSuchMethodException e )
                {
                    e.printStackTrace();
                }
                catch( InvocationTargetException e )
                {
                    e.printStackTrace();
                }
                catch( IllegalAccessException e )
                {
                    e.printStackTrace();
                }
            }

            System.out.println( castedValues );

            ImmutableList<OperationType> finishedStatus =
                            ImmutableList.copyOf( Collections2.filter( castedValues, new Predicate<OperationType>()
                            {
                                public boolean apply( OperationType operationType )
                                {
                                    return !operationType.equals( OperationType.ONGOING );
                                }
                            } ) );
            ImmutableList<String> finishStatusInString = ImmutableList.copyOf( Collections2.transform(
                            finishedStatus, new Function<OperationType, String>()
                            {
                                public String apply( OperationType operationType )
                                {
                                    return operationType.getValue();
                                }
                            } ) );

            System.out.println( finishedStatus );
            System.out.println( finishStatusInString );

            buf.clear(); // make buffer ready for writing
            bytesRead = inChannel.read( buf );
        }

        aFile.close();
    }
}
