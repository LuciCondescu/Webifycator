package com.licenta.core;

import java.io.*;

/**
 * @author Lucian CONDESCU
 */
public class StreamHandlerThread extends Thread {

    private InputStream is;
    private String fileName;

    public StreamHandlerThread(InputStream is, String workingDirectory) {
        this.is = is;
        fileName = workingDirectory + File.separator + String.valueOf(this.hashCode());
    }

    @Override
    public void run() {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String tail(int lines) {

        RandomAccessFile fileHandler = null;
        File file = new File(fileName);
        try {

            fileHandler = new RandomAccessFile(file, "r");
            long fileLength = fileHandler.length() - 1;
            StringBuilder builder = new StringBuilder();
            int line = 0;

            for(long filePointer = fileLength; filePointer != -1; filePointer--){
                fileHandler.seek( filePointer );
                int readByte = fileHandler.readByte();

                if( readByte == 0xA ) {
                    if (filePointer < fileLength) {
                        line = line + 1;
                    }
                } else if( readByte == 0xD ) {
                    if (filePointer < fileLength-1) {
                        line = line + 1;
                    }
                }
                if (line >= lines) {
                    break;
                }
                builder.append( ( char ) readByte );
            }

            return builder.reverse().toString();
        } catch( IOException e ) {
            e.printStackTrace();
            return "";
        }
        finally {
            if (fileHandler != null ) try {fileHandler.close();} catch (IOException ignored) {}
        }
    }
}
