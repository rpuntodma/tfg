package ramon.del.moral.buscadormtg.services;

import java.io.IOException;

public interface ScryfallService {
    
    String searchCards(String endpoint) throws InterruptedException, IOException;
}
