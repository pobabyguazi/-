package pobaby;


	import java.io.*;
	import java.util.ArrayList;

	/**
	 * Created by zero on 2018/3/17.
	 */
	public class cx
	{

	    static int r = 0;
	    static int lineCount = 0;                //统计代码行数
	    static int charCount = 0;                //统计字符个数
	    static int wordCount = 0;                //统计单词个数
	    static int code = 0;                     //统计每行字符个数
	    static int codeLine = 0;                //统计代码行
	    static int empLine = 0;                //统计空行
	    static int exLine = 0;                //统计注释行
	    static int num = 0;                //统计注释行
	    static int wordCount2 = 0;                //统计单词个数
	    static char Char;
	    static char SChar;
	    static char[] text = new char[1000];
	    static char[] stop = new char[1000];
	    public static boolean a =false;
	    public static boolean exSign =false;
	    public static boolean exBegin =false;
	    public static boolean exEnd =false;
	    public static boolean c =false;
	    public static boolean l =false;
	    public static boolean w =false;
	    public static boolean o =false;
	    public static boolean e =false;
	    public static boolean s =false;
	    //public static String road = "";
	    public static boolean change =false;
	    public static boolean sp =false;
	    public static String message = "";
	    public static char[] charMessage;
	    public static String [] stopArray = new String [200];
	    public static String [] wordArray = new String [200];


	    public static boolean isSpace()       //判断是否是空格
	    {
	        if(Char==' ')
	            return true;
	        else
	            return false;
	    }

	    public static boolean isComma()       //判断是否是逗号
	    {
	        if(Char==',')
	            return true;
	        else
	            return false;
	    }

	    public static boolean isEnter()        //判断是否是回车
	    {
	        if(Char=='\n'||Char=='\r')
	            return true;
	        else
	            return false;
	    }
	    public static void Reader(String filename,char[] array)                //读文件函数，将读出的字符放在字符数组中。
	    {
	        try{
	            FileReader in = new FileReader(filename);
	            in.read(array);
	            in.close();

	        }
	        catch (IOException e){

	        }
	    }
	    public static void fileOut(String filename,String outfile,boolean w,boolean c,boolean l,boolean a)             //写文件函数，将程序分析结果接入到指定文件。
	    {
	        try{
	            String filepath = System.getProperty("user.dir");
	            filepath = filepath+'\\';
	            filename = filename.replace(filepath,"");
	            if(w)
	            {
	                message = filename+",";
	                message =message+ "单词数是";
	                if(e)
	                {
	                    message = message +wordCount2;
	                }
	                else
	                {
	                    message = message +wordCount;
	                }
	                message = message +"\r\n";
	            }
	            if(c)
	            {
	                message = filename+",";
	                message =message+ "字符数是";
	                message = message +charCount;
	                message = message +"\r\n";
	            }
	            if(l)
	            {
	                message = filename+",";
	                message =message+ "行数是";
	                message = message +lineCount;
	                message = message +"\r\n";
	            }
	            if(a)
	            {
	                message = filename+",";
	                message = message + "代码行/空行/注释行:";
	                message = message +codeLine +'/'+empLine+'/'+exLine;
	                message = message +"\r\n";
	            }
	            //System.out.println(outfile);
	            File file = new File(outfile);
	            FileWriter out = new FileWriter(outfile);          //将需要输出的信息在需要输出的文件中进行输出
	            charMessage = message.toCharArray();
	            out.write(charMessage);
	            out.close();

	        }
	        catch (IOException e){

	        }
	    }


	    public static void main(String[] args)           //程序从控制台接收数据
	    {

	            String filename = "";                         //文件名
	            String keyword = "";
	            String filepath = "";                         //文件名
	            String path = "";                         //文件名
	            String outfile = "result.txt";              //输出信息文件名
	            int i = 0;
	            int num = 0;
	            int length = args.length;                   //获取指令的个数
	            String stoplist = "";
	            while(i<length)
	            {
	                if(args[i].equals("-a"))
	                {
	                    a = true;
	                    i++;
	                }
	                else if(args[i].equals("-o"))
	                {
	                    o = true;
	                    i++;
	                    outfile =args[i];
	                    if((i+1) == args.length)
	                    {
	                        break;
	                    }
	                    i++;
	                }
	                else if(args[i].equals("-c"))
	                {
	                    c = true;
	                    i++;
	                }
	                else if(args[i].equals("-w"))
	                {
	                    w = true;
	                    i++;
	                }
	                else if(args[i].equals("-l"))
	                {
	                    l = true;
	                    i++;
	                }
	                else if(args[i].equals("-s"))
	                {
	                    s = true;
	                    i++;
	                }
	                else if(args[i].equals("-e"))
	                {
	                    e = true;
	                    i++;
	                    stoplist=args[i];
	                    if((i+1) == args.length)
	                    {
	                        break;
	                    }
	                    i++;
	                }
	                else if(args[i].charAt(0)!='-')
	                {
	                    filepath = System.getProperty("user.dir");
	                    if(s)
	                    {
	                        path = args[i];
	                        if(path.contains(".c"))
	                            keyword = ".c";
	                        else if(path.contains(".txt"))
	                            keyword = ".txt";
	                    }
	                    filename = args[i];
	                    i++;
	                }
	            }

	            Reader(filename,text);
	            for(int h=0;h<200;h++)
	            {
	                stopArray[h] = "";
	            }

	            if(s)                                //对指令路径下的文件进行遍历输出操作
	            {
	                ArrayList<String>sourceNameArray = new ArrayList<String>();
	                getAllFile(sourceNameArray,filepath,keyword,s);
	                for(int n= 0;n<sourceNameArray.size();n++)
	                {
	                    Count();
	                    fileOut(sourceNameArray.get(n),outfile,w,c,l,a);
	                }
	            }

	            if(e)
	            {
	                Reader(stoplist,stop);
	                int j = 0;
	                while(j<stop.length)
	                {
	                    SChar = stop[j];
	                    while(SChar==' '||SChar==','||SChar=='\r')
	                    {
	                        j++;
	                        SChar = stop[j];
	                        change = true;
	                    }
	                    if(change)
	                    {
	                        num++;
	                        change = false;
	                    }
	                    stopArray[num] = stopArray[num]+stop[j];
	                    j++;
	                }
	            }

	            do
	            {
	                Process();
	            }while(Char!='\0');
	            charCount--;
	            charCount = charCount-2*lineCount;
	            if(w)
	            {
	                message =message+ "单词数是";
	                if(e)
	                {
	                    message = message +wordCount2;
	                }
	                else
	                {
	                    message = message +wordCount;
	                }
	                message = message +"\r\n";
	            }
	            if(c)
	            {
	                message =message+ "字符数是";
	                message = message +charCount;
	                message = message +"\r\n";
	            }
	            if(l)
	            {
	                Reader(stoplist,stop);
	                int j = 0;
	                while(j<stop.length)
	                {
	                    SChar = stop[j];
	                    while(SChar==' '||SChar==','||SChar=='\r')
	                    {
	                        j++;
	                        SChar = stop[j];
	                        change = true;
	                    }
	                    if(change)
	                    {
	                        num++;                   //停用词数加一
	                        change = false;
	                    }
	                    stopArray[num] = stopArray[num]+stop[j];
	                    j++;
	                }
	            }

	        do
	        {
	            Process();
	        }while(Char!='\0');
	        charCount--;
	        charCount = charCount-lineCount;
	        String temp = "";
	        String temp2 = "";
	        if(e)
	        {
	            for(int m = 0;m<wordCount;m++)
	            {
	                for(int n = 0;n<num;n++)
	                {
	                    temp = stopArray[n];
	                    temp2 = wordArray[m];
	                    if(temp.contains(temp2))
	                    {
	                        sp = true;
	                    }

	                }
	                if(!sp)
	                {
	                    wordCount2++;
	                }
	                sp=false;
	            }
	        }
	        fileOut(filename,outfile,w,c,l,a);
	    }


	    public static void getAllFile(ArrayList<String>sourceNameArray, String rootPath, String keyword, boolean s)   //获取所有文件("*.c")
	    {
	        File rootFile = new File(rootPath);
	        File[] files = rootFile.listFiles();
	        if (files != null)
	        {
	            for (File f : files)
	            {
	                if (f.isDirectory() && s) //判断是文件夹
	                    getAllFile(sourceNameArray, f.getPath(), keyword, true);
	                else if (f.getName().indexOf(keyword) == f.getName().length() - keyword.length())
	                    sourceNameArray.add(f.getPath());
	            }
	        }
	    }


	    public static void GetChar()            //从源程序中提取字符
	    {
	        Char = text[r];
	        if(Char == '/')
	        {
	            if(text[r+1]=='/')          //判断是否有两个连载一起的"//"
	            {
	                exSign = true;
	            }
	            else if(text[r+1]=='*')      //如果遇到/*则进入注释状态
	            {
	                exBegin = true;
	                r++;
	            }
	        }
	        else if(Char == '*')
	        {
	            if(text[r+1]=='/'&&exBegin)
	            {
	                exBegin = false;
	                exEnd = true;
	                r++;
	            }
	        }
	        else if(Char=='\n'||Char =='\0')
	        {
	            if(exBegin&&(code==0||code == 1))
	            {
	                exLine++;
	                code = 0;
	            }
	            else if(exBegin)
	            {
	                codeLine++;
	                code = 0;
	            }
	            else if(exEnd)
	            {
	                codeLine++;
	                code = 0;
	                exEnd = false;
	            }
	            else if((code==1&&exSign)||(code==0&&exSign))      //如果该行只有一个或者没有字符并且在"/*"的作用范围内
	            {
	                exLine++;
	                code = 0;
	                exSign = false;
	            }
	            else if(code==0||code==1)
	            {
	                empLine++;
	                exSign = false;
	                code = 0;
	            }
	            else
	            {
	                codeLine++;
	                exSign = false;
	                code = 0;
	            }
	            lineCount++;
	        }
	        else if(!(Char==' '||Char=='\r'||Char=='\t'||exSign||exBegin))
	        code++;
	        charCount++;
	        if(Char!='\0')
	            r++;
	    }


	    public static void Process()               //处理函数
	    {
	        String temp = "";
	        while(!(isComma()||isSpace()||isEnter()))
	        {
	            if(Char!=' ')
	                temp = temp + Char;
	            GetChar();
	            if(Char!=' ')
	                temp = temp + Char;
	            if(Char=='\0')
	                break;

	        }
	        wordArray[wordCount] = temp;
	        wordCount++;
	        while(isComma()||isSpace()||isEnter())
	        {
	            GetChar();
	            if(Char=='\0')
	                break;
	        }
	    }
	    public static void Count()
	    {
	        do
	        {
	            Process();
	        }while(Char!='\0');
	        charCount--;
	        charCount = charCount-lineCount;
	        String temp = "";
	        String temp2 = "";
	        if(e)
	        {
	            for(int m = 0;m<wordCount;m++)
	            {
	                for(int n = 0;n<num;n++)
	                {
	                    temp = stopArray[n];
	                    temp2 = wordArray[m];
	                    if(temp.equals(temp2))
	                    {
	                        sp=true;
	                    }

	                }
	                if(!sp)
	                {
	                    wordCount2++;
	                }
	                sp=false;
	            }
	        }
	    }

	};


