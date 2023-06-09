package hello.advanced.app.v0;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerV0Test {

    public static final String URL_TEMPLATE = "/v0/request";
    @Autowired
    OrderControllerV0 orderController;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("쿼리 파라미터 요청에 성공한다")
    void requestTest() throws Exception {
        ResultActions perform = mvc.perform(get(URL_TEMPLATE)
                .param("itemId", "itemId"));

        perform.andExpect(content().string("ok"));
    }

    @Test
    @DisplayName("쿼리 파라미터 요청에 실패한다")
    void request500Test() {
        Assertions.assertThatThrownBy(() -> {
            ResultActions perform = mvc.perform(get(URL_TEMPLATE)
                    .param("itemId", "ex"));
        });
    }
}
