

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CalcAvg
 */
@WebServlet("/CalcAvg")
public class CalcAvg extends HttpServlet {
	static Connection conn;
	int grade, count;
	double average, sum;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalcAvg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*PrintWriter out = response.getWriter();
		 * request.setAttribute("message","Hello World");
		 * getServletContext().getRequestDispatcher("/Output.jsp").forward(request,response);*/
		
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/html");
		out.println("<html><head>");
		out.println("<div align=\"center\"><h1>Average</h1></div>");
		
		try{
			String url = "jdbc:oracle:thin:testuserdb/password@localhost";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Properties props = new Properties();
			props.setProperty("user", "testuserdb");
			props.setProperty("password", "password");
			
			try{
				conn = DriverManager.getConnection(url,props);
			} catch (SQLException e){
				String msg = e.getMessage();
				out.println("<p>"+msg);
			}
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT ASSIGNMENT, GRADE FROM GRADES");
			
			out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\" integrity=\"sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==\" crossorigin=\"anonymous\">");
			out.println("</head>");
			out.println("<div class=\"container\">");
			out.println("<table class=\"table table-striped\">");
			out.println("<th>Grade</th>");
			
			sum = 0;
			count = 0;
			while (rs.next()){
				grade = rs.getInt("GRADE");
				
				sum += grade;
				count += 1;
				

				out.println("<tr><td>"+grade+"</td></tr>");
			}
			
			average = (sum/count);
			
			out.println("</table>");
			out.println("<div class=\"container\" align=\"right\">");
			out.println("<style>h2, h3 {display:inline; color:green;}</style>");
			out.println("<h2>Average:<h2>");
			out.println("<h3>"+average+"<h3>");
			out.println("</div>");
			out.println("<br>");
			out.println("<br>");
			out.println("<br>");
			out.println("</div>");
			out.println("<div class=\"container\" align=\"right\">");
			out.println("<a class=\"btn btn-primary btn-md\" href=\"http://localhost:8080/MsMackelberryGBAssignment/index.html\" role=\"button\">Add Grade</a>");
			out.println("</div>");
			conn.close();
		}
		catch (Exception e){
			String msg = e.getMessage();
			out.println("<p>" + msg);
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}