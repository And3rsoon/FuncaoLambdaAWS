package com.example;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;



public class henadler implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent req,Context ctn){

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        AmazonS3 client=AmazonS3ClientBuilder.standard()
            .withRegion("us-east-1")
            .build();

        
        ObjectListing listaObj=client.listObjects("meubucket010203");
        List<S3ObjectSummary> sumario=listaObj.getObjectSummaries();
        StringBuilder responseBody = new StringBuilder();
        try{
        for (S3ObjectSummary os : sumario) {
            responseBody.append(" - ").append(os.getKey()).append("\n");
        }
            response.setStatusCode(200);
            response.setBody(responseBody.toString());
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setBody("Erro ao listar objetos no bucket S3");
        }

        return response;
    }

}
