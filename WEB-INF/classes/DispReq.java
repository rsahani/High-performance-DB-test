
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
 import org.json.simple.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class DispReq extends HttpServlet {
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
      // Set the response message's MIME type
      JSONArray data = null;
      // String data="false";
response.setContentType("application/json");
// response.setContentType("application/plain");
// response.setCharacterEncoding("UTF-8");

      // Allocate a output writer to write the response message into the network socket
     PrintWriter out = response.getWriter();
 
      // Write the response message, in an HTML page
      try {
         String reqid=request.getParameter("reqid");
         Final tmp= Final.getInstance();
         String str=reqid.toUpperCase();
         JSONArray ja=tmp.getElements(str);
         if(ja!=null)
            data=ja;
      } 
      finally {
         //   RequestDispatcher requestDispatcher = request.getRequestDispatcher("/crunch");
         // requestDispatcher.forward(request, response);
         // response.getWriter().write(data);
         response.getWriter().print(data);
         response.getWriter().flush();
         out.close();  // Always close the output writer
      }
   }
}