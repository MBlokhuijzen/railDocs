package nl.loxia.raildocs.raildocs;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.RequiresAuthentication;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.List;

/**
 * Created by Tiemen on 10-6-2014.
 */
@Rest(rootUrl = "https://www.railcloud.nl/railcloud/infra", converters = {GsonHttpMessageConverter.class})
public interface IRailCloud {
    @Get("/namespaces")
    @RequiresAuthentication
    void getNamespaces();

    @Get("/values/lijst/velden?type=nl.loxia.document.blad&veld=vlpost")
    @RequiresAuthentication
    List<String> getPosten();

    @Get("/values/lijst/velden?type=nl.loxia.document.blad&veld=dossiernaam&filter=vlpost;e;STRING;{post}")
    @RequiresAuthentication
    List<String> getDossiers(String post);

    @Get("/values/lijst/velden?type=nl.loxia.document.blad&veld=documentnaam&filter=vlpost;e;STRING;{post}&filter=dossiernaam;e;STRING;{dossier}")
    @RequiresAuthentication
    List<String> getDocumenten(String post, String dossier);

    void setHttpBasicAuth(String username, String password);
}
