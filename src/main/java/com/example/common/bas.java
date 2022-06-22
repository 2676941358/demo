package com.example.common;


public class bas {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
    public static void setCurrentId(long id){
        threadLocal.set(id);
    }
    public static long getCurrentId(){
        return threadLocal.get() ;
    }
}
