public record CommunityString(String value) {
    public CommunityString {
        ValidationUtils.requireNonBlank(value, "communityString");
    }

    @Override
    public String toString() {
        return value;
    }
}
