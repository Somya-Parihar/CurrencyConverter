/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author somya
 */
@WebServlet(urlPatterns = {"/Convert"})
public class Convert extends HttpServlet {

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String amount = request.getParameter("fname");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        double a = Double.parseDouble(amount);
        try {
            double exchange = Convert(from,to);
            String htmlResponse = "<!DOCTYPE html>" +
                                    "<html lang=\"en\">" +
                                    "<head>" +
                                    "<meta charset=\"UTF-8\">" +
                                    "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" +
                                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                                    "<title>Currency Converter</title>\n" +
                                    "<!-- linking style.css file-->\n" +
                                    "<link rel=\"stylesheet\" href=\"style.css\">\n" +
                                    "</head>";
            htmlResponse += "<h1 class = \"heading\" >"+amount+" " +from+"'s is " + String.format("%.2f", a*exchange) + " "+to+"'s"  +"<br/>";
            htmlResponse += "<div class=\"form\"> <a class = \"convert\"href = \"index.html\"><button class=\"convert\">Go Back</button></a></div>";
            htmlResponse += "</html>";
        // return response
            out.println(htmlResponse);
        } catch (InterruptedException ex) {
            Logger.getLogger(Convert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static double Convert(String from,String To) throws IOException, InterruptedException{
    	URL obj = new URL("http://api.exchangeratesapi.io/v1/latest?access_key=9e82057a2a2de180379d711fe37b6859&format=1");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close(); 
			try {
				JSONObject object = new JSONObject(response.toString());
				JSONObject rates_object = new JSONObject(object.getJSONObject("rates").toString());
				return rates_object.getDouble(To)/rates_object.getDouble(from);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			System.out.println("GET request did not work.");
		}
    	return 0;
    }


    

}
