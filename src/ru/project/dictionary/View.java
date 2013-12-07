package ru.project.dictionary;

import java.io.*;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class View 
{   
    private BufferedReader inBuf;
    
    public View()
    {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader inBuffered = new BufferedReader(in);
        inBuf = inBuffered;
    }
    
    public int language(String inputString) //0 - Rus->Eng; 1 - Eng->Rus; -1 - try new word
    {
        Pattern pat1 = Pattern.compile("[А-Яа-я][ А-Яа-я']*");
        Pattern pat2 = Pattern.compile("[A-Za-z][ A-Za-z']*");
        Matcher mat = pat1.matcher(inputString);
        if (mat.matches()) return 0;
        mat = pat2.matcher(inputString);
        if (mat.matches()) return 1;
        else 
        {
            System.out.println("There is no such language in my dictionary");
            System.out.println("Type another command");
            exit();
            return -1;
        }
    }
    
    public void keyWords(String inputString) throws IOException //по ключевому слову узнаём, что хочет пользователь
    {                                                           //put, find, sort, exit
        Pattern pat1 = Pattern.compile("put [А-Яа-яA-Za-z][ А-Яа-яA-Za-z']*");
        Pattern pat2 = Pattern.compile("find [А-Яа-яA-Za-z][ А-Яа-яA-Za-z']*");
        Pattern pat3 = Pattern.compile("sort");
        Pattern pat4 = Pattern.compile("exit");
        Matcher mat = pat1.matcher(inputString);
        if (mat.matches())
        {
            put(inputString.substring(4));
        }
        mat = pat2.matcher(inputString);
        if (mat.matches())
        {
            find(inputString.substring(5));
        }
        mat = pat3.matcher(inputString);
        if (mat.matches())
        {
            sort();
        }
        mat = pat4.matcher(inputString);
        if (mat.matches())
        {
            exit();
        }
        else
            System.out.println("Invalid command");
    }
    
    void put(String inputString) throws IOException
    {
        int type;
        type = language(inputString);
      /*if (dict == -1)
        {
            System.out.println("Type another command");
            exit();
        }*/
        
        System.out.println("Input translation for your word");
        String translation = inBuf.readLine();
        while (translation == null)
        {
            System.out.println("You have not written translation for your word");
            System.out.println("Please, input translation for your word");
            translation = inBuf.readLine();
        }
        System.out.println("Input transcription for your word");
        String transcription = inBuf.readLine();
        while (transcription == null)
        {
            System.out.println("You have not written transcription for your word");
            System.out.println("Please, input transcription for your word");
            transcription = inBuf.readLine();
        }
        
        Word newWord = new Word();                           //надо будет заменить отдельным методом
        newWord.setTranslation(translation.toLowerCase());
        newWord.setTranscription(transcription.toLowerCase());
        newWord.setName(inputString.toLowerCase());
        
        Dictionary.putWordToDict(type, inputString.toLowerCase(), newWord);
        System.out.println(inputString.toLowerCase() + " in dictionary");
        
        exit();
    }
    
    void find(String inputString) throws IOException
    {
        boolean isInDict;
        int type;
        type = language(inputString);
      /*if (dict == -1)
        {
            System.out.println("Type another command");
            exit();
        }*/
        
        isInDict = Dictionary.isInDict(type, inputString.toLowerCase());
        if (isInDict == true)
        {
            System.out.println(inputString.toLowerCase() + ": " + 
                    Dictionary.getWordFromDict(type, inputString.toLowerCase()));
            exit();
        }
        else
        {
            HashSet<String> hs = new HashSet();
            System.out.println("Your word is not in dictionary");
            if (type == 0) hs = Dictionary.wordsStartWith(Dictionary.dictRuEng, inputString);
            if (type == 1) hs = Dictionary.wordsStartWith(Dictionary.dictEngRu, inputString);
            else
            {
                System.out.println("You couldn't come here, cheater");
                exit();
            }
            if (hs.isEmpty())
            {
                System.out.println("Dictionary has no words like yours");
                System.out.println("Try again with new word");
                exit();
            }
            else
            {
                System.out.println("Perhaps you meant one of these:");
                System.out.println(hs);
                System.out.println("Try again with new word");
                exit();
            }
        }
    }
    
    void sort() throws IOException
    {
        System.out.println("Which ditionary you want to sort?");
        System.out.println("Input 'ru' for Russian-English; Input 'en' for English-Russian");
        String type = inBuf.readLine();
        int intType = 0;
        if ("ru".equals(type)) intType = 0;
        if ("en".equals(type)) intType = 1;
        else
        {
            System.out.println("Invalid input");
            exit();
        }
        TreeMap<String, Object> tm = new TreeMap<>();
        if (intType == 0) tm = Dictionary.sortKeys(Dictionary.dictRuEng);
        if (intType == 1) tm = Dictionary.sortKeys(Dictionary.dictEngRu);
        System.out.println("Dictionary in alphabetical order");
        System.out.println(tm);
        exit();
        
    }
    
    void exit()
    {
        try
        {
            inBuf.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally
        {
            System.exit(0);
        }        
    }
}
