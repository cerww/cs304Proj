
import java.util.Optional;

public class GreaterFilterOption implements FilterOption{

    String optionName;
    Optional<Double> amount = Optional.empty();

    @Override
    public String getQueryString() {
        if(amount.isPresent()){
            return optionName + ">=" + amount.get().toString();
        }else{
            return "";
        }
    }
}
