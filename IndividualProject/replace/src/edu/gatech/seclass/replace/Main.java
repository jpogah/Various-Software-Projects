package edu.gatech.seclass.replace;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static Charset charset = StandardCharsets.UTF_8;
   private static  String newline = System.lineSeparator();


    public static void main(String[] args) throws Exception {
        // TODO: Empty skeleton method
        ArrayList<String>  myargs = new ArrayList<String>();
        HashSet<String> flags = new HashSet<String>();
        HashSet<String>  listOfFlag = new HashSet<String>();
        StringBuilder fromString = new StringBuilder();
        StringBuilder toString = new StringBuilder();
        ArrayList<String> filenames = new ArrayList<String>();
        listOfFlag.add("-b");
        listOfFlag.add("-l");
        listOfFlag.add("-i");
        listOfFlag.add("-f");
        boolean error = false;
        int startOfOption = 0;

        for(int i = 0 ; i < args.length; i++){
            myargs.add(args[i]);
        }
        if(myargs.indexOf("--") == myargs.lastIndexOf("--")){

            for(int i = 0 ; i < myargs.lastIndexOf("--"); i++){
                if(!listOfFlag.contains(myargs.get(i)) || myargs.get(i).equals("--")){
                    startOfOption=i;
                    if(myargs.get(i).equals("--")){
                        flags.add("--");
                    }
                    break;

                }
            }

        }else{
            startOfOption = myargs.indexOf("--") + 1;
        }

        if(startOfOption== 0){
            flags.add("none");
        } else{
            for(int i=0 ; i <startOfOption ; i++){
                flags.add(myargs.get(i));
            }
        }


         int startpos = myargs.lastIndexOf("--");
        if(startpos == -1){
            error = true;
        }


        if (startpos < 2) {
            error = true;
        }

        if(error){
            usage();
        }
        else{
            for (int i = startpos + 1; i < args.length; i++) {
                filenames.add(args[i]);
            }

            int j = startOfOption;
            while(j < startpos ){
                fromString = new StringBuilder(myargs.get(j));
                toString = new StringBuilder(myargs.get(j+1));
                j=j+2;
                if(fromString.toString().isEmpty()){
                    usage();
                    continue;
                }

                if (listOfFlag.contains(fromString.toString())){

                    if(!flags.contains("--") && !((startpos - startOfOption)>2)){
                        usage();
                        continue;
                    }
                }

                String fromArray[] = fromString.toString().split("\\r?\\n");
                String toArray[] = toString.toString().split("\\r?\\n");
                for (int i = 0; i < fromArray.length; i++) {
                    performReplace(fromArray[i], toArray[i], flags, filenames);
                }

            }
        }


    }



    private static void usage() {
        System.err.println("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- " + "<filename> [<filename>]*" );
    }



    public static void performReplace(String fromString, String toString,HashSet<String> flags, ArrayList<String> filenames ) throws IOException {
        boolean[] backupFlag = new boolean[filenames.size()];
        Arrays.fill(backupFlag, false);
        if (flags.contains("-b")){
            for(int i = 0; i < filenames.size() ; i++){
                Path source = Paths.get(filenames.get(i));
                Path target = Paths.get(filenames.get(i) + ".bck");
                File temp = new File(target.toString());
                if(temp.exists()){
                    backupFlag[i]=true;
                    continue;

                }
                try{
                    Files.copy(source,target);
                } catch(IOException e1){
                    e1.printStackTrace();
                }
            }
        }
        if (flags.contains("-i") && !fromString.isEmpty()) {

            try {
                for (int i = 0; i < filenames.size(); i++) {
                    File myFile = new File(filenames.get(i));
                    StringBuilder result = new StringBuilder();
                    if (myFile.exists() && !myFile.isDirectory() ) {

                        if(backupFlag[i]){
                            System.err.println("Not performing replace for " + myFile.getName() + ": Backup file already exists");
                            continue;
                        }

                       // BufferedReader reader = new BufferedReader(new FileReader(myFile));
                        FileWriter writer = new FileWriter(new File("temp.txt"));
                        String  content = new String(Files.readAllBytes(Paths.get(filenames.get(i))), charset);

                            if (flags.contains("-f") || flags.contains("-l")) {
                                if (flags.contains("-f") ) {
                                    if (content.toLowerCase().contains(fromString.toLowerCase())) {
                                        content= content.replaceFirst("(?i)" + fromString, toString);
                                    }
                                }  if (flags.contains("-l")) {
                                    if(content.toLowerCase().contains(fromString.toLowerCase())){
                                        int lastindex = content.toLowerCase().lastIndexOf(fromString.toLowerCase());
                                        String temp = content.substring(lastindex+fromString.length(),content.length());
                                        result.append(content.substring(0,lastindex));
                                        result.append(toString);
                                        result.append(temp);
                                        content = result.toString();
                                    }
                                    }

                                }
                             else if (!flags.contains("-f") && !flags.contains("-l")) {
                                if (content.toLowerCase().contains(fromString.toLowerCase())) {
                                    content = content.replaceAll("(?i)" + fromString, toString);
                                }
                            }
                         writer.write(content);
                        File oldFile = new File(myFile.getPath());
                        oldFile.delete();
                        File newFile = new File("temp.txt");
                        newFile.renameTo(oldFile);
                        writer.close();
                    } else{
                        System.err.println("File " + myFile.getName() + " not found");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ((flags.contains("none") && !fromString.isEmpty()) || (flags.size() == 1 && flags.contains("-b"))) {
            try {
                for (int i = 0; i < filenames.size(); i++) {
                    File myFile = new File(filenames.get(i));
                    if(backupFlag[i]){
                        System.err.println("Not performing replace for " + myFile.getName() + ": Backup file already exists");
                        continue;
                    }

                    if(myFile.exists() && !myFile.isDirectory()) {
                        BufferedReader reader = new BufferedReader(new FileReader(myFile));
                        FileWriter writer = new FileWriter(new File("temp.txt"));
                        String  content = new String(Files.readAllBytes(Paths.get(filenames.get(i))), charset);
                        if (content.contains(fromString)) {
                              content= content.replaceAll(fromString, toString);
                            }

                        writer.write(content);
                        File oldFile = new File(myFile.getPath());
                        oldFile.delete();
                        File newFile = new File("temp.txt");
                        newFile.renameTo(oldFile);
                        reader.close();
                        writer.close();
                    } else{
                        System.err.println("File " + myFile.getName() + " not found");
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (flags.contains("-f") && !fromString.isEmpty()) {
            try {
                for (int i = 0; i < filenames.size(); i++) {
                    StringBuilder result = new StringBuilder();

                    File myFile = new File(filenames.get(i));
                    if(backupFlag[i]){
                        System.err.println("Not performing replace for " + myFile.getName() + ": Backup file already exists");
                        continue;
                    }

                    if(myFile.exists() && !myFile.isDirectory()) {
                  //      BufferedReader reader = new BufferedReader(new FileReader(myFile));
                        FileWriter writer = new FileWriter(new File("temp.txt"));
                       String  content = new String(Files.readAllBytes(Paths.get(filenames.get(i))), charset);

                        if (content.contains(fromString)) {
                           content =  content.replaceFirst(fromString, toString);
                        }
                        if (flags.contains("-l")){
                            if(content.contains(fromString)){
                                int lastindex = content.lastIndexOf(fromString);
                                String temp = content.substring(lastindex+fromString.length(),content.length());
                                result.append(content.substring(0,lastindex));
                                result.append(toString);
                                result.append(temp);
                                content = result.toString();
                            }
                        }

                        writer.write(content);
                        File oldFile = new File(filenames.get(i));
                        oldFile.delete();
                        File newFile = new File("temp.txt");
                        newFile.renameTo(oldFile);
                        writer.close();
                    }else{
                        System.err.println("File " + myFile.getName() + " not found");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (flags.contains("-l") && !fromString.isEmpty()) {
            try {
                for (int i = 0; i < filenames.size(); i++) {
                    StringBuilder result = new StringBuilder();
                    File myFile = new File(filenames.get(i));
                    if(backupFlag[i]){
                        System.err.println("Not performing replace for " + myFile.getName() + ": Backup file already exists");
                        continue;
                    }

                    if (myFile.exists() && !myFile.isDirectory()) {
                        BufferedReader reader = new BufferedReader(new FileReader(myFile));
                        FileWriter writer = new FileWriter(new File("temp.txt"));
                        String content = new String(Files.readAllBytes(Paths.get(filenames.get(i))), charset);

                        if(content.contains(fromString)){
                            int lastindex = content.lastIndexOf(fromString);
                            String temp = content.substring(lastindex+fromString.length(),content.length());
                            result.append(content.substring(0,lastindex));
                            result.append(toString);
                            result.append(temp);
                        }
                        writer.write(result.toString());
                        File oldFile = new File(filenames.get(i));
                        oldFile.delete();
                        File newFile = new File("temp.txt");
                        newFile.renameTo(oldFile);
                        reader.close();
                        writer.close();
                    }else {
                        System.err.println("File " + myFile.getName() + " not found");
                    }
                    }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(flags.contains("--")){

            try {
                for (int i = 0; i < filenames.size(); i++) {
                    File myFile = new File(filenames.get(i));
                    if(backupFlag[i]){
                        System.err.println("Not performing replace for " + myFile.getName() + ": Backup file already exists");
                        continue;
                    }

                    if(myFile.exists() && !myFile.isDirectory()) {
                        BufferedReader reader = new BufferedReader(new FileReader(myFile));
                        FileWriter writer = new FileWriter(new File("temp.txt"));
                        String  content = new String(Files.readAllBytes(Paths.get(filenames.get(i))), charset);
                        if (content.contains(fromString)) {
                            content= content.replaceAll(fromString, toString);
                        }

                        writer.write(content);
                        File oldFile = new File(myFile.getPath());
                        oldFile.delete();
                        File newFile = new File("temp.txt");
                        newFile.renameTo(oldFile);
                        reader.close();
                        writer.close();
                    } else{
                        System.err.println("File " + myFile.getName() + " not found");
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}