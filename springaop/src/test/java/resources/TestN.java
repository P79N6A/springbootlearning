package resources;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

public class TestN {
    private String namwwe="";
    @Test
    public void tets() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(stringBuilder.toString());
        TestN testN = new TestN();
        Field[] fields =testN.getClass().getDeclaredFields();
        System.out.println(fields[0].get(testN)==null);
    }

    private void f1(int a, int b) {
        System.out.println(a);
        System.out.println(b);
    }

    private String add() {
        return "sss";
    }

    private String ad() throws Exception {
        throw new Exception();
    }

    @Test
    public void tetst() {
        BloomFilter bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 10, 0.01);
        bloomFilter.put("asad");
        bloomFilter.put("asxa");
        System.out.println(bloomFilter.mightContain("asad"));
        System.out.println(bloomFilter.mightContain("as"));
        System.out.println(bloomFilter.mightContain("asxa"));
        System.out.println(bloomFilter.mightContain("asda"));
    }
}
