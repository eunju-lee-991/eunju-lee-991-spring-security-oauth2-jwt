package com.myprohect.springsecurityoauth2jwt.service;

import com.myprohect.springsecurityoauth2jwt.constant.OAuth2Constant;
import com.myprohect.springsecurityoauth2jwt.constant.RoleConstant;
import com.myprohect.springsecurityoauth2jwt.model.OAuthToken;
import com.myprohect.springsecurityoauth2jwt.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2ProcessServiceImpl implements OAuth2ProcessService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final String NAVER_TOKEN_URL = "https://nid.naver.com/oauth2.0/token";
    private final String KAKAO_PROFILE_URL = "https://kapi.kakao.com/v2/user/me";
    private final String NAVER_PROFILE_URL = "https://openapi.naver.com/v1/nid/me";


    @Override
    public OAuthToken getToken(String code, String provider) throws IOException {
        OAuthToken oAuthToken = null;
        HttpURLConnection connection = null;
        int responseCode;

        String grantType = "authorization_code";
        String clientId;
        String clientSecret;
        String redirectUri;
        String state;
        String url;

        if (provider.equals("kakao")) {
            url = KAKAO_TOKEN_URL;
            clientId = OAuth2Constant.KAKAO_CLIENT_ID;
            redirectUri = OAuth2Constant.KAKAO_REDIRECT_URI;

            connection = getConnection(url, "POST", true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            sb.append("grant_type=" + grantType);
            sb.append("&client_id=" + clientId);
            sb.append("&redirect_uri=" + redirectUri);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

        } else if (provider.equals("naver")) {
            url = NAVER_TOKEN_URL;
            clientId = OAuth2Constant.NAVER_CLIENT_ID;
            clientSecret = OAuth2Constant.NAVER_CLIENT_SECRET;
            state = URLEncoder.encode(OAuth2Constant.NAVER_REDIRECT_URI, StandardCharsets.UTF_8);

            connection = getConnection(url, "POST", true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            sb.append("grant_type=" + grantType);
            sb.append("&client_id=" + clientId);
            sb.append("&client_secret=" + clientSecret);
            sb.append("&state=" + state);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();
        }

        responseCode = connection.getResponseCode();
        String response = getResposeString(connection.getInputStream());

        log.info("{} getToken response code: {}", provider, responseCode);
        log.info("Response: {}", response);

        if (responseCode == 200) {
            JsonParser jsonParser = new JacksonJsonParser();
            Map<String, Object> map = jsonParser.parseMap(response);
            Integer expiresIn = null;
            if (provider.equals("kakao")) {
                expiresIn = (Integer) map.get("expires_in");
            } else if (provider.equals("naver")) {
                expiresIn = Integer.parseInt((String) map.get("expires_in"));
            }

            oAuthToken = OAuthToken.builder()
                    .accessToken((String) map.get("access_token"))
                    .refreshToken((String) map.get("refresh_token"))
                    .tokenType((String) map.get("token_type"))
                    .expiresIn(expiresIn)
                    .refreshTokenExpiresIn(provider.equals("kakao") ? (int) map.get("refresh_token_expires_in") : null)
                    .build();

        } else { // 에러 발생

        }

        return oAuthToken;
    }

    @Override
    public User getUserProfile(String accessToken, String provider) throws IOException {
        String url = null;
        int responseCode;

        User user = null;
        String providerId = "";
        String name = "";
        String email = "";
        String nickname = "";
        String profileImage = "";

        if (provider.equals("kakao")) {
            String[] propertyKeys = {"kakao_account.profile", "kakao_account.name", "kakao_account.email", "kakao_account.age_range", "kakao_account.birthday", "kakao_account.gender"};
            StringBuilder sb = new StringBuilder();
            for (String key : propertyKeys) {
                sb.append("property_keys").append(key).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            url = KAKAO_PROFILE_URL + "?" + sb.toString();
        } else if (provider.equals("naver")) {
            url = NAVER_PROFILE_URL;
        }

        HttpURLConnection connection = getConnection(url, "GET", false);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        responseCode = connection.getResponseCode();
        String response = getResposeString(connection.getInputStream());

        log.info("{} getUserProfile response code: {}", provider, responseCode);
        log.info("UserProfile Response: {}", response);

        if (responseCode == 200) {
            JsonParser jsonParser = new JacksonJsonParser();
            Map<String, Object> map = jsonParser.parseMap(response);

            if (provider.equals("kakao")) {
                providerId = String.valueOf(map.get("id"));

                Map<String, Object> kakaoAccount = (LinkedHashMap) map.get("kakao_account");
                Map<String, Object> profile = (LinkedHashMap) kakaoAccount.get("profile");
                name = (String) kakaoAccount.get("name");
                email = (String) kakaoAccount.get("email");
                nickname = (String) profile.get("nickname");
                profileImage = (String)profile.get("profile_image_url");


            } else if (provider.equals("naver")) {
                Map<String, String> profileMap = (LinkedHashMap) map.get("response");
                providerId = profileMap.get("id");
                name = profileMap.get("name");
                email = profileMap.get("email");
                nickname = URLEncoder.encode(profileMap.get("nickname"), "UTF-8");
                profileImage = profileMap.get("profile_image");
            }

//            user = userRepository.findByEmail(email);

            if (user == null) {
                user = User.builder().name(name)
                        .email(email)
                        .nickname(nickname)
                        .profileImage(profileImage)
                        .providerId(providerId)
                        .provider(provider)
                        .role(RoleConstant.ROLE_USER)
                        .createDate(LocalDateTime.now())
                        .password(passwordEncoder.encode(providerId))
                        .build();

//                userRepository.save(user);
                user.setId(999999); // 생성할 때 받은 Id
            }
        }

        return user;
    }

    @Override
    public OAuthToken refreshAccessToken(String refreshToken) {
        return null;
    }

    private HttpURLConnection getConnection(String requestUrl, String requestMethod, boolean doOutPut) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setDoOutput(doOutPut);

        return connection;
    }

    private String getResposeString(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
