public class EqualFilterOption implements FilterOption {

    String optionName;
    String value = "";

    @Override
    public String getQueryString() {
        return value == null || value.isBlank() ? "" : optionName + "=" + "\"" + value  + "\"";
    }
}
