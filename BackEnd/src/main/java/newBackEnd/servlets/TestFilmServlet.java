package newBackEnd.servlets;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "TestFilmServlet", value = {"/add-film"})
@MultipartConfig(maxFileSize = 16177215)
public class TestFilmServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("title");
        String lastName = request.getParameter("plot");
        String[] actors = request.getParameterValues("actors");


        InputStream inputStream = null; // input stream of the upload file
        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("poster");
        byte[] image = null;
        if (filePart != null) {
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());

            //obtains input stream of the upload file
            //the InputStream will point to a stream that contains
            //the contents of the file
            inputStream = filePart.getInputStream();
            image = inputStream.readAllBytes();
        }


        ServletContext sc = getServletContext();
        String filepath = "C:\\Users\\pmans\\IdeaProjects\\FilmReviewProject\\BackEnd\\src\\main\\webapp\\src\\manager\\images\\image.jpeg";

        byte[] fileContent = FileUtils.readFileToByteArray(new File(filepath));
        String encodedString = Base64.getEncoder().encodeToString(image);
        OutputStream os = response.getOutputStream();
        response.setContentType("image/jpeg");
        os.write(encodedString.getBytes());

    }


}
