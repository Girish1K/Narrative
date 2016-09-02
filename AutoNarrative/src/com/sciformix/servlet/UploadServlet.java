package com.sciformix.servlet;
 
import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.wink.json4j.JSONArray;

import com.sciformix.utility.Constants;
import com.sciformix.utility.FileUtility;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3 ; 
	private static final int MAX_FILE_SIZE= 1024 * 1024 * 40;
	private static final int MAX_REQUEST_SIZE= 1024 * 1024 * 50;
	
	
	
	
	private FileUtility fileUtility;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("In Post Mehtod of UploadServlet\n"+"CaseID= "+request.getParameter("caseID\n"));
			if (!ServletFileUpload.isMultipartContent(request)) {
			    PrintWriter writer = response.getWriter();
			    writer.println("Request does not contain upload data");
			    writer.flush();
			    return;
			}
			new Constants("autoNarrative.properties");
			
			int MAX_COLUMN_SIZE_OF_FILE=Integer.parseInt(Constants.getProperty("MAX_COLUMN_SIZE_OF_FILE"));
			int DEFAULT_GRID_SIZE=Integer.parseInt(Constants.getProperty("DEFAULT_GRID_SIZE"));
			int SEARCH_SHEET_NO=Integer.parseInt(Constants.getProperty("SEARCH_SHEET_NO"));
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(THRESHOLD_SIZE);
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			 
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(MAX_FILE_SIZE);
			upload.setSizeMax(MAX_REQUEST_SIZE);
			
			String uploadPath = getServletContext().getRealPath("")
				    + File.separator + Constants.getProperty("UPLOAD_DIRECTORY");
				// creates the directory if it does not exist
			System.out.println("upload path= "+uploadPath);
				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists()) {
					System.out.println("Directory not exist so creating it");
				    uploadDir.mkdir();
				}
				
				List formItems = null;
				String fileName=null;
				try {
					formItems = upload.parseRequest(request);
				Iterator iter = formItems.iterator();
				 
				// iterates over form's fields
				while (iter.hasNext()) {
				    FileItem item = (FileItem) iter.next();
				    // processes only fields that are not form fields
				    if (!item.isFormField()) {
				        fileName = new File(item.getName()).getName();
				        String filePath = uploadPath + File.separator + fileName;
				        File storeFile = new File(filePath);
				 
				        // saves the file on disk
				        item.write(storeFile);
				    }
				}
				
				} catch (FileUploadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("fileName= "+fileName);
				
				fileUtility=new FileUtility();
				JSONArray jsonArray = null;
				try {
					jsonArray = fileUtility.getJson(uploadPath+"\\"+fileName, SEARCH_SHEET_NO, MAX_COLUMN_SIZE_OF_FILE, DEFAULT_GRID_SIZE);
				} catch (org.apache.wink.json4j.JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(jsonArray);
				
				request.setAttribute("message", "Upload has been done successfully!");
				request.getSession().setAttribute("jsonArray", jsonArray);				
				request.getSession().setAttribute("displayTable", "Y");				
				getServletContext().getRequestDispatcher("/upload.jsp").forward(request, response);
	
	}

}
