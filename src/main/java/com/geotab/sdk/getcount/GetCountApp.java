package com.geotab.sdk.getcount;

import com.geotab.api.GeotabApi;
import com.geotab.http.exception.DbUnavailableException;
import com.geotab.http.exception.InvalidUserException;
import com.geotab.http.request.AuthenticatedRequest;
import com.geotab.http.request.param.SearchParameters;
import com.geotab.http.response.CountResponse;
import com.geotab.model.login.*;
import static com.geotab.http.invoker.ServerInvoker.DEFAULT_TIMEOUT;
import com.geotab.sdk.Util.Cmd;
import java.util.*;

public class GetCountApp {

    public static Integer getCount(GeotabApi api,String typeName,LoginResult loginResult) throws Exception{
        AuthenticatedRequest<?> requestCount = AuthenticatedRequest.authRequestBuilder()
              .method("GetCountOf")
              .params(SearchParameters.searchParamsBuilder()
                .credentials(loginResult.getCredentials())
                .typeName(typeName)
                .build())
              .build();
        Optional<Integer> resultCount = api.call(requestCount, CountResponse.class);
        return resultCount.get();
    }

    public static void main( String[] args ) throws Exception{
        Cmd cmd = new Cmd(GetCountApp.class);
      
        try (GeotabApi api = new GeotabApi(cmd.credentials, cmd.server, DEFAULT_TIMEOUT)) {

            LoginResult loginResult = null;

            // Authenticate user
            try {
                loginResult = api.authenticate();
            } catch (InvalidUserException exception) {
                System.exit(1);
            } catch (DbUnavailableException exception) {
                System.exit(1);
            } catch (Exception exception) {
                System.exit(1);
            }
            String [] entityTypes  = new String []{"Audit","Device","DVIRLog","Zone","ExceptionEvent","Route","Rule","Trailer","User"};
            Scanner scan = new Scanner(System.in);
            String input = null;
            Integer resultCount = null;
            while(true){
                System.out.println("");
                System.out.println("To quit, please enter : Done");
                System.out.println("Please enter entity type to get the count of");
                System.out.println("Some examples are: " + Arrays.toString(entityTypes));
                input = scan.next();
                if(input.equals("Done")){
                    scan.close();
                    System.exit(1);
                }
                else{
                    try{
                        resultCount = getCount(api, input, loginResult);
                        System.out.println(String.format("Total Count of %s is: %s",input,resultCount));
                    }catch(Exception exception){
                        System.out.println("Invalid entity type, please try again");
                    }
                }    
            }

        }
    }   
}
