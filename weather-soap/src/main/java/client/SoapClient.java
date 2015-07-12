package client;

import config.WeatherConfig;
import model.soap.GetCitiesByCountry;
import model.soap.GetCitiesByCountryResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.addressing.client.ActionCallback;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;

/**
 * Created by lutay.d on 12.07.2015.
 */
public class SoapClient {

    public static void main(String[] args) throws URISyntaxException {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(WeatherConfig.class);
        WebServiceTemplate client =
                ctx.getBean("weatherTemplate", WebServiceTemplate.class);
        GetCitiesByCountry request = new GetCitiesByCountry();
        request.setCountryName("Ukraine");
        GetCitiesByCountryResponse response =
                (GetCitiesByCountryResponse) client.marshalSendAndReceive(request, new WebServiceMessageCallback() {
                    public void doWithMessage(WebServiceMessage webServiceMessage) throws IOException, TransformerException {
                        System.out.println(
                                getStringFromDocument(
                                ((SoapMessage) webServiceMessage).getDocument())
                        );
                        ((SoapMessage)webServiceMessage).setSoapAction("http://www.webserviceX.NET/GetCitiesByCountry");
                    }
                }
        );
        System.out.println(response.getGetCitiesByCountryResult());
    }

    public static String getStringFromDocument(Document doc)
    {
        try
        {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        }
        catch(TransformerException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
