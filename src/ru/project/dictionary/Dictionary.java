package ru.project.dictionary;

import java.util.*;

public class Dictionary 
{   
    public static DictionaryModel dictRuEng = new DictionaryModel();
    public static DictionaryModel dictEngRu = new DictionaryModel();
    
    public void getAllWordsToSee(HashMap<String, Word> srcHashMap)
    {
        Set<Map.Entry<String, Word>> set = srcHashMap.entrySet();
        for (Map.Entry<String, Word> me : set)
        {
            System.out.println(me.getKey() + ": " + me.getValue());
        }
    }
    
    public static Object getWordFromDict(int type, String key)
    {
        if (type == 0)
        {
            return dictRuEng.getKey(key);
        }
        if (type == 1)
        {
            return dictEngRu.getKey(key);
        }
        else return null;
    }
    
    public static void putWordToDict(int type, String key, Word word) throws Exception
    {
        if (type == 0)
        {
            dictRuEng.putKey(key, word);
            DAO.doSerialize(type, dictRuEng);
        }
        else if (type == 1)
        {
            dictEngRu.putKey(key, word);
            DAO.doSerialize(type, dictEngRu);
        }
    }
    
    public static boolean isInDict(int type, String key)
    {
        if (type == 0)
            return dictRuEng.containKey(key);
        else if (type == 1)
            return dictEngRu.containKey(key);
        else 
        {
            System.out.println("I don't know how you came here");
            return false;
        }
    }
    
    public static HashSet<String> wordsStartWith(DictionaryModel srcHashMap, String inputString)  //ищу ключи, начинающиеся с тех же слов, что и исходный ключ
    {
        HashSet<String> hs = new HashSet<>();
        Scanner scanInputString = new Scanner(inputString);
        String begin;
        String exmp;
        Set<Map.Entry<String, Word>> set = srcHashMap.showEntrySet();
        while (scanInputString.hasNext())             //просто разит от того, как это не оптимизировано)
        {                                             //но в принципе кол-во слов в строке 1-2, не больше
            begin = scanInputString.next();
            begin = begin.toLowerCase();
            for (Map.Entry<String, Word> me : set)
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
    
    public static TreeMap<String, Word> sortKeys(DictionaryModel srcHashMap)
    {
        TreeMap<String, Word> treeMap = new TreeMap<>();
        Set<Map.Entry<String, Word>> set = srcHashMap.showEntrySet();
        for (Map.Entry<String, Word> mapEntry : set)
        {
            treeMap.put(mapEntry.getKey(), mapEntry.getValue());
        }
        return treeMap;
    }
    
    public static void removeWordFromDict(int type, String inputString) throws Exception
    {
        if (type == 0)
        {
            dictRuEng.removeKey(inputString);
            if (dictRuEng.isEmpty())
            {
                System.out.println("Your dictionary is empty now");
                DAO.resultRus.delete();
            }
            else
            DAO.doSerialize(type, dictRuEng);
        }
        else if (type == 1)
        {           
            dictEngRu.removeKey(inputString);
            if (dictEngRu.isEmpty())
            {
                System.out.println("Your dictionary is empty now");
                DAO.resultEng.delete();
            }
            else
            DAO.doSerialize(type, dictEngRu);
        }
        else 
        {
            System.out.println("I don't know how you came here");
        }
    }
}
