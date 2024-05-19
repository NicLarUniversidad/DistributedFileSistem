package ar.com.unlu.sdypp.integrador.file.manager.utils;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class json {

    public String ConvertirAjson (Object objeto){
        Gson gson = new Gson();
        // POJO -> JSON String
        String json = gson.toJson(objeto);
        return json;
    }

    public Object ConvertirAobjeto (String json, Class clase){
        Gson gson = new Gson();
        // JSON String -> POJO
        return gson.fromJson(json, clase.getClass());
    }
}
