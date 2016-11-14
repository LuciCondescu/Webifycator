package com.licenta.core;

import java.io.*;

/**
 * A thread which writes to a file (it's name will be "this" hashCode) the content from an {@link InputStream}
 *
 * @author Lucian CONDESCU
 */
public class StreamHandlerThread extends Thread {

    private InputStream stream;
    private String fileName;

    public StreamHandlerThread(InputStream stream, String workingDirectory) {
        this.stream = stream;
        fileName = workingDirectory + File.separator + String.valueOf(this.hashCode());
    }

    @Override
    public void run() {
        try (
                InputStreamReader isr = new InputStreamReader(stream);
                BufferedReader reader = new BufferedReader(isr);
                PrintWriter writer = new PrintWriter(fileName, "UTF-8")) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String tail(int lines) {

        File file = new File(fileName);
        try (RandomAccessFile fileHandler = new RandomAccessFile(file, "r")) {

            long fileLength = fileHandler.length() - 1;
            StringBuilder builder = new StringBuilder();
            int line = 0;

            for (long filePointer = fileLength; filePointer != -1; filePointer--) {
                fileHandler.seek(filePointer);
                int readByte = fileHandler.readByte();

                if (readByte == 0xA) {
                    if (filePointer < fileLength) {
                        line = line + 1;
                    }
                } else if (readByte == 0xD) {
                    if (filePointer < fileLength - 1) {
                        line = line + 1;
                    }
                }
                if (line >= lines) {
                    break;
                }
                builder.append((char) readByte);
            }

            return builder.reverse().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
