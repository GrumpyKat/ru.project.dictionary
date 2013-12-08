package ru.project.dictionary;

import java.util.*;

public class Dictionary 
{   
    public static HashMap<String, Object> dictRuEng = new HashMap<>();
    public static HashMap<String, Object> dictEngRu = new HashMap<>();
    
    public void getAllWordsToSee(HashMap<String, Object> srcHashMap)
    {
        Set<Map.Entry<String, Object>> set = srcHashMap.entrySet();
        for (Map.Entry<String, Object> me : set)
        {
            System.out.println(me.getKey() + ": " + me.getValue());
        }
    }
    
    public static Object getWordFromDict(int type, String key)
    {
        if (type == 0)
        {
            return dictRuEng.get(key);
        }
        if (type == 1)
        {
            return dictEngRu.get(key);
        }
        else return null;
    }
    
    public static void putWordToDict(int type, String key, Object word) throws Exception
    {
        if (type == 0)
        {
            dictRuEng.put(key, word);
            DAO.doSerialize(type, word);
        }
        else if (type == 1)
        {
            dictEngRu.put(key, word);
            DAO.doSerialize(type, word);
        }
    }
    
    public static boolean isInDict(int type, String key)
    {
        if (type == 0)
            return dictRuEng.containsKey(key);
        else if (type == 1)
            return dictEngRu.containsKey(key);
        else 
        {
            System.out.println("I don't know how you came here");
            return false;
        }
    }
    
    public static HashSet<String> wordsStartWith(HashMap<String, Object> srcHashMap, String inputString)  //ищу ключи, начинающиеся с тех же слов, что и исходный ключ
    {
        HashSet<String> hs = new HashSet<>();
        Scanner scanInputString = new Scanner(inputString);
        String begin;
        String exmp;
        Set<Map.Entry<String, Object>> set = srcHashMap.entrySet();
        while (scanInputString.hasNext())             //просто разит от того, как это не оптимизировано)
        {                                             //но в принципе кол-во слов в строке 1-2, не больше
            begin = scanInputString.next();
            begin = begin.toLowerCase();
            for (Map.Entry<String, Object> me : set)
            {
                Scanner scanKey = new Scanner(me.getKey());
                while (scanKey.hasNext())
                {
                    exmp = scanKey.next();
                    exmp = exmp.toLowerCase();
                    if (exmp.startsWith(begin))
                    {
                        hs.add(me.getKey());
                    }
                }
            }
        }
        return hs;
    }
    
    public static TreeMap<String, Object> sortKeys(HashMap<String, Object> srcHashMap)
    {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        Set<Map.Entry<String, Object>> set = srcHashMap.entrySet();
        for (Map.Entry<String, Object> mapEntry : set)
        {
            treeMap.put(mapEntry.getKey(), mapEntry.getValue());
        }
        return treeMap;
    }
}
