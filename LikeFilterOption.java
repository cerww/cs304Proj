public class LikeFilterOption implements FilterOption {

    String optionName;
    String value = "";

    @Override
    public String getQueryString() {
        return !(value.length() == 0) ? optionName + " like " + "'" + value + "'" : "";
    }
}
