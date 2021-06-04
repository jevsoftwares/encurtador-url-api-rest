package com.jevsoftwares.apirestencurtadorurl.controller;

import com.jevsoftwares.apirestencurtadorurl.model.Url;
import com.jevsoftwares.apirestencurtadorurl.model.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.Optional;

@RestController
public class UrlController {

    @GetMapping(path = "api/status")
    public String status(){

        return "online";

    }

    @Autowired
    private Urls urls;

    @GetMapping(path = "api/{id}")
    @ResponseBody
    public String getRedireciona(@PathVariable Integer id){

        Optional<Url> urlModel    = urls.findById(id);

        return retornaPaginaAcessada(urlModel.get().getUrlOriginal());
    }

    private String retornaPaginaAcessada(String stringURL) {
        String resposta = "";

        try {
            URL url = new URL(stringURL);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            String inputLine;

            StringBuffer sb = new StringBuffer();
            while ((inputLine = in.readLine()) != null) sb.append(inputLine);
            resposta = sb.toString();
            in.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resposta;
    }

    @GetMapping("api/request/{url}")
    @ResponseBody
    public String getUrlById(@PathVariable String url) {

        //valida se é nullo ou vazio
        if (url == null || url.isEmpty())
            return "É necessário informar o parâmetro!";

        //busca pela url o objeto
        Url urlModel    = urls.findByUrlOriginal(url);

        if (urlModel != null && urlModel.getUrlOriginal() != null) {
            //se encontrar a url original é para buscar a curta

            urlModel = urls.findByUrlCurta(url);
            return  urlModel.getUrlCurta();

        }else if (urlModel == null || urlModel.getUrlCurta() == null){
            //se encontrar a url curta é para buscar a original
            return urlModel.getUrlOriginal();
        }

        //não encontrando nenhuma das url é para incluir
        return insertUrl(url, urlModel);
    }

    private String insertUrl(String url, Url urlModel) {

        try {
            urlModel.setUrlOriginal(url);
            urlModel.setUrlCurta(url
                    .replace("www", "")
                    .replace(".com", "")
                    .replace(".br", "")
                    .replace("http://", "")
                    .replace("https://",""));
            urls.save(urlModel);

            return urlModel.getUrlCurta();

        }catch (Exception e){
            return "Erro ao incluir a url no banco de dados; \n"+e.getMessage();
        }
    }

}
