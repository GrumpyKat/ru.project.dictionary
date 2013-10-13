package ru.project.dictionary;

import java.util.*;

public class Dictionary 
{
    //final int CAPACITY = 100;
    //HashMap<String, Object> dictRuEng = new HashMap<>(CAPACITY);
    //HashMap<String, Object> dictEngRu = new HashMap<>(CAPACITY);
    
    public void getAllWordsToSee(HashMap<String, Object> srcHashMap)
    {
        Set<Map.Entry<String, Object>> set = srcHashMap.entrySet();
        for (Map.Entry<String, Object> me : set)
        {
            System.out.println(me.getKey() + ": " + me.getValue());
        }
    }
    
    public Object getWordFromDict(HashMap<String, Object> srcHashMap, String key)
    {
        return srcHashMap.get(key);
    }
    
    public void putWordToDict(HashMap<String, Object> srcHashMap, String key, Object word)
    {
        srcHashMap.put(key, word);
    }
    
    public static void main(String[] args) 
    {
        final int CAPACITY = 100;
        Word newWord = new Word();
        Dictionary newDict = new Dictionary();
        
        HashMap<String, Object> dictRuEng = new HashMap<>(CAPACITY);
        HashMap<String, Object> dictEngRu = new HashMap<>(CAPACITY);
        
        newWord.setName("Я");
        newWord.setTranslation("I");
        newWord.setTranscription("[a:j]");
        
        newDict.putWordToDict(dictRuEng, newWord.getName(), newWord);
        newDict.getAllWordsToSee(dictRuEng); // я это намутил, чтоб убедиться, что хоть что-то выводится
    }
}
