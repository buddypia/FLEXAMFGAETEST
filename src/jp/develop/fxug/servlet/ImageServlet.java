package jp.develop.fxug.servlet;

import java.io.IOException;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.develop.fxug.entity.ImageData;
import jp.develop.fxug.util.PMF;

@SuppressWarnings("serial")
public class ImageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		long since = request.getDateHeader("If-Modified-Since");
		String path = request.getPathInfo();
		String imageId = null;
		if (path != null && path.length() > 1) {
			imageId = path.substring(1);
		}

		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			ImageData image = pm.getObjectById(ImageData.class, imageId);
			long createTime = image.getCreateTime().getTime();
			createTime -= createTime % 1000;
			if (since >= createTime) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return;
			}
			byte[] imageData = image.getImage().getBytes();
			response.setDateHeader("Last-Modified", createTime);
			response.setContentType("image/jpeg");
			response.setContentLength(imageData.length);
			response.getOutputStream().write(imageData);
		} catch (JDOObjectNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} finally {
			pm.close();
		}
	}
}
