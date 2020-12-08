package com.app.toolboxlibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtils {

    public static int backArrayListSize( ArrayList dataList ){
        int size = 0 ;
        if( null != dataList && dataList.size()>0){
            size = dataList.size() ;
        }
        return size ;
    }

    public static Object getDataFormPosition( ArrayList dataList , int pos ){
        Object size = null ;
        if( null != dataList && dataList.size()>0 && pos<dataList.size()){
            size = dataList.get(pos);
        }
        return size ;
    }

    public static int backArrayListSize( List dataList ){
        int size = 0 ;
        if( null != dataList && dataList.size()>0){
            size = dataList.size() ;
        }
        return size ;
    }

    public static Object getDataFormPosition( List dataList , int pos ){
        Object size = null ;
        if( null != dataList && dataList.size()>0 && pos<dataList.size()){
            size = dataList.get(pos);
        }
        return size ;
    }

    public static List<Object> arrayObjectToList(Object[] stringArray){
        List<Object> list =new ArrayList<>();
        if(null != stringArray){
            list = Arrays.asList(stringArray);
        }
        return list;
    }

    public static List<String> arrayStringToList(String[] stringArray){
        List<String> list =new ArrayList<>();
        if(null != stringArray){
            list = Arrays.asList(stringArray);
        }
        return list;
    }
}
