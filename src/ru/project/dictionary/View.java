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
        Pattern pat1 = Pattern.compile("[А-Яа-я][ А-Яа-я'-]*");
        Pattern pat2 = Pattern.compile("[A-Za-z][ A-Za-z'-]*");
        Matcher mat = pat1.matcher(inputString);
        if (mat.matches()) return 0;
        mat = pat2.matcher(inputString);
        if (mat.matches()) return 1;
        else 
        {
            System.out.println("There is no such language in my dictionary");
            System.out.println("Type another command");
            return -1;
        }
    }
    
    public void keyWords(String inputString) throws IOException, Exception //по ключевому слову узнаём, что хочет пользователь
    {                                                           //put, find, sort, exit
        if (inputString.isEmpty()) return;
        Pattern patPut = Pattern.compile("put [А-Яа-яA-Za-z][ А-Яа-яA-Za-z'-]*");
        Pattern patFind = Pattern.compile("find [А-Яа-яA-Za-z][ А-Яа-яA-Za-z'-]*");
        Pattern patRemove = Pattern.compile("remove [А-Яа-яA-Za-z][ А-Яа-яA-Za-z'-]*");
        Pattern patSort = Pattern.compile("sort");
        Pattern patExit = Pattern.compile("exit");
        Pattern patEnter = Pattern.compile("\n");
        Pattern patHelp = Pattern.compile("help");
        Matcher mat = patPut.matcher(inputString);
        if (mat.matches())
        {
            put(inputString.substring(4));
            return;
        }
        mat = patFind.matcher(inputString);
        if (mat.matches())
        {
            find(inputString.substring(5));
            return;
        }
        mat = patSort.matcher(inputString);
        if (mat.matches())
        {
            sort();
            return;
        }
        mat = patExit.matcher(inputString);
        if (mat.matches())
        {
            exit();
        }
        mat = patHelp.matcher(inputString);
        if (mat.matches())
        {
            help();
            return;
        }
        mat = patRemove.matcher(inputString);
        if (mat.matches())
        {
            remove(inputString.substring(7));
            return;
        }
        mat = patEnter.matcher(inputString);
        if (!mat.matches())
            System.out.println("Invalid command");
    }
    
    void put(String inputString) throws IOException, Exception
    {
        int type;
        type = language(inputString);
        if (type == -1)
        {
            return;
        }
        
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
        if (type == -1)
        {
            return;
        }
        
        isInDict = Dictionary.isInDict(type, inputString.toLowerCase());
        if (isInDict == true)
        {
            System.out.println(inputString.toLowerCase() + ": " + 
                    Dictionary.getWordFromDict(type, inputString.toLowerCase()));
        }
        else
        {
            HashSet<String> hs;
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
        TreeMap<String, Word> tm;
        switch (type) 
        {
            case "ru":
                tm = Dictionary.sortKeys(Dictionary.dictRuEng);
                break;
            case "en":
                tm = Dictionary.sortKeys(Dictionary.dictEngRu);
                break;
            default:
                System.out.println("Invalid input");
                return;
        }
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
    
    void help()
    {
        System.out.println("Possible patterns:");
        System.out.println("put 'word'");
        System.out.println("find 'word'");
        System.out.println("sort");
        System.out.println("remove 'word'");
        System.out.println("exit");
    }
    
    void remove(String inputString) throws Exception
    {
        int type;
        type = language(inputString);
        if (type == -1)
        {
            return;
        }
        
        boolean isInDict;
        isInDict = Dictionary.isInDict(type, inputString.toLowerCase());
        if (isInDict == true)
        {
            Dictionary.removeWordFromDict(type, inputString.toLowerCase());
            System.out.println("Your word has been removed");
        }
        else
        {
            System.out.println("This word is not in dictionary");
            System.out.println("Type another word");
        }
    }
}
