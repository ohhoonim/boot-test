# 스프링부트 테스트 라이브러리 5종 해설

## 유튜브 영상
https://www.youtube.com/@hobbyMett

## vscode용 static import 설정

```
"java.completion.favoriteStaticMembers": [
        "org.springframework.test.web.servlet.result.MockMvcResultMatchers.*",
        "org.springframework.test.web.servlet.result.MockMvcResultHandlers.*",
        "org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*",
        "org.assertj.core.api.Assertions.*",
        "org.junit.Assert.*",
        "org.junit.Assume.*",
        "org.junit.jupiter.api.Assertions.*",
        "org.junit.jupiter.api.Assumptions.*",
        "org.junit.jupiter.api.DynamicContainer.*",
        "org.junit.jupiter.api.DynamicTest.*",
        "org.mockito.Mockito.*",
        "org.mockito.ArgumentMatchers.*",
        "org.mockito.Answers.*",
        "org.hamcrest.Matchers.*",
        // "org.hamcrest.MatcherAssert.*"
    ],
```