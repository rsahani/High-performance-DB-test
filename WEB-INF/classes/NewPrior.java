
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class NewPrior extends HttpServlet {
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
      // Set the response message's MIME type
      String data = "false";
response.setContentType("text/plain");
response.setCharacterEncoding("UTF-8");

      // Allocate a output writer to write the response message into the network socket
     PrintWriter out = response.getWriter();
 // out.println("Here0!!");
      // Write the response message, in an HTML page
      try {

         // out.println("Here1!!");
         String newp=request.getParameter("prior");
         String reqid=request.getParameter("reqd");
         
         Final tmp= Final.getInstance();
         String str=reqid.toUpperCase();
         int np=Integer.parseInt(newp);
         // out
         // out.println(np);
         boolean f=tmp.changePriority(np,reqid);
         // out.println("Here2!!");
         if(f)
            data="true";
         // out.println(data);
      } 
      finally {
         //   RequestDispatcher requestDispatcher = request.getRequestDispatcher("/crunch");
         // requestDispatcher.forward(request, response);
         // out.println("Here3!!");
         response.getWriter().write(data);
         out.close();  // Always close the output writer
      }
   }
}