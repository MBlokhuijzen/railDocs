package nl.loxia.raildocs.raildocs;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.RequiresAuthentication;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.List;

import nl.loxia.raildocs.raildocs.domein.Blad;

/**
 * Created by Tiemen on 10-6-2014.
 */
@Rest(rootUrl = "https://www.railcloud.nl/railcloud/infra", converters = {GsonHttpMessageConverter.class}, interceptors = {StupidBracketInterceptor.class})
public interface IRailCloud {
    @Get("/namespaces")
    @RequiresAuthentication
    void getNamespaces();

    @Get("/values/lijst/velden?type=nl.loxia.document.blad&veld=vlpost&filter=domeintype;in;STRING;OBE(?<!),OR(?<!),OS")
    @RequiresAuthentication
    List<String> getPosten();

    @Get("/values/lijst/velden?type=nl.loxia.document.blad&veld=dossiernaam&filter=vlpost;e;STRING;{post}&filter=domeintype;in;STRING;OBE(?<!),OR(?<!),OS")
    @RequiresAuthentication
    List<String> getDossiers(String post);

    @Get("/values/lijst/velden?type=nl.loxia.document.blad&veld=documentnaam&filter=vlpost;e;STRING;{post}&filter=dossiernaam;e;STRING;{dossier}&filter=domeintype;in;STRING;OBE(?<!),OR(?<!),OS")
    @RequiresAuthentication
    List<String> getDocumenten(String post, String dossier);

    @Get("/values?nameSpace=nl.loxia.document.blad&query=%%7B\"sorteerSpecificaties\":[%%7B\"sorteerVolgorde\":\"ASC\",\"veldNaam\":\"idnummer\"%%7D],\"skip\":0,\"limit\":-1,\"filters\":[%%7B\"veld\":\"domeintype\",\"operator\":\"IN\",\"query\":null,\"inQuery\":[\"OBE\",\"OR\",\"OS\"],\"type\":\"STRING\"%%7D,%%7B\"veld\":\"vlpost\",\"operator\":\"EQUALS\",\"query\":\"{post}\",\"inQuery\":null,\"type\":\"STRING\"%%7D,%%7B\"veld\":\"dossiernaam\",\"operator\":\"EQUALS\",\"query\":\"{dossier}\",\"inQuery\":null,\"type\":\"STRING\"%%7D,%%7B\"veld\":\"documentnaam\",\"operator\":\"EQUALS\",\"query\":\"{document}\",\"inQuery\":null,\"type\":\"STRING\"%%7D,%%7B\"veld\":\"gepubliceerd\",\"operator\":\"EQUALS\",\"query\":\"true\",\"inQuery\":null,\"type\":\"BOOLEAN\"%%7D],\"group\":%%7B\"groupKey\":\"idnummer\",\"reduceKey\":\"versienummer\",\"reduceOperator\":\"HOOGSTE\"%%7D%%7D")
    @RequiresAuthentication
    List<Blad> getBladen(String post, String dossier, String document);

    void setHttpBasicAuth(String username, String password);
}
