public class LikeFilterOption implements FilterOption {

    String optionName;
    String value = "";

    @Override
    public String getQueryString() {
        return !value.isBlank() ? optionName + " like " + "'" + value + "'" : "";
    }
}
