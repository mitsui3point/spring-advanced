package hello.advanced.app.v1;

import hello.advanced.log.appender.LogAppender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerV1Test extends LogAppender {

    public static final String URL = "/v1/request";
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("쿼리 파라미터 요청에 성공한다")
    void requestTest() throws Exception {
        ResultActions perform = mvc.perform(get(URL)
                .param("itemId", "itemId"));
        //then
        perform.andExpect(content().string("ok"));
    }

    @Test
    @DisplayName("쿼리 파라미터 요청에 실패한다")
    void request500Test() {
        assertThatThrownBy(() -> mvc.perform(get(URL)
                .param("itemId", "ex")));
    }

    @Test
    @DisplayName("쿼리 파라미터 요청 로그를 출력한다")
    void requestLogTest() throws Exception {
        //given
        String expectedLog = "OrderController.request() time=";
        //when
        ResultActions perform = mvc.perform(get(URL)
                .param("itemId", "itemId"));
        //then
        assertThat(getContainsLog(expectedLog)).isPresent();
    }

    @Test
    @DisplayName("쿼리 파라미터 요청 실패 로그를 출력한다")
    void request500LogTest() {
        //given
        String expectedLog = "java.lang.IllegalStateException";
        //when
        assertThatThrownBy(() -> mvc.perform(get(URL)
                .param("itemId", "ex")));
        //then
        assertThat(getContainsLog(expectedLog)).isPresent();
    }
}
