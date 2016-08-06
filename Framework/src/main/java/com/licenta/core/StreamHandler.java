package com.licenta.core;

import java.io.*;

/**
 * @author Lucian CONDESCU
 */
public class StreamHandler {

    private File file;
    public StreamHandler(File file) {
        this.file = file;
    }

    public String tail(int lines, String name) {
        RandomAccessFile fileHandler = null;

        try {

            fileHandler = new RandomAccessFile(file, "r");
            long fileLength = fileHandler.length() - 1;
            StringBuilder sb = new StringBuilder();
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
                sb.append( ( char ) readByte );
            }

            return sb.reverse().toString();
        } catch( java.io.IOException e ) {
            e.printStackTrace();
            return "";
        }
        finally {
            if (fileHandler != null ) try {fileHandler.close();} catch (IOException ignored) {}
        }
    }
}
