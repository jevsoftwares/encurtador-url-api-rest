package com.jevsoftwares.apirestencurtadorurl.controller;

import com.jevsoftwares.apirestencurtadorurl.model.Url;
import com.jevsoftwares.apirestencurtadorurl.model.Urls;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@RestController
public class UrlController {

    private String URL_CURTA = "localhost:8080/";
    private String HTTP = "http://";
    private String HTTPS = "https://";

    @GetMapping(path = "api/status")
    @ApiOperation("Informa status da API")
    public String status(){

        return "online";

    }

    @Autowired
    private Urls urls;

    @GetMapping(path = "direct/{id}")
    @ResponseBody
    public String getRedireciona(@PathVariable Integer id){

        Optional<Url> urlModel    = urls.findById(id);

        return retornaPaginaAcessada(urlModel.get().getOriginal());
    }

    @GetMapping(path = "api/request")
    @ResponseBody
    public List<Url> getAll(){

        Iterable<Url> urlModel    = urls.findAll();

        return (List<Url>) urlModel;
    }
    private String retornaPaginaAcessada(String stringURL) {
        String resposta = "";

        try {
            URL url = new URL(HTTP+stringURL.replace("https://","").replace("http://",""));
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            if (!((HttpURLConnection) connection).getResponseMessage().contains("OK")) {
                url = new URL(HTTPS+stringURL.replace("http://","").replace("https://",""));
                connection = url.openConnection();
                in = new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream()));

            }

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

        //valida se ?? nullo ou vazio
        if (url == null || url.isEmpty())
            return "?? necess??rio informar o par??metro!";

        //busca pela url o objeto
        Url urlModel    = urls.findByOriginal(url);

        if (urlModel != null && urlModel.getOriginal() != null) {
            //se encontrar a url original ?? para buscar a curta

            return  urlModel.getEncurtada();

        }else if (urlModel != null && urlModel.getEncurtada() != null){

            //se encontrar a url curta ?? para buscar a original
            return urlModel.getOriginal();
        }

        //n??o encontrando nenhuma das url ?? para incluir
        return insertUrl(url);
    }

    private String insertUrl(String url) {
        Url urlModel = new Url();

        try {
            urlModel.setOriginal(url);
            urls.save(urlModel);
            urlModel    = urls.findByOriginal(url);
            urlModel.setEncurtada(HTTP+URL_CURTA+urlModel.getId());
            urls.save(urlModel);

            return urlModel.getEncurtada();

        }catch (Exception e){
            return "Erro ao incluir a url no banco de dados; \n"+e.getMessage();
        }
    }
    @DeleteMapping(path = "api/request/{url}")
    @ResponseBody
    public String deleteById(@PathVariable String url){
        Url urlModel   = urls.findByOriginal(url);

        try {
            urls.deleteById(urlModel.getId());
        }catch (Exception e){
            return "Erro ao tentar excluir: "+e.getMessage();
        }
        return urlModel.getOriginal() + " -> Exclu??do com sucesso!" ;
    }

}
