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
    
    public void keyWords(String inputString) throws IOException, Exception //по ключевому слову узнаём, что хочет пользователь
    {                                                           //put, find, sort, exit
        if (inputString.isEmpty()) return;
        Pattern pat1 = Pattern.compile("put [А-Яа-яA-Za-z][ А-Яа-яA-Za-z']*");
        Pattern pat2 = Pattern.compile("find [А-Яа-яA-Za-z][ А-Яа-яA-Za-z']*");
        Pattern pat3 = Pattern.compile("sort");
        Pattern pat4 = Pattern.compile("exit");
        Pattern pat5 = Pattern.compile("\n");
        Matcher mat = pat1.matcher(inputString);
        if (mat.matches())
        {
            put(inputString.substring(4));
            return;
        }
        mat = pat2.matcher(inputString);
        if (mat.matches())
        {
            find(inputString.substring(5));
            return;
        }
        mat = pat3.matcher(inputString);
        if (mat.matches())
        {
            sort();
            return;
        }
        mat = pat4.matcher(inputString);
        if (mat.matches())
        {
            exit();
        }
        mat = pat5.matcher(inputString);
        if (!mat.matches())
            System.out.println("Invalid command");
    }
    
    void put(String inputString) throws IOException, Exception
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
        System.out.println(inputString.toLowerCase() + " [" + transcription + "] " + translation + " in dictionary");
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
        }
        else
        {
            HashSet<String> hs = new HashSet();
            System.out.println("Your word is not in dictionary");
            if (type == 0) hs = Dictionary.wordsStartWith(Dictionary.dictRuEng, inputString);
            else if (type == 1) hs = Dictionary.wordsStartWith(Dictionary.dictEngRu, inputString);
            else
            {
                System.out.println("You couldn't come here, cheater");
                return;
            }
            if (hs.isEmpty())
            {
                System.out.println("Dictionary has no words like yours");
                System.out.println("Try again with new word");
            }
            else
            {
                System.out.println("Perhaps you meant one of these:");
                System.out.println(hs);
                System.out.println("Try again with new word");
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
        else if ("en".equals(type)) intType = 1;
        else
        {
            System.out.println("Invalid input");
            return;
        }
        TreeMap<String, Object> tm = new TreeMap<>();
        if (intType == 0) tm = Dictionary.sortKeys(Dictionary.dictRuEng);
        else if (intType == 1) tm = Dictionary.sortKeys(Dictionary.dictEngRu);
        System.out.println("Dictionary in alphabetical order");
        System.out.println(tm);        
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
