package com.licenta.servlets;

import com.licenta.utils.TempGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "UploadServlet",
            urlPatterns = {"/protected/upload"})
@MultipartConfig
public class UploadServlet extends HttpServlet {

    /**
     * Name of the directory where uploaded files will be saved, relative to
     * the web application directory.
     */
    private static final String SAVE_DIR = "workspace";
    private static final String FILE_PREFIX = "file_source_";

    /**
     * handles file upload
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String receivedToken = request.getParameter("CSRFtoken");
        String storedToken = (String) request.getSession().getAttribute("CSRFToken");
        if(!storedToken.equals(receivedToken))  {
            System.out.println("storedToken = " + storedToken);
            System.out.println("receivedToken = " + receivedToken);
            return;
        }
        // gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String tempDirectoryName = TempGenerator.INSTANCE.nextTemp();
        final String workspace = appPath + File.separator + SAVE_DIR + File.separator;
        String tempDirectoryPath = workspace + tempDirectoryName;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(tempDirectoryPath);
        fileSaveDir.mkdirs();

        for (Part part : request.getParts()) {
            if (part.getName().startsWith(FILE_PREFIX)) {
                String fileName = extractFileName(part);
                String absoluteFilePath="";
                if(!"".equals(fileName)) {
                    absoluteFilePath = tempDirectoryPath + File.separator + fileName;
                    part.write(absoluteFilePath);
                }
                final String fileInput = part.getName().substring(FILE_PREFIX.length(), part.getName().length());
                request.setAttribute(fileInput, absoluteFilePath);
            }
        }

        request.setAttribute("dir",tempDirectoryPath);
        request.getRequestDispatcher("launch").forward(request, response);
    }

    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("Content-Disposition");
        System.out.println("contentDisp = " + contentDisp);
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}