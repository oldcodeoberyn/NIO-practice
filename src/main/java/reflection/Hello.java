/*
 * Copyright (c) 2016 Nokia Solutions and Networks. All rights reserved.
 */

package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

interface China{
    public static final String name="Rollen";
    public static  int age=20;
    public void sayChina();
    public void sayHello(String name, int age);
}

class Person implements China{
    public Person() {

    }
    public Person(String sex){
        this.sex=sex;
    }
    private String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public void sayChina(){
        System.out.println("hello ,china");
    }
    public void sayHello(String name, int age){
        System.out.println(name+"  "+age);
    }
    private String sex;
}

/**
 * @author Lex Li
 * @date 29/09/2016
 */
public class Hello
{
    public static void main(String[] args) {
        Class<?> demo=null;
        try{
            demo=Class.forName("reflection.Person");
            Constructor pCo = demo.getConstructor( );
            Class superClz = demo.getSuperclass();
            Person jetty = (Person)pCo.newInstance( );

            Field[] demoFields = demo.getDeclaredFields();
            Method[] demoDeclaredMethods = demo.getDeclaredMethods();
            for( int i = 0; i < demoFields.length; i++ )
            {

                if( (demoFields[i].getModifiers() & Modifier.PRIVATE) != 0 )
                {
                    demoFields[i].setAccessible( true );
                    System.out.println( "private: " + demoFields[i].getName() );
                    demoFields[i].set( jetty, "male" );

                }
                else
                {
                    System.out.println( "other: " +  demoFields[i].getName() );
                }

            }
            for( int i = 0; i < demoDeclaredMethods.length; i++ )
            {
                if( (demoDeclaredMethods[i].getModifiers() & Modifier.PRIVATE) != 0 )
                {
                    demoDeclaredMethods[i].setAccessible( true );
                    System.out.println( "private: " + demoDeclaredMethods[i].getName() );
                    System.out.println( "call it: " + demoDeclaredMethods[i].invoke( jetty ) );

                }
                else
                {
                    System.out.println( "other: " +  demoDeclaredMethods[i].getName() );
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

