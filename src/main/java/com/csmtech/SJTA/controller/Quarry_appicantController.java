package com.csmtech.SJTA.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import java.io.File;
import java.io.IOException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.json.JSONObject; 
import org.springframework.web.bind.annotation.RestController; 
import com.csmtech.SJTA.service.Quarry_appicantService;

import com.csmtech.SJTA.util.CommonUtil;
import com.csmtech.SJTA.util.Quarry_appicantValidationCheck;
@RestController 
@CrossOrigin("*")
@RequestMapping("/quarry-application")
 public class Quarry_appicantController {
@Autowired
private Quarry_appicantService quarry_appicantService; 
String path = "src/storage/individual-details/";
String data ="";@PostMapping("/individual-details/addEdit")
public ResponseEntity<?> create(@RequestBody  String   quarry_appicant)  throws JsonMappingException, JsonProcessingException {
 data = CommonUtil.inputStreamDecoder(quarry_appicant);JSONObject resp=new JSONObject();
if (Quarry_appicantValidationCheck.BackendValidation(new JSONObject(data)) != null) {
 resp.put("status", 502);
 resp.put("errMsg", Quarry_appicantValidationCheck.BackendValidation(new JSONObject(data)));
} else {resp=quarry_appicantService.save(data);
}
 return ResponseEntity.ok(resp.toString());
} 
@PostMapping("/individual-details/preview")
public ResponseEntity<?>getById(@RequestBody String formParams) { data = CommonUtil.inputStreamDecoder(formParams); 
JSONObject json=new JSONObject(data);
JSONObject entity=quarry_appicantService.getById(json.getInt("intId"));
JSONObject jsb = new JSONObject();
jsb.put("status", 200);
  jsb.put("result", entity);
return ResponseEntity.ok(jsb.toString());
}@PostMapping("/individual-details/all")
public ResponseEntity<?>getAll(@RequestBody String formParams){
JSONArray entity=quarry_appicantService.getAll(CommonUtil.inputStreamDecoder(formParams));
JSONObject jsonobj=new JSONObject(); 
jsonobj.put("status", 200); 
jsonobj.put("result",entity); 
return ResponseEntity.ok(jsonobj.toString());
} 
@PostMapping("/individual-details/delete")
public ResponseEntity<?>delete(@RequestBody String formParams) { 
 data = CommonUtil.inputStreamDecoder(formParams); 
JSONObject json=new JSONObject(data);
JSONObject entity=quarry_appicantService.deleteById(json.getInt("intId"));
return ResponseEntity.ok(entity.toString());
}@GetMapping("/individual-details/download/{name}")
 public ResponseEntity<Resource> download(@PathVariable("name") String name) throws IOException{
 System.out.println(name);
 File file=new File(path + name);
 System.out.println("the file is:"+file);
 Path path=Paths.get(file.getAbsolutePath());
 ByteArrayResource byteArrayResource=new ByteArrayResource(Files.readAllBytes(path));
 return ResponseEntity.ok().headers(headers(name))
               .contentLength(file.length())
                 .contentType(MediaType
                  .parseMediaType("application/octet-stream"))
              .body(byteArrayResource);
 }

 private HttpHeaders headers(String name) {
 HttpHeaders header = new HttpHeaders();
         header.add(HttpHeaders.CONTENT_DISPOSITION, 
                      "attachment; filename=" + name);
         header.add("Cache-Control", 
                      "no-cache, no-store, must-revalidate");
         header.add("Pragma", "no-cache");
         header.add("Expires", "0");
         return header;
 }
}