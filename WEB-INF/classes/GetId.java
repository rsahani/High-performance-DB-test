
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class GetId extends HttpServlet {
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
      // Set the response message's MIME type
      String data = "0";
response.setContentType("text/plain");
response.setCharacterEncoding("UTF-8");

      // Allocate a output writer to write the response message into the network socket
     PrintWriter out = response.getWriter();
 // out.println("Here0!!");
      // Write the response message, in an HTML page
      try {

         Final tmp= Final.getInstance();
         
         // out.println("Here1!!");
         // out.println(B));
         // out.println(Boolean.toString(tmp.ifEist(rid)));
         // out.println(tmp.a);
         Integer f=tmp.getId();
         data=f.toString();         // out.println(data);
      } 
      // catch(Exception e)
      // {
      //    out.println(e);

      // }
      finally {
         //   RequestDispatcher requestDispatcher = request.getRequestDispatcher("/crunch");
         // requestDispatcher.forward(request, response);
         // out.println("Here3!!");
         response.getWriter().write(data);
         out.close();  // Always close the output writer
      }
   }
}