package hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MurmurHashTest {

    private static final Logger logger = LoggerFactory.getLogger(MurmurHashTest.class);

    public void test() throws IOException {
        System.out.println(Murmurs.hashUnsigned("chenshuo").toString());
        //.isEqualTo("5016608279269930526");
//        assertThat(Murmurs.hashUnsigned("shaoguoqing").toString()).isEqualTo("17121371936686143062");
//        assertThat(Murmurs.hashUnsigned("baozenghui").toString()).isEqualTo("5451996895512824982");
//        assertThat(Murmurs.hashUnsigned("05ff62ff6f7749ffff43ffff6673ff65").toString()).isEqualTo("10652549470333968609");
//        assertThat(Murmurs.hashUnsigned("hahahaha").toString()).isEqualTo("15134676900169534748");
//        assertThat(Murmurs.hashUnsigned("hahah1369531321aha5466sfdfaerttedddd56da").toString()).isEqualTo("6439159232526071817");
//        assertThat(Murmurs.hashUnsigned("测试汉字").toString()).isEqualTo("1146745369200541601");
//        assertThat(Murmurs.hashUnsigned("1234566大大21".getBytes("GBK")).toString()).isEqualTo("10129762727109086067");
//        assertThat(Murmurs.hashUnsigned("12345665哦4哦3我的妈呀21".getBytes("GBK")).toString()).isEqualTo("5141842319936259217");
    }

}