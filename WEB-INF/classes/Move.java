
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class Move extends HttpServlet {
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
      // Set the response message's MIME type
      String data = "false";
response.setContentType("text/plain");
response.setCharacterEncoding("UTF-8");

      // Allocate a output writer to write the response message into the network socket
     PrintWriter out = response.getWriter();
 
      // Write the response message, in an HTML page
      try {
         String par=request.getParameter("parent");
         String reqid=request.getParameter("reqid");
         Final tmp= Final.getInstance();
         String str=par.toUpperCase();
         String rid=reqid.toUpperCase();
         Boolean f=tmp.moveElement(rid,str);
         if(f)
            data="true";
      } 
      finally {
         //   RequestDispatcher requestDispatcher = request.getRequestDispatcher("/crunch");
         // requestDispatcher.forward(request, response);
         response.getWriter().write(data);
         out.close();  // Always close the output writer
      }
   }
}