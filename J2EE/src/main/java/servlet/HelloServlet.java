package servlet;

import SessionBean.CLStateLess;
import SessionBean.TestStaful;
import entity.ClassEntity;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HelloServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    public HelloServlet() {
    }

    @EJB
    TestStaful ts;

    @EJB
    CLStateLess stateLess;

    protected void doLoadPage(HttpServletResponse response) throws IOException {
        ServletOutputStream out = response.getOutputStream();
        out.println("<html>");
        out.println("<head><title>Hello Servlet</title></head>");
        out.println("<body>");
        out.println("<h1>Delete: " + ts.DeleteNum +"</h1>");
        out.println("<h1>Insert: " + ts.InsertNum +"</h1>");

        List<ClassEntity> list = stateLess.getAllClass();
        for(ClassEntity cl : list){
            out.print(String.format("<h3>ClassID: %-20s ClassName: %-20s</h3>",cl.getId(),cl.getName()));
        }

        out.print("<form action=\"\" method=\"post\">" +
                "<h1>Insert Class:</h1>" +
                "ClassIDDD : <input type=\"text\" name=\"classID\"/><br/>" +
                "ClassName : <input type=\"text\" name=\"className\"/><br/>" +
                "<h1>Delete:</h1>" +
                "ClassIDDD : <input type=\"text\" name=\"Delete\"/><br/>\n" +
                "<input type=\"submit\"/>" +
                "</form>");

        out.println("</body>");
        out.println("<html>");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doLoadPage(response);


    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
        ServletOutputStream out = response.getOutputStream();

        String insertName= request.getParameter("className");
        String insertID= request.getParameter("classID");
        String deID= request.getParameter("Delete");


        if (!insertName.equals("") && !insertID.equals("")){
            ClassEntity cl = new ClassEntity(insertID,insertName);
            if(stateLess.insertClass(cl)){
                ts.InsertNum ++;
                response.reset();
                doLoadPage(response);

            }
            else {
                out.print("<html><body><script>alert('Insert failure!');</script></body></html>");
            }

        }

        if (!deID.equals("")){
            if(stateLess.delete(deID)){
                ts.DeleteNum ++;
                response.reset();
                doLoadPage(response);

            }
            else {
                out.print("<html><body><script>alert('Delete failure!');</script></body></html>");
            }

        }



    }


}
