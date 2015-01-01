
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class AddReq extends HttpServlet {
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

         String par=request.getParameter("parent");
         String reqdesc=request.getParameter("reqd");
         String reqid=request.getParameter("rid");
         // out.println("Here1.1!!");
         String str=par.toUpperCase();
         String rid=reqid.toUpperCase();
         // out.println(str);
         // out.println(rid);
         Final tmp= Final.getInstance();
         
         // out.println("Here1!!");
         // out.println(B));
         // out.println(Boolean.toString(tmp.ifEist(rid)));
         // out.println(tmp.a);
         Boolean f=tmp.addElement(rid,reqdesc,str);
         if(f)
            data="true";         // out.println(data);
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