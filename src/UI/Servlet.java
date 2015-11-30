package UI;

import javax.servlet.http.HttpServlet;

import java.util.Map;
import java.util.Hashtable;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.transform.stream.StreamSource;

import java.io.StringReader;
import java.net.URL;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;

import BusinessLayer.AppFacade;

import java.io.FileInputStream;

public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Properties	properties	= new Properties();
	private Page page;

	public void doGet(	HttpServletRequest	request,HttpServletResponse	response)throws ServletException, IOException {
		System.out.println("Page =" + request.getParameter("Page"));	
		response.setContentType("text/html; charset=ISO-8859-1");
		PrintWriter out = response.getWriter();
		try {
			StreamSource input= new StreamSource(new StringReader(page.doGet(request,response)));
			StreamSource stylesheet= new StreamSource(new URL("file", "",properties.getProperty("template")).openStream());
			StreamResult output	= new StreamResult(out);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer(stylesheet);
			transformer.transform(input, output);	
		}
		catch (Exception e) {
			//out.write(e.getMessage());
			e.printStackTrace(out);
		}
	}

	public void doPost(	HttpServletRequest	request,HttpServletResponse	response)throws ServletException, IOException {		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			StreamSource input= new StreamSource(new StringReader(page.doPost(request,response)));
			StreamSource stylesheet= new StreamSource(new URL("file", "",properties.getProperty("template")).openStream());
			StreamResult output= new StreamResult(out);
			TransformerFactory tf= TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer(stylesheet);
			transformer.transform(input, output);
		}
		catch (Exception e) {
			//out.write(e.getMessage());
			e.printStackTrace(out);
		}
	}

	public void init() throws ServletException {
		try {
			String cfgfile = getInitParameter("config_file");
			properties.load(new FileInputStream(cfgfile));
			page = new Page(new AppFacade());			
		}
		catch (IOException e) {

		}	
		
	}
}
