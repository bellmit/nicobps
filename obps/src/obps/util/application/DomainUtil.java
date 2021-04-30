package obps.util.application;

import java.util.Map;
import java.util.List;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Component;

@Component("domainUtil")
public class DomainUtil implements DomainUtilInterface{
    
    //@Autowired private ObjectMapper omapper; 
    
    public Map<String,String> listCommonMap(final List<CommonMap> commonMapList) {
        Map<String,String> newMapList = new LinkedHashMap<>();        
        for(CommonMap L :commonMapList){
            newMapList.put(L.getKey(),L.getValue());
        }
        return  newMapList;
    }  

    
}
