package oauth;


public interface OAuth2UserInfo {
    String getProviderId(); // google, facebook의 primary key
    String getProvider(); // google, facebook...
    String getEmail();
    String getName();
}
