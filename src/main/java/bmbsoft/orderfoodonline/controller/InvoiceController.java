package bmbsoft.orderfoodonline.controller;

import bmbsoft.orderfoodonline.model.shared.InvoiceFileInfo;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;

@RestController
@CrossOrigin
public class InvoiceController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(FavouriesController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/api/invoice/{restaurantId}/{fromDate}/{toDate}", method = RequestMethod.GET)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @ResponseBody
    public ResponseEntity<?> createInvoice(
            @PathVariable(value = "fromDate") String from,
            @PathVariable(value = "toDate") String to,
            @PathVariable(value = "restaurantId") Long restaurantId){
        ByteArrayOutputStream byteArrayOutputStream = orderService.createInvoice(from, to, restaurantId);
        ResponseGet responseGet = new ResponseGet();
        responseGet.setContent(new InvoiceFileInfo("Invoice_" + from + "_" + to,"pdf", byteArrayOutputStream.toByteArray()));
        responseGet.setStatus(200);
        responseGet.setMessage("OK");
        return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
    }
}
