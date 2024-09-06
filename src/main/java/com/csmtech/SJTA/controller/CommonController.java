package com.csmtech.SJTA.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.SJTA.util.CommonUtil;

@RestController
@CrossOrigin("*")
public class CommonController {

	@Autowired
	private EntityManager entityManager;

	@Value("${file.path}")
	private String docPath;

	@Value("${scanfile.path}")
	private String scanDocPath;

	@PostMapping(value = "/saveFileToTemp")
	public ResponseEntity<?> saveDocImgToTemp(@RequestParam("file") MultipartFile multipart) {
		System.out.println(multipart.toString());
		JSONObject response = new JSONObject();
		try {
			Random rand = new Random();
			int x = rand.nextInt(900 - 100) + 100;
			Timestamp tt = new Timestamp(System.currentTimeMillis());
			String fileNameForType = (multipart.getOriginalFilename());
			String[] fileArray = fileNameForType.split("[.]");
			String actualType = fileArray[fileArray.length - 1];
			File file1 = new File("src/storage/tempfile/" + x + tt.getTime() + "." + actualType);
			BufferedOutputStream bf = null;
			try {
				byte[] bytes = multipart.getBytes();
				bf = new BufferedOutputStream(new FileOutputStream(file1));
				bf.write(bytes);
				bf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			response.put("originalfileName", fileNameForType);
			response.put("fileName", "" + x + tt.getTime() + "." + actualType);
			response.put("status", 200);
			return ResponseEntity.ok(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", 400);
		}
		return ResponseEntity.ok(response.toString());
	}

	@PostMapping(value = "/fillDropDown")
	public ResponseEntity<?> getAllDynamicDropDownValue(@RequestBody String data)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, JSONException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String getData = CommonUtil.inputStreamDecoder(data);
		JSONObject json = new JSONObject(getData);
		String[] str = json.getString("method").split("/");
		Class<?> cls = Class.forName("com.csmtech.SJTA.serviceImpl." + str[0]);
		Method method = cls.getMethod(str[1], EntityManager.class, String.class);
		JSONObject response = new JSONObject();
		response.put("status", 200);
		response.put("result", method.invoke(method, entityManager, json.toString()));
		//System.out.println(ResponseEntity.ok(CommonUtil.inputStreamEncoder(response.toString()).toString()));
		return ResponseEntity.ok(CommonUtil.inputStreamEncoder(response.toString()).toString());

	}

	@PostMapping(value = "/fillRadio")
	public ResponseEntity<?> getAllDynamicRadioValue(@RequestBody String data)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, JSONException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String getData = CommonUtil.inputStreamDecoder(data);
		JSONObject json = new JSONObject(getData);
		String[] str = json.getString("method").split("/");
		Class<?> cls = Class.forName("com.csmtech.SJTA.serviceImpl." + str[0]);
		Method method = cls.getMethod(str[1], EntityManager.class, String.class);
		JSONObject response = new JSONObject();
		response.put("status", 200);
		response.put("result", method.invoke(method, entityManager, json.toString()));
		return ResponseEntity.ok(response.toString());

	}

	// Token based API
	@PostMapping(value = "/fillDropDownToken")
	public ResponseEntity<?> getAllDynamicDropDownValueToken(@RequestBody String data)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, JSONException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println(data.toString());
		JSONObject json = new JSONObject(data);
		String[] str = json.getString("method").split("/");
		Class<?> cls = Class.forName("com.csmtech.SJTA.serviceImpl." + str[0]);
		Method method = cls.getMethod(str[1], EntityManager.class, String.class);
		JSONObject response = new JSONObject();
		response.put("status", 200);
		response.put("result", method.invoke(method, entityManager, json.toString()));
		return ResponseEntity.ok(response.toString());

	}

	@PostMapping(value = "/downloadDocumentPostCheck")
	public ResponseEntity<byte[]> downloadDocumentPost(HttpServletResponse response, @RequestBody String param) {
		JSONObject json = new JSONObject(param);
		String fileName = json.getString("fileName");
		String fileType = json.getString("fileType");
		String filePath = json.getString("filePath");
		String finalPath = docPath;

		if (fileType.equalsIgnoreCase("S")) {
			finalPath = scanDocPath;
		}
		if (filePath != "") {
			finalPath = finalPath + "/" + filePath;
		}
		HttpHeaders headers = new HttpHeaders();
		byte[] data = null;
		try {
			if (null != fileName && fileName.contains(".")) {
				String fileExtension = fileName.split("\\.")[1];
				Path path = Paths.get(finalPath + "/" + fileName);
				data = Files.readAllBytes(path);
				headers.setContentType(MediaType.MULTIPART_FORM_DATA);
				if (fileExtension.equalsIgnoreCase("pdf"))
					response.setContentType("application/pdf");
				else if (fileExtension.equalsIgnoreCase("xls"))
					response.setContentType("application/vnd.ms-excel");
				else if (fileExtension.equalsIgnoreCase("xlsx"))
					response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				else if (fileExtension.equalsIgnoreCase("png"))
					response.setContentType("image/png");
				else if (fileExtension.equalsIgnoreCase("jpeg"))
					response.setContentType("image/jpeg");
				else if (fileExtension.equalsIgnoreCase("jpg"))
					response.setContentType("image/jpg");
				else if (fileExtension.equalsIgnoreCase("csv"))
					response.setContentType("text/csv");
				else if (fileExtension.equalsIgnoreCase("zip"))
					response.setContentType("application/zip");

				response.setHeader("Content-disposition", "inline;filename=" + fileName);
				headers.setContentLength(data.length);
			}

			return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<byte[]>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/downloadDocument/{appDocId}", name = "Download File")
	public ResponseEntity<byte[]> downloadDocument(HttpServletResponse response,
			@PathVariable("appDocId") String fileName) {
		HttpHeaders headers = new HttpHeaders();
		byte[] data = null;
		try {
			if (null != fileName && fileName.contains(".")) {
				String fileExtension = fileName.split("\\.")[1];
				Path path = Paths.get(docPath + "/" + fileName);
				data = Files.readAllBytes(path);
				headers.setContentType(MediaType.MULTIPART_FORM_DATA);
				if (fileExtension.equalsIgnoreCase("pdf"))
					response.setContentType("application/pdf");
				else if (fileExtension.equalsIgnoreCase("xls"))
					response.setContentType("application/vnd.ms-excel");
				else if (fileExtension.equalsIgnoreCase("xlsx"))
					response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				else if (fileExtension.equalsIgnoreCase("png"))
					response.setContentType("image/png");
				else if (fileExtension.equalsIgnoreCase("jpeg"))
					response.setContentType("image/jpeg");
				else if (fileExtension.equalsIgnoreCase("jpg"))
					response.setContentType("image/jpg");
				else if (fileExtension.equalsIgnoreCase("csv"))
					response.setContentType("text/csv");
				else if (fileExtension.equalsIgnoreCase("zip"))
					response.setContentType("application/zip");

				response.setHeader("Content-disposition", "inline;filename=" + fileName);
				headers.setContentLength(data.length);
			}

			return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<byte[]>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/viewDocument/{appDocId}", name = "View File")
	public ResponseEntity viewDocument(HttpServletResponse response, @PathVariable("appDocId") String fileName)
			throws FileNotFoundException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "inline;filename=" + fileName);
		File file = new File(docPath + "/" + fileName);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		String contentType = "";

		if (null != fileName && fileName.contains(".")) {
			String fileExtension = fileName.split("\\.")[1];

			if (fileExtension.equalsIgnoreCase("pdf"))
				contentType = "application/pdf";
			if (fileExtension.equalsIgnoreCase("xls"))
				contentType = "application/vnd.ms-excel";
			if (fileExtension.equalsIgnoreCase("xlsx"))
				contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			else if (fileExtension.equalsIgnoreCase("png"))
				contentType = "image/png";
			else if (fileExtension.equalsIgnoreCase("jpeg"))
				contentType = "image/jpeg";
			else if (fileExtension.equalsIgnoreCase("jpg"))
				contentType = "image/jpg";
			else if (fileExtension.equalsIgnoreCase("csv"))
				response.setContentType("text/csv");
			else if (fileExtension.equalsIgnoreCase("zip"))
				response.setContentType("application/zip");
		}

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType(contentType)).body(resource);
	}

	@Value("${file.ckpath}")
	private String filePathloc;

	@PostMapping(value = "/ckEditorImageUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> ckEditorImageUpload(@RequestPart("upload") MultipartFile request) {
		JSONObject json = new JSONObject();
		int status;
		String message;
		String result;

		try {
			List<String> typeArray = Arrays.asList("png", "jpg", "jpeg");
			String fileName = request.getOriginalFilename();
			String fileType = request.getContentType();
			fileType = fileType.split("/")[1];
			long fileSize = request.getSize();
			if (!typeArray.contains(fileType) && fileSize > 0) {
				status = 400;
				message = "Invalid file Type";
				result = fileType;
			} else {
				// DB::beginTransaction();
				String[] fileNameArray = fileName.split("\\.");
				String nfilename1 = fileNameArray[0].replace(" ", "_");
				// correct the new fileName up to 50 character
				if (nfilename1.length() > 50) {
					nfilename1 = nfilename1.substring(0, 50);
				}
				// correct the new fileName up to 50 character
				String nfilename = nfilename1 + "_" + System.currentTimeMillis() + "." + fileType;
				String path = filePathloc + '/' + nfilename;
				InputStream source = request.getInputStream();
				Path destination = new File(path).toPath();

				Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
				status = 200;
				message = "File Uploaded Successfully";
				result = destination.toString();

			}
		} catch (IOException e) {
			status = 500;
			message = e.getMessage();
			result = "";
		}

		json.put("status", status);
		json.put("message", message);
		json.put("result", result);

		return ResponseEntity.ok(json.toString());
	}

	@PostMapping(value = "/ckEditorFileUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> ckEditorfileUpload(@RequestPart("upload") MultipartFile request) {
		JSONObject json = new JSONObject();
		int status;
		String message;
		String result;

		try {
			List<String> typeArray = Arrays.asList("csv", "txt", "xlx", "xls", "pdf", "doc", "docx", "xlxs", "xlsx",
					"ppt", "pptx", "odt", "ods", "odp", "rtf");
			String fileName = request.getOriginalFilename();
			String fileType = request.getContentType();
			fileType = fileType.split("/")[1];
			String fileTempName = request.getName();
			long fileSize = request.getSize();
			if (!typeArray.contains(fileType) && fileSize > 0) {
				status = 400;
				message = "Invalid file Type";
				result = fileType;
			} else {
				String[] fileNameArray = fileName.split("\\.");
				String nfilename1 = fileNameArray[0].replace(" ", "_");
				if (nfilename1.length() > 50) {
					nfilename1 = nfilename1.substring(0, 50);
				}
				String nfilename = nfilename1 + "_" + System.currentTimeMillis() + "." + fileType;
				String path = filePathloc + "/" + nfilename;
//	            File file = new File(fileTempName);
				InputStream file = request.getInputStream();
				Path destination = new File(path).toPath();
				Files.copy(file, destination, StandardCopyOption.REPLACE_EXISTING);

				status = 200;
				message = "File Uploaded Successfully";
				result = destination.toString();
			}
		} catch (IOException e) {
			status = 500;
			message = e.getMessage();
			result = "";
		}

		json.put("status", status);
		json.put("message", message);
		json.put("result", result);

		return ResponseEntity.ok(json.toString());
	}

}