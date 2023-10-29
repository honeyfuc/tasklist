package com.honeyfuc.tasklist.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "JWT response")
public class JwtResponse {

    @Schema(description = "User id", example = "3")
    private Long id;

    @Schema(description = "Username", example = "yaroslavyankov@yandex.ru")
    private String username;

    @Schema(description = "Access token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YXJvc2xhdnlhbmtvdkBnbWFpbC5jb20iLCJqdGkiOiIzIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY5ODYwNTQ4OSwiZXhwIjoxNjk4NjA5MDg5fQ.0LxTbTVLyQNYdt9FUVFsmrPExFHaYbuvXMVlPnuYxLtgsdmjqym0pHCBkm3-ZMw0iMvmD8M3NZKHYhEM91yvgA")
    private String accessToken;

    @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5YXJvc2xhdnlhbmtvdkBnbWFpbC5jb20iLCJqdGkiOiIzIiwiaWF0IjoxNjk4NjA1NDg5LCJleHAiOjE3MDExOTc0ODl9.SgYWhdhg-v8DoFL3DcVT0CUDSQsV6vwGa-4uqeVmRiiqMVT9wMkXnjahNR-KDrGjRSve8zRI7D-j_CNFp8v6lA")
    private String refreshToken;

}
